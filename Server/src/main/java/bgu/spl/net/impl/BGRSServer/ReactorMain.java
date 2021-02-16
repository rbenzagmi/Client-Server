package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.srv.Server;

public class ReactorMain {

	public static void main(String[] args) {
		
        Server.reactor(
                Integer.valueOf(args[1]),
				Integer.valueOf(args[0]), //port
                prot::new, //protocol factory
                encoderDecoder::new //message encoder decoder factory
        ).serve();
	}
}
