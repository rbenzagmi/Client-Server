CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system -pthread -lboost_filesystem -lboost_thread

all:bin/BGRSclient

bin/BGRSclient: bin/ClientNew.o bin/ConnectionHandler.o bin/Read.o bin/Write.o
	g++ -o bin/BGRSclient bin/ClientNew.o bin/ConnectionHandler.o bin/Read.o bin/Write.o $(LDFLAGS)

bin/ClientNew.o : src/ClientNew.cpp
	g++ $(CFLAGS)  -o bin/ClientNew.o src/ClientNew.cpp

bin/ConnectionHandler.o : src/ConnectionHandler.cpp
	g++ $(CFLAGS)  -o bin/ConnectionHandler.o src/ConnectionHandler.cpp

bin/Read.o : src/Read.cpp
	g++ $(CFLAGS)  -o bin/Read.o src/Read.cpp

bin/Write.o :  src/Write.cpp
	g++ $(CFLAGS)  -o bin/Write.o src/Write.cpp


.PHONY: clean
clean:
	rm -f bin/*
