#include "../include/Read.h"
#include <cstdlib>
#include <iostream>


using namespace std;

void Read::run() {
    while (1) {
        short opcode;
        string response;
        short msgOpcode;
        string optional;

        char opcodeChars[2];
        //reading the opcode from the server (ACK/ERROR)
        if (!ConnectionHandler::getBytes(opcodeChars,2)) {
            cout << "Disconnected. Exiting...\n" << endl;
            break;
        }
        else
        {
            opcode = bytesToShort(opcodeChars);
            //case of ACK
            if(opcode==12)
            {
                response = "ACK";
                char opcodeMsgChars[2];
                //reading the opcode of the function
                if (!ConnectionHandler::getBytes(opcodeMsgChars,2)) {
                    cout << "Disconnected. Exiting...\n" << endl;
                    break;
                }
                else
                {
                    msgOpcode = bytesToShort(opcodeMsgChars);
                    if((msgOpcode==5)|(msgOpcode==6)|(msgOpcode==7)|(msgOpcode==8)|(msgOpcode==9)|(msgOpcode==11))
                    {
                        //in this cases we have optional parts to read
                        if (!ConnectionHandler::getMessage(optional)) {
                            cout << "Disconnected. Exiting...\n" << std::endl;
                            break;
                        }
                    }

                }
            }
            //case of ERROR
            else if(opcode==13)
            {
                response = "ERROR";
                char opcodeMsgChars[2];
                //reading the opcode of the function
                if (!ConnectionHandler::getBytes(opcodeMsgChars,2)) {
                    cout << "Disconnected. Exiting...\n" << endl;
                    break;
                }
                else
                    msgOpcode = bytesToShort(opcodeMsgChars);
            }
            else
            {
                cout << "The opcode is incorrect\n" << endl;
                break;
            }
        }

        cout<<response+" "<<msgOpcode<<optional<<endl;

        //in case of logout we need to close the client
        if ((response=="ACK") & (msgOpcode==4)) {
            exit(0);
        }
    }
}

short Read::bytesToShort(char* bytesArr)
{
    short result = (short)((bytesArr[0] & 0xff) << 8);
    result += (short)(bytesArr[1] & 0xff);
    return result;
}

Read::Read(ConnectionHandler &connectionHandler) :handler(connectionHandler){}
