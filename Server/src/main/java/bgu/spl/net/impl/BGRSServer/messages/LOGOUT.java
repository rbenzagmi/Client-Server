package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.User;
import bgu.spl.net.impl.BGRSServer.prot;

public class LOGOUT extends Command {
	
	public LOGOUT() {
		super(4);
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
	@Override
	public Command act(prot protocol) {
		User user = protocol.getLoggedInUser();
		if ( user == null || !user.try_logout() ) {
			return new ERROR(this);
		}
		//we want to close this client and his protocol when we get from him a logout
		protocol.terminate();
		protocol.setLoggedInUser(null);
		return new ACK(this);
	}
}
