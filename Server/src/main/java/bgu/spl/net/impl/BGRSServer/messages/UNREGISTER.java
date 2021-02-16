package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.User;
import bgu.spl.net.impl.BGRSServer.prot;

public class UNREGISTER extends Command {
	
	private int courseNum;
	
	public UNREGISTER() {
		super(10);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ": " + this.courseNum;
	}
	
	@Override
	public Command act(prot protocol) {
		User user = protocol.getLoggedInUser();
		ERROR error = new ERROR(this);
		if ( user == null || user.isAdmin())
			return error;

		if (user.unregister(courseNum) ) {
			return new ACK(this);
		}
		return error;

	}
}
