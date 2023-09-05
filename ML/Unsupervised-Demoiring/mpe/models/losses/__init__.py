# I'm sorry for your loss!

import torch
import torch.nn as nn


class AttLoss(nn.Module):
    def __init__(self, criterion=nn.L1Loss):
        super(AttLoss, self).__init__()
        self.criterion = criterion()
        
    def forward(self, moired_mask_prior, moired_mask_pred, clean_mask_pred):
        # The two image can't be paired clean-moire\'d !
        
        all_ones = torch.zeros_like(clean_mask_pred, device=clean_mask_pred.device)
        return self.criterion(clean_mask_pred, all_ones) + \
               self.criterion(moired_mask_prior, moired_mask_pred)

class Norm(nn.Module):
    def __init__(self,  criterion=nn.L1Loss):
        super(Norm, self).__init__()
        self.criterion = criterion() 

    def forward(self, to_be_normed):
        # The two image can't be paired clean-moire\'d !
        all_ones = torch.zeros_like(to_be_normed, device=to_be_normed.device)
        return self.criterion(to_be_normed, all_ones)

