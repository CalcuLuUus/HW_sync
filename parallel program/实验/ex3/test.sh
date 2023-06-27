#!/bin/sh
module load openmpi/mpi-x-gcc9.3.0
yhrun -p thcp1 -N 1 -n 1 ./ex.o > log.txt
yhrun -p thcp1 -N 2 -n 2 ./ex.o >> log.txt
yhrun -p thcp1 -N 2 -n 4 ./ex.o >> log.txt
yhrun -p thcp1 -N 2 -n 8 ./ex.o >> log.txt
yhrun -p thcp1 -N 2 -n 16 ./ex.o >> log.txt
yhrun -p thcp1 -N 2 -n 32 ./ex.o >> log.txt
yhrun -p thcp1 -N 2 -n 64 ./ex.o >> log.txt
