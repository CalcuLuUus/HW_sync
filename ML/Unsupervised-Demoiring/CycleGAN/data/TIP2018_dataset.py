"""Dataset class template

This module provides a template for users to implement custom datasets.
You can specify '--dataset_mode template' to use this dataset.
The class name should be consistent with both the filename and its dataset_mode option.
The filename should be <dataset_mode>_dataset.py
The class name should be <Dataset_mode>Dataset.py
You need to implement the following functions:
    -- <modify_commandline_options>:　Add dataset-specific options and rewrite default values for existing options.
    -- <__init__>: Initialize this dataset class.
    -- <__getitem__>: Return a data point and its metadata information.
    -- <__len__>: Return the number of images.
"""
from data.base_dataset import BaseDataset, get_transform
# from data.image_folder import make_dataset
# from PIL import Image
from unittest.mock import DEFAULT
import torch
import numpy as np
import cv2
import torch.utils.data as data
import os
from PIL import Image
import random
import torchvision.transforms

DEFAULT_TRANSFORM = torchvision.transforms.Compose([torchvision.transforms.ToTensor()])

class TIP2018Dataset(BaseDataset):
    def __init__(self, opt, shuffle=True, center_size=(512, 512),shift=0, 
                 transform=DEFAULT_TRANSFORM, 
                 target_transform=DEFAULT_TRANSFORM):
        """Initialize this dataset class.

        Parameters:
            opt (Option class) -- stores all the experiment flags; needs to be a subclass of BaseOptions

        A few things can be done here.
        - save the options (have been done in BaseDataset)
        - get image paths and meta information of the dataset.
        - define the image transformation.
        """
        self.root = opt.dataroot
        # save the option and dataset root
        BaseDataset.__init__(self, opt)
        # get the image paths of your dataset;
        self.image_paths = []  # You can call sorted(make_dataset(self.root, opt.max_dataset_size)) to get all the image paths under the directory self.root
        # define the default transform function. You can use <base_dataset.get_transform>; You can also define your custom transform function
        self.transform = transform
        self.target_transform = target_transform
        self.source_imgs = list(filter(lambda x : "source" in x, [os.path.join(self.root, img) for img in os.listdir(self.root)]))
        self.target_imgs = list(map(lambda x : x.replace("source", "target"), self.source_imgs))
        self.shift = shift
        self.center_size = center_size
        
        dataset_length = len(self.source_imgs)
        if shuffle:
          # make sure the two don't share the same permutation for unpaired training
          self.source_imgs_perm = np.random.RandomState(seed=42).permutation(dataset_length)
          self.target_imgs_perm = np.random.RandomState(seed=43).permutation(dataset_length)
          self.source_imgs_perm, self.target_imgs_perm = list(zip(*filter(lambda x : x[0] != x[1], 
                                                              zip(self.source_imgs_perm, self.target_imgs_perm))))
          # print(self.source_imgs_perm)
          self.source_imgs = [self.source_imgs[item] for item in self.source_imgs_perm]
          self.target_imgs = [self.target_imgs[item] for item in self.target_imgs_perm]
          # print(self.source_imgs, self.target_imgs)

    def file_open(self, img_path) : 
        img = cv2.imread(img_path)
        img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
        img = cv2.resize(img, (800, 800))
        return img

    @staticmethod
    def paired_center_crop(img1, img2, size, shift=0) :
        # cropping from the center
        h1, w1 = img1.shape[0], img1.shape[1]
        h2, w2 = img2.shape[0], img2.shape[1]
        th, tw = size
        i1, i2 = int(round((h1 - th) / 2.) + shift), int(round((h2 - th) / 2.) + shift)
        j1, j2 = int(round((w1 - tw) / 2.) + shift), int(round((w2 - tw) / 2.) + shift)
        # print(img1.shape, img2.shape)
        return Image.fromarray(img1[i1:i1+th, j1:j1+tw, :]), \
               Image.fromarray(img2[i2:i2+th, j2:j2+tw, :])


    def file_process(self, img1, img2, path) : 
        img1, img2 = self.paired_center_crop(img1, img2, self.center_size, self.shift)

        file_name = path.split("/")[-1].split("source")[-2]
        
        # print(img1.shape, img2.shape, file_name)
        return self.transform(img1), self.target_transform(img2)#, file_name
    
    
    def __getitem__(self, index):
        """Return a data point and its metadata information.

        Parameters:
            index -- a random integer for data indexing

        Returns:
            a dictionary of data with their names. It usually contains the data itself and its metadata information.

        Step 1: get a random image path: e.g., path = self.image_paths[index]
        Step 2: load your data from the disk: e.g., image = Image.open(path).convert('RGB').
        Step 3: convert your data to a PyTorch tensor. You can use helpder functions such as self.transform. e.g., data = self.transform(image)
        Step 4: return a data point as a dictionary.
        """
        source_img_path, target_img_path = self.source_imgs[index], self.target_imgs[index]
        # print(source_img_path.split("/")[-1].split("source")[-2])
        # print(target_img_path.split("/")[-1].split("target")[-2])
        source_img, target_img = self.file_open(source_img_path), self.file_open(target_img_path)
        A_path = source_img_path
        B_path = target_img_path
        
        data_A, data_B = self.file_process(source_img, target_img, source_img_path)
        return {'A': data_A, 'B': data_B, 'A_paths': A_path, 'B_paths': B_path}

    def __len__(self):
        """Return the total number of images."""
        return len(self.source_imgs)
    