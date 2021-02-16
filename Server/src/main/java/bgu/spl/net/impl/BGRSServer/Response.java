package bgu.spl.net.impl.BGRSServer;


public class Response extends Command {
	protected short msg_opcode;
	protected String optional;
	
	public Response(int opcode, Command cmd, String optional) {
		super(opcode);
		this.msg_opcode = cmd.getOpcode();
		this.optional = optional;
	}
	
	public Response(int opcode, Command cmd) {
		this(opcode, cmd, "");
	}

	@Override
	public Command act(prot protocol) {
		throw new IllegalStateException();
	}
	
	public String getOptional() {
		String prefix = optional.equals("") ? "" : "\n";
		return prefix + optional;
	}
	
	public short getMsg_opcode() {
		return msg_opcode;
	}
}


