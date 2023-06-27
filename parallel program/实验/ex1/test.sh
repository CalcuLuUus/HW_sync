#!/bin/bash
yhrun -p thcp1 -n 1 ./ex1s1.o &> runs1.log
yhrun -p thcp1 -n 1 -c 2 ./ex1p1.o 2 &> runp1.log
yhrun -p thcp1 -n 1 -c 4 ./ex1p1.o 4 &>> runp1.log
yhrun -p thcp1 -n 1 -c 8 ./ex1p1.o 8 &>> runp1.log
yhrun -p thcp1 -n 1 ./ex1s2.o &> runs2.log
yhrun -p thcp1 -n 1 -c 2 ./ex1p2.o 2 &> runp2.log
yhrun -p thcp1 -n 1 -c 4 ./ex1p2.o 4 &>> runp2.log
yhrun -p thcp1 -n 1 -c 8 ./ex1p2.o 8 &>> runp2.log