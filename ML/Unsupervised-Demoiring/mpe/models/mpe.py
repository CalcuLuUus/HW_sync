from turtle import forward
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim

from .DeepFSPIS import DeepFSPIS

class LayerNorm(nn.Module):
    r""" LayerNorm that supports two data formats: channels_last (default) or channels_first. 
    The ordering of the dimensions in the inputs. channels_last corresponds to inputs with 
    shape (batch_size, height, width, channels) while channels_first corresponds to inputs 
    with shape (batch_size, channels, height, width).
    """
    def __init__(self, normalized_shape, eps=1e-6, data_format="channels_last"):
        super().__init__()
        self.weight = nn.Parameter(torch.ones(normalized_shape))
        self.bias = nn.Parameter(torch.zeros(normalized_shape))
        self.eps = eps
        self.data_format = data_format
        if self.data_format not in ["channels_last", "channels_first"]:
            raise NotImplementedError 
        self.normalized_shape = (normalized_shape, )
    
    def forward(self, x):
        if self.data_format == "channels_last":
            return F.layer_norm(x, self.normalized_shape, self.weight, self.bias, self.eps)
        elif self.data_format == "channels_first":
            u = x.mean(1, keepdim=True)
            s = (x - u).pow(2).mean(1, keepdim=True)
            x = (x - u) / torch.sqrt(s + self.eps)
            x = self.weight[:, None, None] * x + self.bias[:, None, None]
            return x


class ConvNextBlock(nn.Module):
    r""" ConvNeXt Block. There are two equivalent implementations:
    (1) DwConv -> LayerNorm (channels_first) -> 1x1 Conv -> GELU -> 1x1 Conv; all in (N, C, H, W)
    (2) DwConv -> Permute to (N, H, W, C); LayerNorm (channels_last) -> Linear -> GELU -> Linear; Permute back
    We use (2) as we find it slightly faster in PyTorch
    
    Args:
        dim (int): Number of input channels.
        drop_path (float): Stochastic depth rate. Default: 0.0
        layer_scale_init_value (float): Init value for Layer Scale. Default: 1e-6.
    """
    def __init__(self, dim, drop_path=0., layer_scale_init_value=1e-6):
        super().__init__()
        self.dwconv = nn.Conv2d(dim, dim, kernel_size=7, padding=3, groups=dim) # depthwise conv
        self.norm = nn.InstanceNorm2d(dim)
        self.pwconv1 = nn.Conv2d(dim, 2 * dim, kernel_size=1, padding=0) #nn.Linear(dim, 4 * dim) # pointwise/1x1 convs, implemented with linear layers
        self.act = nn.GELU()
        self.pwconv2 = nn.Conv2d(2 * dim, dim, kernel_size=1, padding=0) # nn.linear(4 * dim, dim)
        # self.gamma = nn.Parameter(layer_scale_init_value * torch.ones((dim)), 
        #                             requires_grad=True) if layer_scale_init_value > 0 else None
    def forward(self, x):
        input = x
        x = self.dwconv(x)
        # x = x.permute(0, 2, 3, 1) # (N, C, H, W) -> (N, H, W, C)
        x = self.norm(x)
        x = self.pwconv1(x)
        x = self.act(x)
        x = self.pwconv2(x)
        # if self.gamma is not None:
        #     x = self.gamma * x
        # x = x.permute(0, 3, 1, 2) # (N, H, W, C) -> (N, C, H, W)
        x = input + x 
        return x

class ConvBlock(nn.Module) :
    def __init__(self, in_channels, out_channels, kernel_size, stride, padding, dil=1) :
        super(ConvBlock, self).__init__()
        self.conv = nn.Conv2d(in_channels, out_channels, kernel_size, stride, padding, dilation=dil)
        self.bn = nn.Identity() #nn.InstanceNorm2d(out_channels)
        self.relu = nn.ReLU(True)
        
    def forward(self, x) :
        out = self.conv(x)
        out = self.bn(out)
        out = self.relu(out)
        return out

class ResidualBlock(nn.Module) :
    def __init__(self, in_channels, out_channels, kernel_size, stride, padding, dil=1) :
        super(ResidualBlock, self).__init__()
        self.conv1 = nn.Conv2d(in_channels, out_channels, kernel_size, stride, padding, dilation=dil, groups=out_channels)
        self.bn1 = nn.Identity() # nn.InstanceNorm2d(out_channels)
        self.conv2 = nn.Conv2d(out_channels, out_channels, 1, stride, 0)
        self.bn2 = nn.Identity() #nn.InstanceNorm2d(out_channels)
        self.relu = nn.ReLU(inplace=True)

    
    def forward(self, x):
        out = self.conv1(x)
        out = self.bn1(out)
        out = self.relu(out)
        out = self.conv2(out)
        out = self.bn2(out)
        out += x
        return self.relu(out)

