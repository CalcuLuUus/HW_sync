import torch
import itertools
from util.image_pool import ImagePool
from .base_model import BaseModel
from . import networks
# try:
#     from apex import amp
# except ImportError as error:
#     print(error)
import sys
sys.path.append("../../")
sys.path.append("../")
from mpe.models.DeepFSPIS import DeepFSPIS
from mpe.models.mpe import MoirePatternExtractor, MoirePatternPrior
import torch.nn as nn
class AttLoss(nn.Module):
    def __init__(self, criterion=nn.MSELoss):
        super(AttLoss, self).__init__()
        self.criterion = criterion()
        
    def forward(self, moired_mask_prior, moired_mask_pred, clean_mask_pred):
        # The two image can't be paired clean-moire\'d !
        
        all_ones = torch.zeros_like(clean_mask_pred, device=clean_mask_pred.device)
        return self.criterion(clean_mask_pred, all_ones) + \
               self.criterion(moired_mask_prior, moired_mask_pred)


class CycleGANMpeModel(BaseModel):
    """
    This class implements the CycleGAN model, for learning image-to-image translation without paired data.

    The model training requires '--dataset_mode unaligned' dataset.
    By default, it uses a '--netG resnet_9blocks' ResNet generator,
    a '--netD basic' discriminator (PatchGAN introduced by pix2pix),
    and a least-square GANs objective ('--gan_mode lsgan').

    CycleGAN paper: https://arxiv.org/pdf/1703.10593.pdf
    """
    @staticmethod
    def modify_commandline_options(parser, is_train=True):
        """Add new dataset-specific options, and rewrite default values for existing options.

        Parameters:
            parser          -- original option parser
            is_train (bool) -- whether training phase or test phase. You can use this flag to add training-specific or test-specific options.

        Returns:
            the modified parser.

        For CycleGAN, in addition to GAN losses, we introduce lambda_A, lambda_B, and lambda_identity for the following losses.
        A (source domain), B (target domain).
        Generators: G_A: A -> B; G_B: B -> A.
        Discriminators: D_A: G_A(A) vs. B; D_B: G_B(B) vs. A.
        Forward cycle loss:  lambda_A * ||G_B(G_A(A)) - A|| (Eqn. (2) in the paper)
        Backward cycle loss: lambda_B * ||G_A(G_B(B)) - B|| (Eqn. (2) in the paper)
        Identity loss (optional): lambda_identity * (||G_A(B) - B|| * lambda_B + ||G_B(A) - A|| * lambda_A) (Sec 5.2 "Photo generation from paintings" in the paper)
        Dropout is not used in the original CycleGAN paper.
        """
        # parser.set_defaults(no_dropout=True, no_antialias=True, no_antialias_up=True)  # default CycleGAN did not use dropout
        # parser.set_defaults(no_dropout=True)
        if is_train:
            parser.add_argument('--lambda_A', type=float, default=10.0, help='weight for cycle loss (A -> B -> A)')
            parser.add_argument('--lambda_B', type=float, default=10.0, help='weight for cycle loss (B -> A -> B)')
            parser.add_argument('--lambda_identity', type=float, default=0.5, help='use identity mapping. Setting lambda_identity other than 0 has an effect of scaling the weight of the identity mapping loss. For example, if the weight of the identity loss should be 10 times smaller than the weight of the reconstruction loss, please set lambda_identity = 0.1')

        return parser

    def __init__(self, opt):
        """Initialize the CycleGAN class.

        Parameters:
            opt (Option class)-- stores all the experiment flags; needs to be a subclass of BaseOptions
        """
        BaseModel.__init__(self, opt)
        # specify the training losses you want to print out. The training/test scripts will call <BaseModel.get_current_losses>
        self.loss_names = ['D_A', 'G_A', 'cycle_A', 'idt_A',# 'idt_A_prep', 'cycle_A_prep',
                           'D_B', 'G_B', 'cycle_B', 'idt_B',# 'idt_B_prep','cycle_B_prep',
                           'bg', 'tv', 'att']
        # specify the images you want to save/display. The training/test scripts will call <BaseModel.get_current_visuals>
        visual_names_A = ['real_A', 'fake_B', 'rec_A', 'moire_real_B', 'moire_real_A']
        visual_names_B = ['real_B', 'fake_A', 'rec_B', 'moire_fake_B', 'moire_fake_A']
        if self.isTrain and self.opt.lambda_identity > 0.0:  # if identity loss is used, we also visualize idt_B=G_A(B) ad idt_A=G_A(B)
            visual_names_A.append('idt_B')
            visual_names_B.append('idt_A')

        self.mpe = MoirePatternExtractor().to(self.device)
        self.prior = MoirePatternPrior().to(self.device)
        self.prior.eval()
        state_dict = torch.load('../mpe_v4_92k.ckpt')['state_dict']
        state_dict = {".".join(k.split(".")[1:]): state_dict[k] for k in filter(lambda x : "extractor" in x, state_dict.keys())}
        self.mpe.load_state_dict(state_dict)
        # self.mpe.eval()

        # self.DeePFSPIS = DeepFSPIS().to(self.device)
        # self.DeePFSPIS.eval()
        
        self.moire_nc = 3

        self.visual_names = visual_names_A + visual_names_B  # combine visualizations for A and B
        # specify the models you want to save to the disk. The training/test scripts will call <BaseModel.save_networks> and <BaseModel.load_networks>.
        if self.isTrain:
            self.model_names = ['G_A', 'G_B', 'D_A', 'D_B']
        else:  # during test time, only load Gs
            self.model_names = ['G_A', 'G_B']

        # define networks (both Generators and discriminators)
        # The naming is different from those used in the paper.
        # Code (vs. paper): G_A (G), G_B (F), D_A (D_Y), D_B (D_X)
        self.netG_A = networks.define_G(opt.input_nc + self.moire_nc, opt.output_nc, opt.ngf, opt.netG, opt.normG,
                                        not opt.no_dropout, opt.init_type, opt.init_gain, opt.no_antialias, opt.no_antialias_up, self.gpu_ids, opt=opt)
        self.netG_B = networks.define_G(opt.output_nc + self.moire_nc, opt.input_nc, opt.ngf, opt.netG, opt.normG,
                                        not opt.no_dropout, opt.init_type, opt.init_gain, opt.no_antialias, opt.no_antialias_up, self.gpu_ids, opt=opt)

        if self.isTrain:  # define discriminators
            self.netD_A = networks.define_D(opt.output_nc + self.moire_nc, opt.ndf, opt.netD,
                                            opt.n_layers_D, opt.normD, opt.init_type, opt.init_gain, opt.no_antialias, self.gpu_ids, opt=opt)
            self.netD_B = networks.define_D(opt.input_nc + self.moire_nc, opt.ndf, opt.netD,
                                            opt.n_layers_D, opt.normD, opt.init_type, opt.init_gain, opt.no_antialias, self.gpu_ids, opt=opt)

        if self.isTrain:
            if opt.lambda_identity > 0.0:  # only works when input and output images have the same number of channels
                assert(opt.input_nc == opt.output_nc)
            self.fake_A_pool = ImagePool(opt.pool_size)  # create image buffer to store previously generated images
            self.fake_B_pool = ImagePool(opt.pool_size)  # create image buffer to store previously generated images
            # define loss functions
            self.criterionGAN = networks.GANLoss(opt.gan_mode).to(self.device)  # define GAN loss.
            self.criterionCycle = torch.nn.L1Loss()
            self.criterionIdt = torch.nn.L1Loss()
            # self.criterionPrep = networks.VGGLoss()
            self.criterionAtt = AttLoss()
            # initialize optimizers; schedulers will be automatically created by function <BaseModel.setup>.
            # self.optimizer_
            self.optimizer_G = torch.optim.Adam(itertools.chain(self.netG_A.parameters(), self.netG_B.parameters(), self.mpe.parameters()), lr=opt.lr, betas=(opt.beta1, 0.999))
            self.optimizer_D = torch.optim.Adam(itertools.chain(self.netD_A.parameters(), self.netD_B.parameters()), lr=opt.lr, betas=(opt.beta1, 0.999))
            self.optimizers.append(self.optimizer_G)
            self.optimizers.append(self.optimizer_D)

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
        #with torch.no_grad():
        self.moire_real_A_list = self.mpe(self.real_A)
        self.moire_real_B_list = self.mpe(self.real_B)
        
        self.moire_real_A = self.moire_real_A_list[-1]

        self.moire_real_B = self.moire_real_B_list[-1]

        self.prior_real_A = self.prior(self.real_A)
        self.prior_real_B = self.prior(self.real_B)

        self.fake_B = self.netG_A(torch.cat([self.real_A, self.moire_real_A * self.real_A], dim=1))  # G_A(A)
        self.fake_A = self.netG_B(torch.cat([self.real_B, self.moire_real_A * self.real_A], dim=1))  # G_B(B)
        
        # with torch.no_grad():
        self.moire_fake_B = self.mpe(self.fake_B, side_output=False)
        self.moire_fake_A = self.mpe(self.fake_A, side_output=False)
        
        self.rec_A = self.netG_B(torch.cat([self.fake_B, self.moire_fake_A * self.fake_A], dim=1))   # G_B(G_A(A))
        self.rec_B = self.netG_A(torch.cat([self.fake_A, self.moire_fake_A * self.fake_A], dim=1))   # G_A(G_B(B))

    def backward_D_basic(self, netD, real, fake):
        """Calculate GAN loss for the discriminator

        Parameters:
            netD (network)      -- the discriminator D
            real (tensor array) -- real images
            fake (tensor array) -- images generated by a generator

        Return the discriminator loss.
        We also call loss_D.backward() to calculate the gradients.
        """
        # Real
        pred_real = netD(real.detach())
        loss_D_real = self.criterionGAN(pred_real, True)
        # Fake
        pred_fake = netD(fake.detach())
        loss_D_fake = self.criterionGAN(pred_fake, False)
        # Combined loss and calculate gradients
        loss_D = (loss_D_real + loss_D_fake) * 0.5
        # if self.opt.amp:
        #     with amp.scale_loss(loss_D, self.optimizer_D) as scaled_loss:
        #         scaled_loss.backward()
        # else:
        loss_D.backward()
        return loss_D

    def backward_D_A(self):
        """Calculate GAN loss for discriminator D_A"""
        fake_B = self.fake_B_pool.query(torch.cat([self.fake_B, self.moire_fake_A * self.fake_B], dim=1))
        self.loss_D_A = self.backward_D_basic(self.netD_A, 
                                              torch.cat([self.real_B, self.moire_real_A * self.real_B], dim=1),
                                              fake_B)

    def backward_D_B(self):
        """Calculate GAN loss for discriminator D_B"""
        fake_A = self.fake_A_pool.query(torch.cat([self.fake_A, self.moire_fake_A * self.fake_A], dim=1))
        self.loss_D_B = self.backward_D_basic(self.netD_B, 
                                              torch.cat([self.real_A, self.moire_real_A *self.real_A], dim=1),
                                              fake_A)

    def backward_G(self):
        """Calculate the loss for generators G_A and G_B"""
        lambda_idt = self.opt.lambda_identity
        lambda_A = self.opt.lambda_A
        lambda_B = self.opt.lambda_B
        lambda_Att = 10
        # Identity loss
        if lambda_idt > 0:
            # G_A should be identity if real_B is fed: ||G_A(B) - B||
            
            self.idt_A = self.netG_A(torch.cat([self.real_B, self.moire_real_A * self.real_A], dim=1))
            # self.loss_idt_A_prep = self.criterionPrep(self.idt_A, self.real_B)  * lambda_idt 
            self.loss_idt_A = self.criterionIdt(self.idt_A, self.real_B) * lambda_B * lambda_idt 


            # G_B should be identity if real_A is fed: ||G_B(A) - A||
            self.idt_B = self.netG_B(torch.cat([self.real_A, self.moire_real_A * self.real_A], dim=1))
            # self.loss_idt_B_prep = self.criterionPrep(self.idt_B, self.real_A)  * lambda_idt
            self.loss_idt_B = self.criterionIdt(self.idt_B, self.real_A)  * lambda_A * lambda_idt
        else:
            self.loss_idt_A = 0
            self.loss_idt_B = 0

        # GAN loss D_A(G_A(A))
        self.loss_G_A = self.criterionGAN(self.netD_A(torch.cat([self.fake_B, self.moire_fake_A * self.fake_B], dim=1)), True)
        # GAN loss D_B(G_B(B))
        self.loss_G_B = self.criterionGAN(self.netD_B(torch.cat([self.fake_A, self.moire_fake_A * self.fake_A], dim=1)), True)
        # Forward cycle loss || G_B(G_A(A)) - A||
        # self.loss_cycle_A_prep = self.criterionPrep(self.rec_A, self.real_A) #* lambda_A
        self.loss_cycle_A = self.criterionCycle(self.rec_A, self.real_A) * lambda_A
        # Backward cycle loss || G_A(G_B(B)) - B||
        # self.loss_cycle_B_prep = self.criterionPrep(self.rec_B, self.real_B) #* lambda_B
        self.loss_cycle_B = self.criterionCycle(self.rec_B, self.real_B) * lambda_B
        # combined loss and calculate gradients
        self.loss_bg = self.calculate_BG_loss(self.real_A, self.fake_B)
        self.loss_tv = self.calculate_TV_loss(self.fake_B)

        self.loss_att = torch.sum(torch.stack([self.criterionAtt(self.prior_real_A, extracted_moired, extracted_clean) 
                                                for extracted_moired, extracted_clean in 
                                                zip(self.moire_real_A_list, self.moire_real_B_list)]), dim=0) * lambda_Att
        self.loss_G = self.loss_G_A + self.loss_G_B +\
                      self.loss_cycle_A + self.loss_cycle_B +\
                      self.loss_cycle_A + self.loss_cycle_B +\
                      self.loss_bg + self.loss_tv +\
                      self.loss_att 
         
        #     with amp.scale_loss(self.loss_G, self.optimizer_G) as scaled_loss:
        #         scaled_loss.backward()
        # else:
        self.loss_G.backward()

    def data_dependent_initialize(self, data):
        return

    def generate_visuals_for_evaluation(self, data, mode):
        with torch.no_grad():
            visuals = {}
            AtoB = self.opt.direction == "AtoB"
            G = self.netG_A
            source = data["A" if AtoB else "B"].to(self.device)
            if mode == "forward":
                visuals["fake_B"] = G(source)
            else:
                raise ValueError("mode %s is not recognized" % mode)
            return visuals

    def optimize_parameters(self):
        """Calculate losses, gradients, and update network weights; called in every training iteration"""
        # forward
        self.forward()      # compute fake images and reconstruction images.
        # G_A and G_B
        self.set_requires_grad([self.netD_A, self.netD_B], False)
        self.set_requires_grad([self.mpe], True)  # Ds require no gradients when optimizing Gs
        self.optimizer_G.zero_grad()  # set G_A and G_B's gradients to zero
        self.backward_G()             # calculate gradients for G_A and G_B
        self.optimizer_G.step()       # update G_A and G_B's weights
        # D_A and D_B
        self.set_requires_grad([self.netD_A, self.netD_B], True)
        self.set_requires_grad([self.mpe], False)
        self.optimizer_D.zero_grad()   # set D_A and D_B's gradients to zero
        self.backward_D_A()      # calculate gradients for D_A
        self.backward_D_B()      # calculate graidents for D_B
        self.optimizer_D.step()  # update D_A and D_B's weights

    #############################################################

    def calculate_BG_loss(self, src, tgt):
        with torch.no_grad():
            self.src_smo = self.prior.smoother(src)
            self.src_p = self.prior(src, side_output=False)
        bg_loss = torch.norm(self.src_p * (tgt - self.src_smo), p=1) \
                + torch.norm((1 - self.src_p) * (tgt - src), p=1)
        
        # print("bg_loss", bg_loss / src.shape[0])
        return 10 * bg_loss / (src.shape[0] * src.shape[-1] * src.shape[-2])

    def calculate_TV_loss(self, tgt):
        batch_size = tgt.shape[0]
        
        tv_loss = torch.sum(((tgt[:, :, :-1, :-1] - tgt[:, :, 1:, :-1])**2 + (tgt[:, :, :-1, :-1] - tgt[:, :, :-1, 1:])**2 + 1e-10).sqrt())
        # print("tv_loss", tv_loss)
        return  0.1 * tv_loss / (batch_size * (tgt.shape[-1]-1) * (tgt.shape[-2]-1))

    