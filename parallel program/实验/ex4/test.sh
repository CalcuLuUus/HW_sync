#!/bin/sh
module load openmpi/mpi-x-gcc9.3.0
#
echo "1 process 1 thread" > log.txt
yhrun -p thcp1 -N 1 -n 1 -c 1 ./ex.o 1 >> log.txt
#
echo "1 process N thread" >> log.txt
yhrun -p thcp1 -N 1 -n 1 -c 2 ./ex.o 2 >> log.txt
yhrun -p thcp1 -N 1 -n 1 -c 4 ./ex.o 4 >> log.txt
yhrun -p thcp1 -N 1 -n 1 -c 8 ./ex.o 8 >> log.txt
yhrun -p thcp1 -N 1 -n 1 -c 16 ./ex.o 16 >> log.txt
yhrun -p thcp1 -N 1 -n 1 -c 32 ./ex.o 32 >> log.txt
#
echo "N process 1 thread" >> log.txt
yhrun -p thcp1 -N 2 -n 2 -c 1 ./ex.o 1 >> log.txt
yhrun -p thcp1 -N 2 -n 4 -c 1 ./ex.o 1 >> log.txt
yhrun -p thcp1 -N 2 -n 8 -c 1 ./ex.o 1 >> log.txt
yhrun -p thcp1 -N 2 -n 16 -c 1 ./ex.o 1 >> log.txt
yhrun -p thcp1 -N 2 -n 32 -c 1 ./ex.o 1 >> log.txt
#
echo "N process N thread" >> log.txt
yhrun -p thcp1 -N 2 -n 2 -c 2 ./ex.o 2 >> log.txt
yhrun -p thcp1 -N 2 -n 4 -c 4 ./ex.o 4 >> log.txt
yhrun -p thcp1 -N 2 -n 8 -c 8 ./ex.o 8 >> log.txt

