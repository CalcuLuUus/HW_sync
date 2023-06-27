#include <iostream>
#include <cstdlib>
#include <math.h>
#include <iomanip>
#include <sys/time.h>

using namespace std;

const int MAXN = 1e7;
int n = MAXN;
double sum = 0;


void calculate_pi()
{
    int st = 0, ed = n;

    for(int i = st; i < ed; i++)
    {
        double divided = 4;
        double divisor = 1 + pow((i + 0.5) / n, 2);
        double result = divided / divisor;
        sum += result;
    }
}

double cal_time(struct timeval begin, struct timeval end)
{
    long long second = end.tv_sec - begin.tv_sec;
    long long ms = end.tv_usec - begin.tv_usec;
    return (second + ms * 1e-6);
}

int main(int argc, char *argv[])
{
    struct timeval begin, end;
    gettimeofday(&begin, 0);
    
    calculate_pi();
    
    gettimeofday(&end, 0);

    cout << "Serial task: Integral method of calculating pi, N = " << n << endl;
    cout << "Time: " << cal_time(begin, end) << endl;
    cout << fixed << setprecision(12) << sum / n << endl;

    return 0;
}