package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.User;
import bgu.spl.net.impl.BGRSServer.prot;

public class ADMINREG extends Command {
	
	public ADMINREG() {
		super(1);
	}
	
	private String userName;
	private String password;

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+": "+this.userName+", "+password;
	}
	@Override
	public Command act(prot protocol) {
		User user = protocol.getLoggedInUser();
		ERROR error = new ERROR(this);

		if (user != null )
			return error;

		if ( db.try_Register(this.userName, this.password, true) )
			return new ACK(this);

		return new ERROR(this);
	}
}
