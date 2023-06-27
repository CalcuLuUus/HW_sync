#include <iostream>
#include <stdlib.h>
#include <math.h>
#include <iomanip>
#include <pthread.h>
#include <sys/time.h>
#include <vector>

using namespace std;

const int MAXN = 1e7;
const int MAXTHREADNUM = 100;

int n = MAXN;
int sum = 0;
int threadnum = 0;

pthread_mutex_t mutex;

vector<double> x, y;

void init()
{
    srand((unsigned)time(NULL));
    x = vector<double>(n);
    y = vector<double>(n);
    for(int i = 0; i < n; i++)
    {
        x[i] = rand() % 100000000 / 100000000.;
        y[i] = rand() % 100000000 / 100000000.;
    }
}

void *calculate_pi(void *arg)
{
    int id = *((int*)arg);
    int len_per_thread = n / threadnum;
    int st = id * len_per_thread;
    int ed = (id + 1) * len_per_thread;
    int thread_sum = 0;

    for(int i = st; i < ed; i++)
    {
        double dis = sqrt(x[i]*x[i] +  y[i]*y[i]);
        if(dis <= 1)
        {
            thread_sum ++;
        }
    }

    pthread_mutex_lock(&mutex);
    sum += thread_sum;
    pthread_mutex_unlock(&mutex);

    return NULL;
}

double cal_time(struct timeval begin, struct timeval end)
{
    long long second = end.tv_sec - begin.tv_sec;
    long long ms = end.tv_usec - begin.tv_usec;
    return (second + ms * 1e-6);
}

int main(int argc, char *argv[])
{
    if(argc < 2)
    {
        cout << "Error" << endl;
    }
    threadnum = atoi(argv[1]);

    pthread_mutex_init(&mutex, NULL);
    pthread_t tid[MAXTHREADNUM];
    int index[MAXTHREADNUM] = {0};
    
    struct timeval begin, end;
    gettimeofday(&begin, 0);

    init();
    for(int i = 0; i < threadnum; i++)
    {
        index[i] = i;
        int ret = pthread_create(&tid[i], NULL, calculate_pi, (void*)&(index[i]));
    }
    for(int i = 0; i < threadnum; i++)
    {
        pthread_join(tid[i], NULL);
    }
    
    gettimeofday(&end, 0);

    cout << "Parallel task: Probabilistic method of calculating pi, N = " << n << endl;
    cout << "Num of threads: " << threadnum << endl;
    cout << "Time: " << cal_time(begin, end) << endl;
    cout << fixed << setprecision(12) << 4.0 * sum / n << endl;

    return 0;
}
