package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.Response;

public class ACK extends Response {
	public ACK(Command cmd, String optional) {
		super(12, cmd, optional);
	}
	public ACK(Command cmd) {
		this(cmd, "");
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" "+this.msg_opcode+optional;
	}
}
