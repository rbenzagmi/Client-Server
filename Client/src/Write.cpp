#include "../include/Write.h"
#include "boost/lexical_cast.hpp"
#include <iostream>
#include <vector>
#include <string>

using namespace std;


void Write::run(){
    while (1) {
        const short bufsize = 1024;
        char buf[bufsize];
        cin.getline(buf, bufsize);
        string line(buf);

        string rest;
        string miss;
        int mission = line.find(' ');
        //if there is no ' ' so the client wrote just command
        if (mission == -1)
            miss = line;
        else {
            //miss will be the command
            miss = line.substr(0, mission);
            //rest will be the rest string
            rest = line.substr(mission + 1);
        }
        size_t splitter = rest.find(' ');
        if (splitter < 0)
            splitter = rest.length();

        string firstString = rest.substr(0, splitter);
        string secondString = splitter == rest.length() ? "" : rest.substr(splitter + 1);
        vector<string> cmds = {"ADMINREG", "STUDENTREG", "LOGIN", "LOGOUT", "COURSEREG", "KDAMCHECK", "COURSESTAT",
                               "STUDENTSTAT", "ISREGISTERED", "UNREGISTER", "MYCOURSES"};
        long op=0;
        //finding the opcode
        for(size_t i=0; i<cmds.size(); i++)
        {
            if (miss==(cmds[i]))
            {
                op=i+1;
                break;
            }
        }
        if ((op > 11)|(op==0))
            cout << "Command " << miss << " doesn't exists." << endl << "Please try again." << endl;
        else {
            sendMsgToSrv((short)op, firstString, secondString);
        }
    }
}

//sending the message of the client to the server
void Write::sendMsgToSrv(short opcode,string& firstString,string& secondString) {
    char opcodeChars[2];
    shortToBytes(opcode, opcodeChars);
    int zero = 0;
    char* zerop = (char*) &zero;
    //in each case we send the opcode to the server
    ConnectionHandler::sendBytes(opcodeChars, 2);
    short courseNum;
    char courseNumChars[2];

    if(opcode == 8)
    {
        //according to the instructions in opcode 8 we send also 1 string and 0 in the end
        ConnectionHandler::sendBytes(firstString.c_str(), firstString.length());
        ConnectionHandler::sendBytes(zerop, 1);
    }
    else if((opcode==5)|(opcode==6)|(opcode==7)|(opcode==9)|(opcode==10))
    {
        //according to the instructions in this opcodes we send also course num and 0 in the end
        courseNum=(boost::lexical_cast<short>(firstString));
        shortToBytes(courseNum,courseNumChars);
        ConnectionHandler::sendBytes(courseNumChars, 2);
    }
    else if ((firstString.length() != 0) & (secondString.length() != 0)) {
        //according to the instructions in the rest cases we send also 1 string,0, another string and 0 in the end
        ConnectionHandler::sendBytes(firstString.c_str(), firstString.length());
        ConnectionHandler::sendBytes(zerop, 1);
        ConnectionHandler::sendBytes(secondString.c_str(), secondString.length());
        ConnectionHandler::sendBytes(zerop, 1);
    }
}

void Write::shortToBytes(short num, char* bytesArr) {
    bytesArr[0] = (char) (((unsigned char) ((unsigned char) num >> (unsigned char) 8)) & (unsigned char) 0xFF);
    bytesArr[1] = (char) ((unsigned short) num & (unsigned char) 0xFF);
}

Write::Write(ConnectionHandler &connectionHandler) : handler(connectionHandler){}
