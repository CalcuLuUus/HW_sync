import numpy as np

import torch
from .base_model import BaseModel
from . import networks
from .patchnce import PatchNCELoss
import util.util as util

##############################################
import sys
sys.path.append("../../")
sys.path.append("../")
from mpe.models.DeepFSPIS import DeepFSPIS
from mpe.models.mpe import MoirePatternExtractor, MoirePatternPrior


class CUTModel(BaseModel):
    """ This class implements CUT and FastCUT model, described in the paper
    Contrastive Learning for Unpaired Image-to-Image Translation
    Taesung Park, Alexei A. Efros, Richard Zhang, Jun-Yan Zhu
    ECCV, 2020

    The code borrows heavily from the PyTorch implementation of CycleGAN
    https://github.com/junyanz/pytorch-CycleGAN-and-pix2pix
    """
    @staticmethod
    def modify_commandline_options(parser, is_train=True):
        """  Configures options specific for CUT model
        """
        parser.add_argument('--CUT_mode', type=str, default="CUT", choices='(CUT, cut, FastCUT, fastcut)')

        parser.add_argument('--lambda_GAN', type=float, default=1.0, help='weight for GAN loss：GAN(G(X))')
        parser.add_argument('--lambda_NCE', type=float, default=1.0, help='weight for NCE loss: NCE(G(X), X)')
        parser.add_argument('--nce_idt', type=util.str2bool, nargs='?', const=True, default=False, help='use NCE loss for identity mapping: NCE(G(Y), Y))')
        parser.add_argument('--nce_layers', type=str, default='0,4,8,12,16', help='compute NCE loss on which layers')
        parser.add_argument('--nce_includes_all_negatives_from_minibatch',
                            type=util.str2bool, nargs='?', const=True, default=False,
                            help='(used for single image translation) If True, include the negatives from the other samples of the minibatch when computing the contrastive loss. Please see models/patchnce.py for more details.')
        parser.add_argument('--netF', type=str, default='mlp_sample', choices=['sample', 'reshape', 'mlp_sample'], help='how to downsample the feature map')
        parser.add_argument('--netF_nc', type=int, default=256)
        parser.add_argument('--nce_T', type=float, default=0.07, help='temperature for NCE loss')
        parser.add_argument('--num_patches', type=int, default=256, help='number of patches per layer')
        parser.add_argument('--flip_equivariance',
                            type=util.str2bool, nargs='?', const=True, default=False,
                            help="Enforce flip-equivariance as additional regularization. It's used by FastCUT, but not CUT")

        parser.set_defaults(pool_size=0)  # no image pooling

        opt, _ = parser.parse_known_args()

        # Set default parameters for CUT and FastCUT
        if opt.CUT_mode.lower() == "cut":
            parser.set_defaults(nce_idt=True, lambda_NCE=1.0)
        elif opt.CUT_mode.lower() == "fastcut":
            parser.set_defaults(
                nce_idt=False, lambda_NCE=10.0, flip_equivariance=True,
                n_epochs=150, n_epochs_decay=50
            )
        else:
            raise ValueError(opt.CUT_mode)

        return parser

    def __init__(self, opt):
        BaseModel.__init__(self, opt)

        # specify the training losses you want to print out.
        # The training/test scripts will call <BaseModel.get_current_losses>

        #self.loss_names = ['G_GAN', 'D_real', 'D_fake', 'G', 'NCE']

        ######################################
        self.loss_names = ['G_GAN', 'D_real', 'D_fake', 'G', 'NCE', 'TV', 'BG', "COL", "COL_Y"]
        self.mpe = MoirePatternExtractor().to(self.device)
        state_dict = torch.load('../mpe_v4_92k.ckpt')['state_dict']
        state_dict = {".".join(k.split(".")[1:]): state_dict[k] for k in filter(lambda x : "extractor" in x, state_dict.keys())}
        self.mpe.load_state_dict(state_dict)
        self.mpe.eval()
        
        self.prior_mask_model = MoirePatternPrior().to(self.device)
        self.prior_mask_model.eval()
        
        self.DeePFSPIS = DeepFSPIS().to(self.device)
        
        self.moire_nc = 3


        self.visual_names = ['real_A', 'fake_B', 'real_B']
        self.visual_names2 = ['pure_moire_real_A', 'pure_moire_fake_B','pure_moire_real_B', 'pure_moire_idt_B',
                              'smo_real_A', 'smo_fake_B','smo_real_B', 'smo_idt_B',
                              'prior_real_A', 'reverse_prior_real_A', 'fakeB_smorealA', 'fakeB_realA',
                              'BG_part1', 'BG_part2', 'col_part_A', 'col_part_B']
        self.nce_layers = [int(i) for i in self.opt.nce_layers.split(',')] # 0 4 8 12 16
        

        if opt.nce_idt and self.isTrain:
            self.loss_names += ['NCE_Y']
            self.visual_names += ['idt_B']

        if self.isTrain:
            self.model_names = ['G', 'F', 'D']
        else:  # during test time, only load G
            self.model_names = ['G']

        # define networks (both generator and discriminator)
        self.netG = networks.define_G(opt.input_nc + self.moire_nc, opt.output_nc, opt.ngf, opt.netG, opt.normG, not opt.no_dropout, opt.init_type, opt.init_gain, opt.no_antialias, opt.no_antialias_up, self.gpu_ids, opt)
        self.netF = networks.define_F(opt.input_nc + self.moire_nc, opt.netF, opt.normG, not opt.no_dropout, opt.init_type, opt.init_gain, opt.no_antialias, self.gpu_ids, opt)

        if self.isTrain:
            self.netD = networks.define_D(opt.output_nc + self.moire_nc, opt.ndf, opt.netD, opt.n_layers_D, opt.normD, opt.init_type, opt.init_gain, opt.no_antialias, self.gpu_ids, opt)

            # define loss functions
            self.criterionGAN = networks.GANLoss(opt.gan_mode).to(self.device)
            self.criterionNCE = []

            for nce_layer in self.nce_layers:
                self.criterionNCE.append(PatchNCELoss(opt).to(self.device))

            self.criterionIdt = torch.nn.L1Loss().to(self.device)
            self.optimizer_G = torch.optim.Adam(self.netG.parameters(), lr=opt.lr, betas=(opt.beta1, opt.beta2))
            self.optimizer_D = torch.optim.Adam(self.netD.parameters(), lr=opt.lr, betas=(opt.beta1, opt.beta2))
            self.optimizers.append(self.optimizer_G)
            self.optimizers.append(self.optimizer_D)

    def data_dependent_initialize(self, data):
        """
        The feature network netF is defined in terms of the shape of the intermediate, extracted
        features of the encoder portion of netG. Because of this, the weights of netF are
        initialized at the first feedforward pass with some input images.
        Please also see PatchSampleF.create_mlp(), which is called at the first forward() call.
        """
        bs_per_gpu = data["A"].size(0) // max(len(self.opt.gpu_ids), 1)
        self.set_input(data)
        self.real_A = self.real_A[:bs_per_gpu]
        self.real_B = self.real_B[:bs_per_gpu]
        self.forward()                     # compute fake images: G(A)
        if self.opt.isTrain:
            self.compute_D_loss().backward()                  # calculate gradients for D
            self.compute_G_loss().backward()                   # calculate graidents for G
            if self.opt.lambda_NCE > 0.0:
                self.optimizer_F = torch.optim.Adam(self.netF.parameters(), lr=self.opt.lr, betas=(self.opt.beta1, self.opt.beta2))
                self.optimizers.append(self.optimizer_F)

    def optimize_parameters(self):
        # forward
        self.forward()

        # update D
        self.set_requires_grad(self.netD, True)
        self.optimizer_D.zero_grad()
        self.loss_D = self.compute_D_loss()
        self.loss_D.backward()
        self.optimizer_D.step()

        # update G
        self.set_requires_grad(self.netD, False)
        self.optimizer_G.zero_grad()
        if self.opt.netF == 'mlp_sample':
            self.optimizer_F.zero_grad()
        self.loss_G = self.compute_G_loss()
        self.loss_G.backward()
        self.optimizer_G.step()
        if self.opt.netF == 'mlp_sample':
            self.optimizer_F.step()

    def set_input(self, input):
        """Unpack input data from the dataloader and perform necessary pre-processing steps.
        Parameters:
            input (dict): include the data itself and its metadata information.
        The option 'direction' can be used to swap domain A and domain B.
        """
        AtoB = self.opt.direction == 'AtoB'
        self.real_A = input['A' if AtoB else 'B'].to(self.device)
        self.real_B = input['B' if AtoB else 'A'].to(self.device)
        self.image_paths = input['A_paths' if AtoB else 'B_paths']

    def forward(self):
        """Run forward pass; called by both functions <optimize_parameters> and <test>."""
        with torch.no_grad():
            self.mpe_real_A = self.mpe(self.real_A)[-1]
            self.mpe_real_B = self.mpe(self.real_B)[-1]
        self.pure_moire_real_A = self.real_A * self.mpe_real_A
        self.pure_moire_real_B = self.real_B * self.mpe_real_B
        self.input_real_A = torch.cat((self.real_A, self.pure_moire_real_A), dim = 1)
        self.input_real_B = torch.cat((self.real_B, self.pure_moire_real_B), dim = 1)
        self.real = torch.cat((self.input_real_A, self.input_real_B), dim=0) if self.opt.nce_idt and self.opt.isTrain else self.input_real_A
        
        if self.opt.flip_equivariance:
            self.flipped_for_equivariance = self.opt.isTrain and (np.random.random() < 0.5)
            if self.flipped_for_equivariance:
                self.real = torch.flip(self.real, [3])

        self.fake = self.netG(self.real)
        self.fake_B = self.fake[:self.real_A.size(0)]
        if self.opt.nce_idt:
            self.idt_B = self.fake[self.real_A.size(0):]
            
        with torch.no_grad():
            self.mpe_fake_B = self.mpe(self.fake_B)[-1]
            if self.opt.nce_idt:
                self.mpe_idt_B = self.mpe(self.idt_B)[-1]
        if self.opt.nce_idt:
            self.pure_moire_idt_B = self.idt_B * self.mpe_idt_B
        self.pure_moire_fake_B = self.fake_B * self.mpe_fake_B  
        

        
        return self.fake

    def compute_D_loss(self):
        """Calculate GAN loss for the discriminator"""
        fake_B = self.fake_B.detach()
        # Fake; stop backprop to the generator by detaching fake_B
        pure_moire_fake_B = self.pure_moire_fake_B.detach()
        fake = torch.cat((fake_B, pure_moire_fake_B), dim=1)
        
        pred_fake = self.netD(fake)
        self.loss_D_fake = self.criterionGAN(pred_fake, False).mean()
        
        # Real
        self.pred_real = self.netD(self.input_real_B)
        loss_D_real = self.criterionGAN(self.pred_real, True)
        self.loss_D_real = loss_D_real.mean()

        # combine loss and calculate gradients
        self.loss_D = (self.loss_D_fake + self.loss_D_real) * 0.5
        # print("D LOSS REAL:", self.loss_D_real, "D LOSS FAKE", self.loss_D_fake)
        return self.loss_D

    def compute_G_loss(self):
        """Calculate GAN and NCE loss for the generator"""
        fake = torch.cat((self.fake_B, self.pure_moire_fake_B), dim=1)
        # First, G(A) should fake the discriminator
        if self.opt.lambda_GAN > 0.0:
            pred_fake = self.netD(fake)
            self.loss_G_GAN = self.criterionGAN(pred_fake, True).mean() * self.opt.lambda_GAN
        else:
            self.loss_G_GAN = 0.0

        if self.opt.lambda_NCE > 0.0:
            self.loss_NCE = self.calculate_NCE_loss(self.real_A, self.fake_B)
        else:
            self.loss_NCE, self.loss_NCE_bd = 0.0, 0.0

        if self.opt.nce_idt and self.opt.lambda_NCE > 0.0:
            self.loss_NCE_Y = self.calculate_NCE_loss(self.real_B, self.idt_B)
            loss_NCE_both = (self.loss_NCE + self.loss_NCE_Y) * 0.5
        else:
            loss_NCE_both = self.loss_NCE
        
        #######################################################
        self.loss_BG = self.calculate_BG_loss()
        self.loss_TV = self.calculate_TV_loss(self.fake_B)
        self.loss_COL = self.calculate_COL_loss(self.real_A, self.fake_B)
        
        if self.opt.nce_idt:
            self.loss_COL_Y = self.calculate_COL_loss(self.real_B, self.idt_B)
        
        self.loss_G = self.loss_G_GAN + loss_NCE_both + \
                        self.loss_BG + self.loss_TV + self.loss_COL + self.loss_COL_Y
                        
        return self.loss_G

    def calculate_NCE_loss(self, src, tgt):
        #######################
        if torch.equal(src, self.real_A):
            pure_moire_src = self.pure_moire_real_A
            pure_moire_tgt = self.pure_moire_fake_B
            src = self.input_real_A
            self.pure_moire_src = pure_moire_src
            self.pure_moire_tgt = pure_moire_tgt
        else:
            pure_moire_src = self.pure_moire_real_B
            pure_moire_tgt = self.pure_moire_idt_B
            src = self.input_real_B
        
        tgt = torch.cat((tgt, pure_moire_tgt), dim=1)

        n_layers = len(self.nce_layers)
        feat_q = self.netG(tgt, self.nce_layers, encode_only=True)

        if self.opt.flip_equivariance and self.flipped_for_equivariance:
            feat_q = [torch.flip(fq, [3]) for fq in feat_q]

        feat_k = self.netG(src, self.nce_layers, encode_only=True)
        feat_k_pool, sample_ids = self.netF(feat_k, self.opt.num_patches, None)
        feat_q_pool, _ = self.netF(feat_q, self.opt.num_patches, sample_ids)

        total_nce_loss = 0.0
        for f_q, f_k, crit, nce_layer in zip(feat_q_pool, feat_k_pool, self.criterionNCE, self.nce_layers):
            loss = crit(f_q, f_k) * self.opt.lambda_NCE
            total_nce_loss += loss.mean()

        return total_nce_loss / n_layers

    #############################################################
    def calculate_BG_loss(self):
        self.smo_real_A = self.DeePFSPIS(self.real_A)
        self.smo_fake_B = self.DeePFSPIS(self.fake_B)
        with torch.no_grad():
            self.prior_real_A = self.prior_mask_model(self.real_A) # mpp version
            self.reverse_prior_real_A = 1 - self.prior_real_A
            # for visdom
            self.fakeB_smorealA = self.fake_B - self.smo_real_A
            self.fakeB_realA = self.fake_B - self.real_A
            self.BG_part1 = self.prior_real_A * self.fakeB_smorealA
            self.BG_part2 = self.reverse_prior_real_A * self.fakeB_realA
        
        bg_loss = bg_loss = torch.norm(self.prior_real_A * (self.fake_B - self.smo_real_A), p=1) \
                + torch.norm(self.reverse_prior_real_A * (self.fake_B - self.real_A), p=1)
        
        return 10 * self.opt.lambda_GAN * bg_loss / (self.real_A.shape[0] * self.real_A.shape[-1] * self.real_A.shape[-2])

    def calculate_TV_loss(self, tgt):
        batch_size = tgt.shape[0]
        
        tv_loss = torch.sum(((tgt[:, :, :-1, :-1] - tgt[:, :, 1:, :-1])**2 + (tgt[:, :, :-1, :-1] - tgt[:, :, :-1, 1:])**2 + 1e-10).sqrt())
        return 0.1 * self.opt.lambda_GAN * tv_loss / (batch_size * (tgt.shape[-1]-1) * (tgt.shape[-2]-1))

    def calculate_COL_loss(self, src, tgt):
        if torch.equal(src, self.real_A):
            src_smo = self.smo_real_A
            tgt_smo = self.smo_fake_B
            self.col_part_A = src_smo - tgt_smo
        else:
            self.smo_real_B = self.DeePFSPIS(self.real_B)
            self.smo_idt_B = self.DeePFSPIS(self.idt_B)
            src_smo = self.smo_real_B
            tgt_smo = self.smo_idt_B
            self.col_part_B = src_smo - tgt_smo
        
        col_loss = torch.norm(src_smo - tgt_smo, p=1)
        
        return 5 * self.opt.lambda_GAN * col_loss / (src.shape[0] * src.shape[-1] * src.shape[-2])
        
        