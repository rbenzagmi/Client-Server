#ifndef CLIENT_READ_H
#define CLIENT_READ_H

#include <string>
#include <vector>
#include <thread>
#include "ConnectionHandler.h"

using namespace std;

class Read {

private:
    ConnectionHandler& handler;
public:

    short bytesToShort(char* bytesArr);
    Read(ConnectionHandler& handler);
    void run();
};


#endif
