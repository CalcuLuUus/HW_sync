#include <iostream>
#include <cstdlib>
#include <math.h>
#include <iomanip>
#include <sys/time.h>
#include <vector>

using namespace std;

const int MAXN = 1e7;
int n = MAXN;
int sum = 0;
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

void calculate_pi()
{
    int st = 0, ed = n;

    for(int i = st; i < ed; i++)
    {
        double dis = sqrt(x[i]*x[i] +  y[i]*y[i]);
        if(dis <= 1)
        {
            sum ++;
        }
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
    
    init();
    calculate_pi();
    
    gettimeofday(&end, 0);

    cout << "Serial task: Probabilistic method of calculating pi, N = " << n << endl;
    cout << "Time: " << cal_time(begin, end) << endl;
    cout << fixed << setprecision(12) << 4.0 * sum / n << endl;

    return 0;
}