# train
python train.py --dataroot /media/xteam/50288241-ca51-4873-8254-a45e54621c55/datasets/TIP2018/trainData \
--dataset_mode TIP2018 \
--model cycle_gan_mpe \
--name CycleGANMpeModel \
--netG unet_256 \
--batch_size 2
