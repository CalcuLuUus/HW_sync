#include <iostream>
#include <pthread.h>
#include <cstring>
#include <cmath>
#include <assert.h>
#include <omp.h>
#include <mpi.h>
#include <sys/time.h>

using namespace std;

const int MAXN = 2048;
const int ks = 3;
const int N = 10;

int t; // thread_num
int Image[MAXN][MAXN] = {0, 0, 0, 0, 0, 0, 0, 0,
                       0, 3, 6, 7, 5, 3, 0, 0,
                       0, 5, 6, 2, 9, 1, 0, 0,
                       0, 2, 7, 0, 9, 3, 0, 0,
                       0, 6, 0, 6, 2, 6, 0, 0,
                       0, 1, 8, 7, 9, 2, 0, 0,
                       0, 0, 0, 0, 0, 0, 0, 0,
                       0, 0, 0, 0, 0, 0, 0, 0};
int result[MAXN][MAXN];
int filter[ks][ks] = {-1, -1, -1,
                      -1, 4, -1,
                      -1, -1, -1};


void conv2d(int** img, int **result, int row, int col, bool last)
{
    if(!last) row+=2;
    #pragma omp parallel for num_threads(t)
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


void print_img(int **img)
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

int main(int argc, char *argv[])
{
    t = atoi(argv[1]);
    int size = 0;
    int my_rank = 0;
    int row = 0;
    int id = 0;
    int root = 0;
    struct timeval begin, end;

    bool need_init = true;
    int **img = NULL;
    img = (int **)malloc(sizeof(int *) * (MAXN));
    int *imgdata = (int *)malloc(sizeof(int) * MAXN * MAXN);
    for (int i = 0; i < MAXN; i++) 
    {
        img[i] = imgdata + i * MAXN;
    }


    int row_per_process = 0;

    // ------------------ start OpenMPI ------------------------

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &size);
    MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);


    row_per_process = MAXN / size;
    assert(MAXN % size == 0);

    if(row_per_process < 2)
    {
        cout << "TOO MANY PROCESSES" << endl;
        return 0;
    }

    row = row_per_process;
    id = my_rank;
    
    // if it is the first time run this program, img[][] need to be init
    if(id == root && need_init)
    {
        
        gettimeofday(&begin, 0);
        need_init = false;
        for(int i = 0; i < MAXN; i++)
        {
            for(int j = 0; j < MAXN; j++)
            {
                img[i][j] = rand() % 100;
            }
        }
    }

    for(int iter = 0; iter < N; iter++)
    {
        MPI_Bcast(&img[0][0], MAXN*MAXN, MPI_INT, root, MPI_COMM_WORLD); // root process Bcast the content of img

        /*
        * **a is a temp array to save data from st_row to ed_row in order to help calculate conv2d
        */
        int **a = (int **)malloc(sizeof(int *) * (row + 2));
        for(int i = 0; i < row + 2; i++)
        {
            a[i] = (int *)malloc(sizeof(int) * MAXN);
            memset(a[i], 0, sizeof(int) * MAXN);
        }

        int st_row = id * row_per_process;
        int ed_row = st_row + row;

        for(int i = st_row; i < ed_row; i++)
        {
            for(int j = 0; j < MAXN; j++)
            {
                a[i-st_row][j] = img[i][j];
            }
        }

        /*
        * calculate conv2d need data of other process, use MPI_Sendrecv to trans the data which needed
        */
        int send_to = id - 1;
        int receive_from = id + 1;

        if(id == 0)
        {
            send_to = MPI_PROC_NULL;
        }
        if(id == size - 1)
        {
            receive_from = MPI_PROC_NULL;
        }

        int tag1 = 1;

        MPI_Sendrecv(a[0], MAXN, MPI_INT, send_to, tag1, a[row], MAXN, MPI_INT, receive_from, tag1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Sendrecv(a[1], MAXN, MPI_INT, send_to, tag1, a[row+1], MAXN, MPI_INT, receive_from, tag1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

        // for debug
        // string check_content = "Content of id: " + to_string(id) + "\n";
        

        /**
         * res[][] to save the result of conv2d from st_row to ed_row
        */
        int **res = (int **)malloc(sizeof(int *) * (row));
        int *data = (int *)malloc(sizeof(int) * row * MAXN);
        for (int i = 0; i < row; i++) 
        {
            res[i] = data + i * MAXN;
        }
        for(int i = 0; i < row; i++)
        {
            for(int j = 0; j < MAXN; j++)
            {
                res[i][j] = 0;
            }
        }

        conv2d(a, res, row, MAXN, id==size-1);

        MPI_Barrier(MPI_COMM_WORLD); // waiting for all process


        // for debug
        // for(int i = 0; i < row; i++)
        // {
        //     for(int j = 0; j < MAXN; j++)
        //     {
        //         // img[i + st_row + 1][j + 1] = res[i][j];
        //         check_content += to_string(res[i][j]) + "\t";
        //     }
        //     check_content += "\n";
        // }

        MPI_Gather(res[0], row*MAXN, MPI_INT, img[0], row*MAXN, MPI_INT, root, MPI_COMM_WORLD); // all processes send res to root process


        if(id == 0)
        {
            for(int i = MAXN - 1; i >= 0; i--)
            {
                for(int j = MAXN - 1; j >= 0; j--)
                {
                    if(i-1 < 0) img[i][j] = 0;
                    else if(j-1 < 0) img[i][j] = 0;
                    else img[i][j] = img[i-1][j-1];
                }
            }
        }


        free(a);
        free(res);
        free(data);
        
    }

    if(id == 0)
    {
        // cout << "======================= RESULT ============================" << endl;
        gettimeofday(&end, 0);

        double p_time = cal_time(begin, end);
        cout << "Process_num: " << size << " Thread_num: " << t << ' ';
        cout << "TIME: " << p_time << endl;;
        // print_img(img);
    }

    // MPI_Finalize(); //
    // print_img();

    return 0;
}