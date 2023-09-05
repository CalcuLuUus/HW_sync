import os
import torch
import kornia
import argparse
import time
from pytorch_lightning.loggers import CometLogger
from torch.utils.data import DataLoader
from torch.utils.data import random_split
import pytorch_lightning as pl
import torch.nn as nn
import torch.optim as optim
from data.TIP2018Dataset import TIP2018Dataset
from mpe_training_task import MoirePatternExtraction
import torchvision

def main():
    parser = argparse.ArgumentParser(description="Yet another code-base for ytmt.")
    parser.add_argument("--name", default="moire_pattern")
    parser.add_argument("--bs", default=4, type=int)
    parser.add_argument("--save_dir", default='./')
    parser.add_argument("--base_lr", default=1e-3, type=float)
    parser.add_argument("--max_epoch", default=120, type=int)
    parser.add_argument("--proj_name", default="dejpeg")
    parser.add_argument("--inet", default='ytmt_nafnet')
    parser.add_argument("--ckpt", default="results")
    parser.add_argument("--resume", default=None)
    parser.add_argument("--fp16", action="store_true", help="If passed, will use FP16 training.")
    parser.add_argument("--test", action="store_true")
    parser.add_argument("--loss_metric", default="l1")
    parser.add_argument(
    "--mixed_precision",
    type=str,
    default="no",
    choices=["no", "fp16", "bf16"],
    help="Whether to use mixed precision. Choose"
            "between fp16 and bf16 (bfloat16). Bf16 requires PyTorch >= 1.10."
            "and an Nvidia Ampere GPU.",
    )
    parser.add_argument("--device_type", default='tpu', help="If passed, will train on the CPU.")
    parser.add_argument("--logger", default='tb')
    args = parser.parse_args()
    config = {"base_lr": args.base_lr, "max_epoch": args.max_epoch, "batch_size": args.bs, 
            "eps": 1e-2, "logdir" : args.ckpt.rstrip('/\\') + '/' + args.name,
            "inet" : args.inet, "project_name" : args.proj_name,
            'name' : args.name + str(time.time()),
            'logger' : args.logger if args.logger is not True else "tb",
            "save_dir" : args.save_dir,
            "loss_metric" : args.loss_metric,
            }

    os.makedirs(config["logdir"], exist_ok=True)
    train_transform =  torchvision.transforms.Compose([
        torchvision.transforms.RandomHorizontalFlip(),
        torchvision.transforms.RandomCrop((384, 384)),
        torchvision.transforms.ToTensor()
    ])
    train_target_transform = torchvision.transforms.Compose([
        torchvision.transforms.RandomHorizontalFlip(),
        torchvision.transforms.RandomCrop((384, 384)),
        torchvision.transforms.ToTensor()
    ])
    dataset = TIP2018Dataset(root='/home/xteam/dataset/TIPDataset/trainData', 
                             transform=train_transform, 
                             target_transform=train_target_transform)
    val_dataset = TIP2018Dataset(root='/home/xteam/dataset/TIPDataset/testData', shuffle=False)
    test_dataset = TIP2018Dataset(root='/home/xteam/dataset/TIPDataset/testData', shuffle=False)
    
    train_loader = DataLoader(dataset, batch_size=args.bs, num_workers=4, persistent_workers=True, shuffle=True)
    val_loader = DataLoader(val_dataset, batch_size=1, num_workers=4, persistent_workers=True, shuffle=False)
    test_loader = DataLoader(test_dataset, batch_size=1, num_workers=4, persistent_workers=True, shuffle=False)
    # model
    model = MoirePatternExtraction(config)
    if args.logger == "comet":
        logger = CometLogger(
            api_key="I0V4FpUeN5xJlGAYhRz9VHtmY",
            workspace="lime-j",  # Optional
            project_name=config["project_name"],  # Optional
            experiment_name= config["name"]
        )
    elif args.logger == 'tb':
        logger = True

    trainer = pl.Trainer(gpus=[0, 1], max_epochs=config['max_epoch'], 
                    logger=logger,
                    num_sanity_val_steps=2,  resume_from_checkpoint=args.resume, 
                    callbacks=[pl.callbacks.ModelCheckpoint(dirpath=args.proj_name + "/" + args.name + "/", 
                                                            every_n_train_steps=2000),])

    if args.test: 
        trainer.test(model, test_loader, ckpt_path=args.resume)
    else :
        trainer.fit(model, train_loader, val_loader)  

if __name__ == '__main__':
    main()