class ConvGRU(nn.Module):
    def __init__(self, hidden_dim=128, input_dim=192+128):
        super(ConvGRU, self).__init__()
        self.convz = nn.Conv2d(hidden_dim+input_dim, hidden_dim, 3, padding=1)
        self.convr = nn.Conv2d(hidden_dim+input_dim, hidden_dim, 3, padding=1)
        self.convq = nn.Conv2d(hidden_dim+input_dim, hidden_dim, 3, padding=1)

    def forward(self, h, x):
        hx = torch.cat([h, x], dim=1)

        z = torch.sigmoid(self.convz(hx))
        r = torch.sigmoid(self.convr(hx))
        q = torch.tanh(self.convq(torch.cat([r*h, x], dim=1)))

        h = (1-z) * h + z * q
        return h

class MoirePatternExtractor(nn.Module):
    def __init__(self, input_channel = 3, inner_channel=64, iter_len = 3, conv_len = 4):
        super(MoirePatternExtractor, self).__init__()
        self.input_channel = input_channel
        self.iter_len = iter_len
        
        self.intro = ConvBlock(input_channel, inner_channel, 3, 1, 1)
        
        self.stem = nn.Sequential(*[ConvBlock(inner_channel, inner_channel, 3, 1, 3, dil=3) for _ in range(conv_len)])
        # self.stem = nn.Sequential(*[ResidualBlock(inner_channel, inner_channel, 3, 1, 3, dil=3) for _ in range(conv_len)]) 
        self.gru = ConvGRU(hidden_dim=inner_channel, input_dim=inner_channel)
        self.decoding = ConvBlock(inner_channel, 1, 3, 1, 1)

    def forward(self, x, side_output=True):
        outputs = []
        out = self.intro(x)
    
        for _ in range(self.iter_len) :
            curr_out = self.stem(out)
            out = self.gru(out, curr_out)
            outputs.append(self.decoding(out))

        return outputs if side_output else outputs[-1]
        

        
class MoirePatternPrior(nn.Module):
    def __init__(self, input_channel = 3) :
        super(MoirePatternPrior, self).__init__()
        self.smoother = DeepFSPIS()
        # self.extractor = MoirePatternExtractor(input_channel=input_channel)
    
    def forward(self, x, lamb = 0.8, side_output=False):
        # print(x.shape)
        structure_image = self.smoother(x, lamb)
        residual_image = x - structure_image
        
        r, g, b = torch.split(residual_image, 1, dim=1)
        prior_mask = (r.ge((15 / 255)) & b.ge((15 / 255)) & g.le(15 / 255)) |\
                     (r.ge((15 / 255)) & b.le((15 / 255)) & g.le(15 / 255)) |\
                     (r.le((15 / 255)) & b.ge((15 / 255)) & g.le(15 / 255)) |\
                     (r.le((15 / 255)) & b.le((15 / 255)) & g.ge(15 / 255)) 
        # print(prior_mask.shape)
        # prior_mask = ((r.ge(15 / 255))  (b.ge(15 / 255))) + (g.ge(15 / 255))
        if not side_output : return prior_mask.float()
        else: return prior_mask.float(), structure_image
if __name__ == '__main__' :
    from TIP2018Dataset import TIP2018Dataset
    from torch.utils.data import DataLoader
    import os
    from utils.image_utils import imsave, tensor2img
    mpe = MoirePatternExtractor()
    
    #print(torch.load('../mpe_v3_94k.ckpt')['state_dict'].keys())
    # state_dict = torch.load('../mpe_v3_94k.ckpt')['state_dict']
    state_dict = torch.load('../mpe_v4_92k.ckpt')['state_dict']
    
    state_dict = {".".join(k.split(".")[1:]): state_dict[k] for k in filter(lambda x : "extractor" in x, state_dict.keys())}

    print(state_dict.keys())
    mpe.load_state_dict(state_dict)
    mpe = mpe.to('cuda:0')
    mpe.eval()
    mpp = MoirePatternPrior()
    
    eval_length = 50
    eval_dir = "../../dataset/TIPDataset/testData"
    save_dir = "./result_v4"
    os.makedirs(save_dir, exist_ok=True)
    eval_dataset = TIP2018Dataset(eval_dir, shuffle=False)
    eval_loader = DataLoader(eval_dataset, batch_size=1, shuffle=False, num_workers=1)
    clean_metric = 0
    for item in eval_loader :
        eval_length -= 1
        if eval_length == 0 : break
        with torch.no_grad():
            img_moire, img_clean = item
            img_moire = img_moire.to('cuda:0')
            img_clean = img_clean.to('cuda:0')
            img_go = torch.cat([img_moire, img_clean], dim=0)
            print(img_go.shape)
            # with torch.no_grad():
            #     prior, smoothed_image = mpp(img_go, side_output=True)
            extracted = mpe(img_go, side_output=False)
            clean_metric += torch.mean(extracted[1])
        ipt = tensor2img(img_moire[0].detach().cpu())
        img = tensor2img(extracted[0].detach().cpu())
        img_clean = tensor2img(extracted[1].detach().cpu())
        imsave(ipt, f"{save_dir}/test_{eval_length}_moired_extracted.png")
        imsave(img, f"{save_dir}/test_{eval_length}_mpe.png")
        imsave(img_clean, f"{save_dir}/test_{eval_length}_clean_extracted.png")


    print('passed with clean_metric %.4f' % clean_metric)


