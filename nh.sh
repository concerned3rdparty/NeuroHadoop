﻿#!/bin/bash

hadoop fs -rmr output
hadoop fs -mkdir input
hadoop fs -put ~/data/morlet-2000.csv lookup/morlet-2000.dat
hadoop fs -put ~/data/signals/*.csv input/
hadoop jar NeuroHadoop-0.1.0.jar convolution.rchannel.ConvolutionJob input output > output.txt


