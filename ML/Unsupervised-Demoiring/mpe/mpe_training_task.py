import torch
import torch.nn as nn
from pytorch_lightning.loggers import CometLogger
from torch.utils.data import DataLoader
from torch.utils.data import random_split
import pytorch_lightning as pl
from models import mpe
from models.losses import AttLoss, Norm
from utils.image_utils import imsave, tensor2img
import torchmetrics
from timm.scheduler import CosineLRScheduler
import os, math
# from timm.optim import Lookahead

class MoirePatternExtraction(pl.LightningModule):
    def __init__(self, config : dict):
        super().__init__()
        self.config = config
        self.in_channels = 3
        self.extractor = mpe.MoirePatternExtractor(self.in_channels)
        self.prior = mpe.MoirePatternPrior()
        self.lr = config["base_lr"]
        self.loss = AttLoss(criterion=nn.L1Loss if not config['loss_metric'] == 'mse' else nn.MSELoss)
        self.norm_loss = Norm(criterion=nn.L1Loss if not config['loss_metric'] == 'mse' else nn.MSELoss)
        self.save_dirs = ["prior", "target", "input", "input_clean", "mixture"]
        for save_dir in self.save_dirs:
            os.makedirs(os.path.join(config["save_dir"], save_dir), exist_ok=True) 
    def forward(self, image, test=False, side_output=False):
        with torch.no_grad():
            prior, smoothed_image = self.prior(image, side_output=True)
        extracted = self.extractor(image, side_output=True if not test else False)
        if not side_output : return prior, extracted
        else : return prior, extracted, smoothed_image

    def configure_optimizers(self):
        optimizer = torch.optim.Adam(self.parameters(), lr=self.lr)
        scheduler = CosineLRScheduler(optimizer,
                              warmup_lr_init = self.lr / 100,
                              warmup_t = 0, lr_min = 1e-6,
                              t_initial = self.config["max_epoch"])
        return {
            "optimizer": optimizer,
            "lr_scheduler": {
                "scheduler": scheduler,
                "monitor": "metric_to_track",
            },
        }
    
    def lr_scheduler_step(self, scheduler, optimizer_idx, metric):
        scheduler.step(epoch=self.current_epoch)  # timm's scheduler need the epoch value
    def training_step(self, train_batch, batch_idx):
        img_moire, img_clean = train_batch
        # print(img_moire.shape, img_clean.shape)
        # if batch_idx % 100 == 0:
        
        # original pass
        prior_moired, extracted_moired_list, smoothed = self.forward(img_moire, side_output=True)
        _, extracted_clean_list, smoothed_clean = self.forward(img_clean, side_output=True)
        
        att_loss = torch.mean(torch.stack([self.loss(prior_moired, extracted_moired, extracted_clean) 
                           for extracted_moired, extracted_clean in 
                           zip(extracted_moired_list, extracted_clean_list)]), dim=0)
        
        self.log("train_att_loss", att_loss, logger=True) 
        
        # mixture pass 
        texture_clean, texture_moired = img_clean - smoothed_clean, img_moire - smoothed
        mixed_clean = torch.clip(texture_clean  + smoothed, 0, 1)
        mixed_moire = torch.clip(texture_moired + smoothed_clean, 0, 1)
        mixed_prior_moired, mixed_extracted_moired_list = self.forward(mixed_moire)
        _, mixed_extracted_clean_list = self.forward(mixed_clean)
        
        
        mixture_loss = torch.mean(torch.stack([
                        self.loss(prior_moired, mixed_extracted_moired, mixed_extracted_clean) 
                        for mixed_extracted_moired, mixed_extracted_clean in 
                        zip(mixed_extracted_moired_list, mixed_extracted_clean_list)]), dim=0
        )
        self.log('train_mixture_loss', mixture_loss, logger=True)
        # smoothed pass
        # smoothed_extracted_moired_list = self.extractor(smoothed) + self.extractor(smoothed_clean)
        
        # smoothed_loss = torch.mean(torch.stack([self.norm_loss(item) 
        #                         for item in smoothed_extracted_moired_list]), dim=0
        # )
        
        # self.log("train_smoothed_loss", smoothed_loss, logger=True)
        loss = att_loss + mixture_loss #+ smoothed_loss        
        
        self.log("train_loss", loss, logger=True)
        
        if batch_idx % 200 == 0 and self.global_rank == 0: 
            
            ipt, ipt_clean = tensor2img(img_moire[0].detach().cpu()),\
                             tensor2img(img_clean[0].detach().cpu())
            
            img = tensor2img(extracted_moired_list[-1][0].detach().cpu())
            img_clean = tensor2img(extracted_clean_list[-1][0].detach().cpu())
            target = tensor2img(prior_moired[0].detach().cpu())
            smoothed_img = tensor2img(smoothed[0].detach().cpu())
            mixture_clean_img = tensor2img(mixed_clean[0].detach().cpu())
            mixture_moire_img = tensor2img(mixed_moire[0].detach().cpu())
            smoothed_clean_img = tensor2img(smoothed_clean[0].detach().cpu())
            imsave(img, f"{self.config['save_dir']}/prior/train_{batch_idx}.png")
            imsave(target, f"{self.config['save_dir']}/target/train_{batch_idx}.png")
            imsave(ipt, f"{self.config['save_dir']}/input/train_{batch_idx}.png")
            imsave(ipt_clean, f"{self.config['save_dir']}/input_clean/train_{batch_idx}.png")
            imsave(smoothed_img, f"{self.config['save_dir']}/prior/train_{batch_idx}_smoothed.png")
            imsave(smoothed_clean_img, f"{self.config['save_dir']}/prior/train_{batch_idx}_smoothed_clean.png")
            imsave(img_clean, f"{self.config['save_dir']}/prior/train_{batch_idx}_clean.png")
            imsave(mixture_clean_img, f"{self.config['save_dir']}/mixture/train_{batch_idx}_mixture_clean.png")
            imsave(mixture_moire_img, f"{self.config['save_dir']}/mixture/train_{batch_idx}_mixture_moire.png")
        return loss

    # def validation_epoch_end(self, outputs):
    #     loss_stack = [x['loss'] for x in outputs]
    #     psnr_stack = [x['psnr'] for x in outputs]
    #     avg_loss = torch.stack(loss_stack).mean()
    #     avg_psnr = torch.stack(psnr_stack).mean()
    #     # self.log('train_loss', avg_loss, logger=True)
    #     # self.log('train_psnr', avg_psnr, logger=True)
    def validation_step(self, val_batch, batch_idx):

        img_moire, img_clean = val_batch
        # print(img_moire.shape, img_clean.shape)
        prior_moired, extracted_moired = self.forward(img_moire, test=True)
        _, extracted_clean = self.forward(img_clean, test=True)

        loss = self.loss(extracted_moired, extracted_clean, prior_moired)
        self.log("val_att_loss", loss, logger=True, on_epoch=True)

        if self.global_rank == 0:
            ipt, ipt_clean = tensor2img(img_moire), tensor2img(img_clean)
            
            img = tensor2img(prior_moired)
            target = tensor2img(prior_moired)

            imsave(img, f"{self.config['save_dir']}/prior/eval__{batch_idx}.png")
            imsave(target, f"{self.config['save_dir']}/target/eval__{batch_idx}.png")
            imsave(ipt, f"{self.config['save_dir']}/input/eval__{batch_idx}.png")
            imsave(ipt_clean, f"{self.config['save_dir']}/input_clean/eval__{batch_idx}.png")

    def test_step(self, test_batch, batch_idx):

        img_moire, img_clean = test_batch

        prior_moired, extracted_moired = self.forward(img_moire, test=True)
        _, extracted_clean = self.forward(img_clean, test=True)

        loss = self.loss(extracted_moired, extracted_clean, prior_moired)
        self.log("test_att_loss", loss, logger=True, on_epoch=True)
        

