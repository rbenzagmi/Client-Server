package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.Response;

public class ERROR extends Response {
	public ERROR(Command cmd){
		super(13, cmd);
	}
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" "+this.msg_opcode;
	}
}
