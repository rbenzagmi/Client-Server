Implementing a “Course Registration System” server and client.

The communication between the server and the client(s) will be performed using a binary communication protocol.

The implementation of the server is based on the Thread-Per-Client (TPC) and Reactor servers.

The messages in this protocol are binary numbers, composed of an opcode (short number of two bytes) which indicates the command, and the data needed for this command (in various lengths). 

In contrast to the delimiter-based MessageEncoderDecoder, the messages are identified by a predefined message-length.

Unlike real course registration systems, the courses are specified in one file, according to a specific format.

The data which the server and clients get during their running are saved in the RAM (in data structures, e.g., arrays, lists, etc…)

- Establishing a client/server connection:
  
Upon connecting, a client must identify himself to the service.
A new client will issue a Register command with the requested user name and password.
A registered client can then login using the Login command. 
Once the command is sent, the server will acknowledge the validity of the username and password.
Once a user is logged in successfully, she can submit other commands.
The register command will not perform automatic login (you will need to call login after it).
