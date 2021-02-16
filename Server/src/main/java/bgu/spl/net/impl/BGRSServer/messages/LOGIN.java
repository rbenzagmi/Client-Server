package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.User;
import bgu.spl.net.impl.BGRSServer.prot;

public class LOGIN extends Command {
	
	
	public LOGIN() {
		super(3);
	}
	
	private String userName;
	private String password;

	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+": "+this.userName+", "+password;
	}
	@Override
	public Command act(prot protocol) {
		ERROR error = new ERROR(this);
		User user = db.getUser(userName);

		if (protocol.getLoggedInUser() != null || user == null || !user.getPassword().equals(password) )
			return error;

		if ( user.try_login() ) {
			protocol.setLoggedInUser(user);
			return new ACK(this);
		}
		return new ERROR(this);
	}
}
