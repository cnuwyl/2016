CC = g++
HADOOP_INSTALL = /usr/local/development/hadoop-2.6.0
INC = -I$(HADOOP_INSTALL)/include
LIBS = -L$(HADOOP_INSTALL)/lib/native -lhadooppipes -lhadooputils -lpthread -lssl -lcrypto
CPPFLAGS = $(INC) -Wall -g -O2

WordCount: src/WordCount.cpp
	$(CC) $(CPPFLAGS) -o $@ $< $(LIBS)
