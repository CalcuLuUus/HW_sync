# CUT -- For img2img Translation

## Version.beta

- for code test
- mpe v3
- wrong NCE_loss
- test 50 images ==> PSNR:18.31 SSIM:0.64

## Version1.0

- fix NCE_loss
- add PSNR and SSIM
- little info in visdom
- mpp version BG_loss
- mpe v4
- 100 iter
- test 50 images ==> PSNR:19.44 SSIM:0.65

## Version1.0.true

- add some info in visdom
- mpe version BG_loss
- mpe v4
- 100 iter
- test 50 images ==> PNSR:18.36 SSIM:0.62

## Version2.0

- fix adv_loss
- mpe version BG_loss
- add COL_loss, lambda == 5
- add idt COL_loss
- mpe v4
- 40k iter ==> PNSR:18.16

## Version2.1

- mpp version BG_Loss
- COL_loss lambda == 5
- mpe v4
- 40k iter ==> PNSR:19.20 SSIM:0.66

## Version3.0

- fix code
- fix col loss
- mpe v4
- crop 256 \* 256