# Metrics/Indexes
#from skimage.measure import compare_ssim, compare_psnr
from skimage.metrics import structural_similarity as compare_ssim
from skimage.metrics import peak_signal_noise_ratio as compare_psnr
from functools import partial
import numpy as np


class Bandwise(object):
    def __init__(self, index_fn):
        self.index_fn = index_fn

    def __call__(self, X, Y):
        C = X.shape[-1]
        bwindex = []
        for ch in range(C):
            x = X[..., ch]
            y = Y[..., ch]
            index = self.index_fn(x, y)
            bwindex.append(index)
        return bwindex


cal_bwpsnr = Bandwise(partial(compare_psnr, data_range=255))
cal_bwssim = Bandwise(partial(compare_ssim, data_range=255))


def compare_ncc(x, y):
    return np.mean((x - np.mean(x)) * (y - np.mean(y))) / (np.std(x) * np.std(y))

def compare_mse(x, y):
    return np.mean(np.square(x - y), dtype=np.float64)

def ssq_error(correct, estimate):
    """Compute the sum-squared-error for an image, where the estimate is
    multiplied by a scalar which minimizes the error. Sums over all pixels
    where mask is True. If the inputs are color, each color channel can be
    rescaled independently."""
    assert correct.ndim == 2
    if np.sum(estimate ** 2) > 1e-5:
        alpha = np.sum(correct * estimate) / np.sum(estimate ** 2)
    else:
        alpha = 0.
    return np.sum((correct - alpha * estimate) ** 2)


def local_error(correct, estimate, window_size, window_shift):
    """Returns the sum of the local sum-squared-errors, where the estimate may
    be rescaled within each local region to minimize the error. The windows are
    window_size x window_size, and they are spaced by window_shift."""
    M, N, C = correct.shape
    ssq = total = 0.
    for c in range(C):
        for i in range(0, M - window_size + 1, window_shift):
            for j in range(0, N - window_size + 1, window_shift):
                correct_curr = correct[i:i + window_size, j:j + window_size, c]
                estimate_curr = estimate[i:i + window_size, j:j + window_size, c]
                ssq += ssq_error(correct_curr, estimate_curr)
                total += np.sum(correct_curr ** 2)
    # assert np.isnan(ssq/total)
    return ssq / total


def quality_assess(x, y):
    # Y: correct; X: estimate
    # psnr = np.mean(cal_bwpsnr(Y, X))
    ssim_albedo = np.mean(cal_bwssim(y, x))
    lmse_albedo = local_error(y, x, 20, 10)
    mse_albedo = compare_mse(y, x)
    # ncc = compare_ncc(Y, X)
    return {'SSIM' : ssim_albedo,
            'LMSE': lmse_albedo,
            'MSE': mse_albedo}

    
