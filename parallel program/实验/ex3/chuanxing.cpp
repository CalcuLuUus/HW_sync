#include <iostream>
#include <pthread.h>
#include <cstring>
#include <cmath>
#include <assert.h>

using namespace std;

const int MAXN = 2048;
const int ks = 3;
const int N = 5;

int img[MAXN][MAXN] = {};
int result[MAXN][MAXN];
int filter[ks][ks] = {-1, -1, -1,
                      -1, 4, -1,
                      -1, -1, -1};

void conv2d(int **img, int **result, int row, int col, bool last)
{
    if (!last)
        row += 2;
    for (int i = 0; i < row - ks + 1; i++)
    {
        for (int j = 0; j < col - ks + 1; j++)
        {
            int sum = 0;
            for (int ki = 0; ki < ks; ki++)
            {
                for (int kj = 0; kj < ks; kj++)
                {
                    sum += filter[ki][kj] * img[i + ki][j + kj];
                }
            }
            result[i][j] = sum;
        }
    }
}

void print_img()
{
    for (int i = 0; i < MAXN; i++)
    {
        for (int j = 0; j < MAXN; j++)
        {
            cout << img[i][j] << '\t';
        }
        cout << endl;
    }
}

int main(int argc, char *argv[])
{

    for (int iter = 0; iter < N; iter++)
    {
        int **a = (int **)malloc(sizeof(int *) * MAXN);
        for (int i = 0; i < MAXN; i++)
        {
            a[i] = (int *)malloc(sizeof(int) * MAXN);
            for (int j = 0; j < MAXN; j++)
            {
                img[i][j] = rand() % 100;
                a[i][j] = img[i][j];
            }
        }

        int **res = (int **)malloc(sizeof(int *) * MAXN);
        for (int i = 0; i < MAXN; i++)
        {
            res[i] = (int *)malloc(sizeof(int) * MAXN);
            memset(res[i], 0, sizeof(res[i]));
        }

        conv2d(a, res, MAXN, MAXN, true);

        int st_row = 0;

        for (int i = 0; i < MAXN - ks + 1; i++)
        {
            for (int j = 0; j < MAXN - ks + 1; j++)
            {
                img[i + st_row + 1][j + 1] = res[i][j];
            }
        }
    }

    print_img();

    return 0;
}