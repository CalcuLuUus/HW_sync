import torch
import kornia


def bbox_to_ptr(bbox, shape):
    h, w = shape
    ul = bbox[:, 0:2].unsqueeze(-2)
    dr = ul + bbox[:, 2:4].unsqueeze(-2)
    dr = torch.clamp_max_(dr, h)
    ur = torch.cat([dr[..., 0], ul[..., 1]], dim=-1).unsqueeze(-2)
    dl = torch.cat([ul[..., 0], dr[..., 1]], dim=-1).unsqueeze(-2)
    return torch.cat([ul, ur, dr, dl], dim=-2)


def _render(bbox, deg, rgb_img, gt_bbox, gt_trans, shape):
    gt = kornia.geometry.transform.crop_and_resize(rgb_img, gt_bbox, shape)
    pred = kornia.geometry.transform.crop_and_resize(rgb_img, bbox, shape)
    return gt, pred


def _render_single(rgb_img, bbox, shape):
    rendered = kornia.geometry.transform.crop_and_resize(rgb_img, bbox, shape)
    return rendered

def _get_uldr(bbox, shape):
    h, w = shape
    ul = bbox[:, 0:2].unsqueeze(-2)
    dr = ul + bbox[:, 2:4].unsqueeze(-2)
    dr = torch.clamp_max_(dr, h)
    # print(ul.shape, dr.shape,"uldr")
    return torch.cat([ul, dr], dim=-1)



#rm ./train2017_depth_est/000000391145.png
#rm ./train2017_depth_est/000000391253.png