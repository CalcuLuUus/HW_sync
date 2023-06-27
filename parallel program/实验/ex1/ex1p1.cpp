#include <iostream>
#include <cstdlib>
#include <pthread.h>
#include <math.h>
#include <iomanip>
#include <sys/time.h>

using namespace std;

const int MAXN = 1e7;
const int MAXTHREADNUM = 1000;
int n = MAXN;
int threadnum;
double sum = 0;
pthread_mutex_t mutex;


void *calculate_pi(void *threadid)
{
    int id = *((int *)threadid);
    int len_per_thread = n / threadnum;

    int st = id * len_per_thread;
    int ed = (id + 1) * len_per_thread;

    double thread_sum = 0;

    for(int i = st; i < ed; i++)
    {
        double divided = 4;
        double divisor = 1 + pow((i + 0.5) / n, 2);
        double result = divided / divisor;
        thread_sum += result;
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
    if(argc != 2)
    {
        cout << "Error" << endl;
    }

    threadnum = atoi(argv[1]);
    pthread_t tid[MAXTHREADNUM];
    int index[MAXTHREADNUM] = {0};
    pthread_mutex_init(&mutex, NULL);

    struct timeval begin, end;
    gettimeofday(&begin, 0);

    for(int i = 0; i < threadnum; i++)
    {
        index[i] = i;
        int ret = pthread_create(&tid[i], NULL, calculate_pi, (void *)&(index[i]));
    }
    
    for(int i = 0; i < threadnum; i++)
    {
        pthread_join(tid[i], NULL);
    }
    
    gettimeofday(&end, 0);

    cout << "Parallel task: Integral method of calculating pi, N = " << n << endl;
    cout << "Num of threads: " << threadnum << endl;
    cout << "Time: " << cal_time(begin, end) << endl;
    cout << fixed << setprecision(12) << sum / n << endl;
    return 0;

}


