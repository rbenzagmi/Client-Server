#ifndef CLIENT_WRITE_H
#define CLIENT_WRITE_H

#include <string>
#include "ConnectionHandler.h"

using namespace std;


class Write {

private:
    ConnectionHandler& handler;
public:

    Write(ConnectionHandler& handler);

    void shortToBytes(short i, char zero[2]);

    void sendMsgToSrv(short op,string& basicString,string& basicString1);

    void run();
};


#endif