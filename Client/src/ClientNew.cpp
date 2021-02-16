#include <cstdlib>
#include <iostream>
#include <thread>
#include "../include/ConnectionHandler.h"
#include "../include/Write.h"
#include "../include/Read.h"

using namespace std;

int main(int argc, char* argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }

    std::string host = argv[1];
    short port = atoi(argv[2]);

    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }

    //here is the sending to the server
    Write writeToTheServer(connectionHandler);
    //the thread of the sending
    thread th2(&Write::run,&writeToTheServer);
    //here is the reading from the server
    Read readFromServer(connectionHandler);
    //the main thread running the reading
    readFromServer.run();
    th2.join();

    return 0;
}




