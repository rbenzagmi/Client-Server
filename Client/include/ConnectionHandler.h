#ifndef CONNECTION_HANDLER__
#define CONNECTION_HANDLER__

#include <cstdlib>
#include <cstdio>
#include <string>
#include <iostream>

class ConnectionHandler {
private:
    const std::string host_;
	const short port_;


public:
    ConnectionHandler(std::string host, short port);

    virtual ~ConnectionHandler();

    // Connect to the remote machine
    bool connect();

    // Read a fixed number of bytes from the server - blocking.
    // Returns false in case the connection is closed before bytesToRead bytes can be read.
    static bool getBytes(char bytes[], unsigned int bytesToRead);

	// Send a fixed number of bytes from the client - blocking.
    // Returns false in case the connection is closed before all the data is sent.
    static bool sendBytes(const char bytes[], int bytesToWrite);

    // Read an ascii line from the server
    // Returns false in case connection closed before a newline can be read.
    static bool getLine(std::string& line);

    // Read an ascii line from the server
    // Returns false in case connection closed before a newline can be read.
    static bool getMessage(std::string& line);

	// Send an ascii line from the server
    // Returns false in case connection closed before all the data is sent.
    static bool sendLine(std::string& line);

        // Get Ascii data from the server until the delimiter character
    // Returns false in case connection closed before null can be read.
    static bool getFrameAscii(std::string& frame, char delimiter);

    // Send a message to the remote host.
    // Returns false in case connection is closed before all the data is sent.
    static bool sendFrameAscii(const std::string& frame, char delimiter);

    // Close down the connection properly.
    static void close();

}; //class ConnectionHandler
//
#endif