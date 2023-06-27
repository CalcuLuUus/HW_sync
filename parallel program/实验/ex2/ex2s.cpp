#include <iostream>
#include <pthread.h>
#include <cstring>
#include <sys/time.h>
using namespace std;

const int MAXN = 258;
const int ks = 3;
const int N = 3;



int img[MAXN][MAXN];
int filter[ks][ks];

void init()
{
    for (int i = 0; i < MAXN; i++)
    {
        for (int j = 0; j < MAXN; j++)
        {
            if (i == 0 || i == MAXN - 1 || j == 0 || j == MAXN - 1)
            {
                img[i][j] = 0;
                continue;
            }
            img[i][j] = rand() % 10;
        }
    }

    for (int i = 0; i < ks; i++)
    {
        for (int j = 0; j < ks; j++)
        {
            filter[i][j] = -1;
        }
    }
    filter[1][1] = 9;
}

void conv2d()
{   
    int **result = (int **)malloc(sizeof(int *) * (MAXN - ks + 1));
    for (int i = 0; i < (MAXN - ks + 1); i++)
    {
        result[i] = (int *)malloc(sizeof(int) * (MAXN - ks + 1));
    }

    for (int iter = 0; iter < N; iter++)
    {
        for (int i = 0; i < MAXN - ks + 1; i++)
        {
            for (int j = 0; j < MAXN - ks + 1; j++)
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

        for(int i = 0; i < MAXN - ks + 1; i++)
        {
            for(int j = 0; j < MAXN - ks + 1; j++)
            {
                img[i+1][j+1] = result[i][j];
            }
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

double cal_time(struct timeval begin, struct timeval end)
{
    long long second = end.tv_sec - begin.tv_sec;
    long long ms = end.tv_usec - begin.tv_usec;
    return (second + ms * 1e-6);
}

int main()
{
    init();

    struct timeval begin, end;
    gettimeofday(&begin, 0);

    conv2d();
    
    gettimeofday(&end, 0);

    double p_time = cal_time(begin, end);
    cout << "Result: " << ' ';
    cout << "TIME: " << cal_time(begin, end) << endl;
    return 0;
}