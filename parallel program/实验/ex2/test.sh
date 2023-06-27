#!/bin/bash
yhrun -p thcp1 -n 1 ./s.o &> runs.log
yhrun -p thcp1 -n 1 -c 1 ./p.o 1 &> runp.log
yhrun -p thcp1 -n 1 -c 2 ./p.o 2 &>> runp.log
yhrun -p thcp1 -n 1 -c 4 ./p.o 4 &>> runp.log
yhrun -p thcp1 -n 1 -c 8 ./p.o 8 &>> runp.log
yhrun -p thcp1 -n 1 -c 16 ./p.o 16 &>> runp.log
yhrun -p thcp1 -n 1 -c 32 ./p.o 32 &>> runp.log
yhrun -p thcp1 -n 1 -c 64 ./p.o 64 &>> runp.log