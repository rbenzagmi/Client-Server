package bgu.spl.net.impl.BGRSServer;

public abstract class Command {

	protected short opcode;
	protected Database db = Database.getInstance();

	public Command(int opcode) {this.opcode = (short) opcode;}
	
	public Command(short opcode) {this.opcode = opcode;}

	//each command will do this method according to the rules
	public abstract Command act(prot protocol);
	
	public short getOpcode() {
		return opcode;
	}
}


