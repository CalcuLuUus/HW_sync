#include <iostream>
#include <pthread.h>
#include <cstring>
#include <cstdlib>
#include <assert.h>
#include <sys/time.h>

using namespace std;

const int MAXN = 258;
const int ks = 3;
const int N = 3;

int thread_num;

int img[MAXN][MAXN];
int filter[ks][ks];
int per_thread_rows;

pthread_barrier_t barrier;

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

    per_thread_rows = (MAXN - ks + 1) / thread_num;
    assert((MAXN - ks + 1) % thread_num == 0);
    pthread_barrier_init(&barrier, NULL, thread_num);
}

pthread_mutex_t mutex;

void *conv2d(void *arg)
{
    int id = *((int *)arg);
    int start_row = id * per_thread_rows;
    int end_row = (id + 1) * per_thread_rows;

    int **result = (int **)malloc(sizeof(int *) * per_thread_rows);
    for (int i = 0; i < per_thread_rows; i++)
    {
        result[i] = (int *)malloc(sizeof(int) * (MAXN - ks + 1));
    }

    for (int iter = 0; iter < N; iter++)
    {
        for (int i = 0; i < per_thread_rows; i++)
        {
            for (int j = 0; j < MAXN - ks + 1; j++)
            {
                int sum = 0;
                for (int ki = 0; ki < ks; ki++)
                {
                    for (int kj = 0; kj < ks; kj++)
                    {
                        sum += filter[ki][kj] * img[i + ki + start_row][j + kj];
                    }
                }
                result[i][j] = sum;
            }
        }

        pthread_barrier_wait(&barrier);

        for(int i = 0; i < per_thread_rows; i++)
        {
            for(int j = 0; j < MAXN - ks + 1; j++)
            {
                img[i+1+start_row][j+1] = result[i][j];
            }
        }

        pthread_barrier_wait(&barrier);
    }
    return NULL;
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

int main(int argc, char* argv[])
{
    double s_time = 0.01608;
    thread_num = atoi(argv[1]);
    init();
    pthread_t tid[thread_num];
    int ind[thread_num];

    struct timeval begin, end;
    gettimeofday(&begin, 0);

    for (int i = 0; i < thread_num; i++)
    {
        ind[i] = i;
        pthread_create(&tid[i], NULL, conv2d, (void *)&(ind[i]));
    }

    for(int i = 0; i < thread_num; i++)
    {
        pthread_join(tid[i], NULL);
    }

    gettimeofday(&end, 0);

    double p_time = cal_time(begin, end);
    cout << "Thread_num: " << thread_num << ' ';
    cout << "TIME: " << p_time << " Acc rate: " << s_time / p_time << " eff: " << s_time / p_time / thread_num << endl;
    return 0;
}

