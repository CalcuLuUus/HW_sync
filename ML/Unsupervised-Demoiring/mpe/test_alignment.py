import torch
# import kornia as K 
import cv2

def paired_center_crop(img1, img2, size) :
    # cropping from the center
    h, w = img1.shape[0], img1.shape[1]
    th, tw = size
    i = int(round((h - th) / 2.))
    j = int(round((w - tw) / 2.))
    return img1[i:i+th, j:j+tw, :], img2[i:i+th, j:j+tw, :]


source_image = "./image_test_part001_00002178_source.png"
target_image = "./image_test_part001_00002178_target.png"

image_s = cv2.imread(source_image)
print(image_s.shape)
image_t = cv2.imread(target_image)
image_s, image_t = paired_center_crop(image_s, image_t, (512, 512))

cv2.imwrite("minus.png", image_s)