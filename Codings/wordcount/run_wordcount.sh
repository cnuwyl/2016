#! /bin/bash

hadoop pipes -input in/file1.txt -output out/cwordcount -program in/WordCount

