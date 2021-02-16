package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.User;
import bgu.spl.net.impl.BGRSServer.prot;

public class ISREGISTERED extends Command {
	private int courseNum;
	
	public ISREGISTERED() {
		super(9);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+": "+courseNum;
	}
	@Override
	public Command act(prot protocol) {
		User user = protocol.getLoggedInUser();

		if ( user == null || user.isAdmin() )
			return new ERROR(this);

		String answer = user.isRegistered(this.courseNum) ? "" : "NOT ";
		String suffix = "REGISTERED";
		return new ACK(this, answer + suffix);
	}
}
