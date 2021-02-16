package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGRSServer.messages.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;


public class encoderDecoder implements MessageEncoderDecoder<Command> {
	Command msg;
	Field[] fields;
	Short op;
	boolean isOpFound = false;
	int remaining_0 = 0;
	private final LinkedList<Byte> bytes = new LinkedList<>();
	
	@Override
	public Command decodeNextByte(byte nextByte) {
		Command output = null;
		try {
			if ( !isOpFound ) {
				bytes.add(nextByte);
				//if we found the opcode
				if ( bytes.size() == 2 ) {
					op = bytesToShort(bytes);
					bytes.clear();
					msg = get_func_by_opcode(op);
					if(msg == null)
						throw new IllegalArgumentException("opcode " + op + " is unknown");
					isOpFound = true;
					if ( op == 4 | op == 11 ) {
						output = msg;
					}
				}
			} else if ( op == 1 | op == 2 | op == 3 ) {
				if ( nextByte == '\0' ) {
					//in this commands we have 2 strings
					remaining_0 -= 1;
					String str = bytes_ll_2_string(bytes);
					bytes.clear();
					fields[1 - remaining_0].set(msg, str);
					if ( remaining_0 == 0 )
						output = msg;
				} else
					bytes.add(nextByte);
			} else if ( op == 5 | op == 6 | op == 7 | op == 9 | op == 10 ) {
				bytes.add(nextByte);
				if ( bytes.size() == 2 ) {
					int courseNum_value = (bytesToShort(bytes) & 0xFF);
					try {
						fields[0].set(msg,courseNum_value);}
					catch (Exception ignored) {}
					output = msg;
				}
			} else if ( op == 8 ) {
				if ( nextByte == '\0' ) {
					String str = bytes_ll_2_string(bytes);
					STUDENTSTAT msg = new STUDENTSTAT();
					msg.setUserName(str);
					output = msg;
				}
				bytes.add(nextByte);
			}
			if ( output != null ) {
				bytes.clear();
				isOpFound = false;
			}
			return output;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	//making linkedlist of bytes to string
	private String bytes_ll_2_string(LinkedList<Byte> bytes) {
		byte[] arr = new byte[bytes.size()];
		int i = 0;
		for (byte b : bytes)
			arr[i++] = b;
		return new String(arr, StandardCharsets.UTF_8);
	}

	//making the fields accessible
	private void updateFields(Class<? extends Command> clz)
	{
		fields = clz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getType() == String.class)
				remaining_0 += 1;
		}
	}

	//return an object of the opcode
	private Command get_func_by_opcode(short opcode) {
		switch (opcode) {
			case 1:
			{
				updateFields(ADMINREG.class);
				return new ADMINREG();
			}
			case 2: {
				updateFields(STUDENTREG.class);
				return new STUDENTREG();
			}
			case 3:
			{
				updateFields(LOGIN.class);
				return new LOGIN();
			}
			case 4:
			{
				updateFields(LOGOUT.class);
				return new LOGOUT();
			}
			case 5:
			{
				updateFields(COURSEREG.class);
				return new COURSEREG();
			}
			case 6:
			{
				updateFields(KDAMCHECK.class);
				return new KDAMCHECK();
			}
			case 7:
			{
				updateFields(COURSESTAT.class);
				return new COURSESTAT();
			}
			case 8:
			{
				updateFields(STUDENTSTAT.class);
				return new STUDENTSTAT();
			}
			case 9:
			{
				updateFields(ISREGISTERED.class);
				return new ISREGISTERED();
			}
			case 10:
			{
				updateFields(UNREGISTER.class);
				return new UNREGISTER();
			}
			case 11:
			{
				updateFields(MYCOURSES.class);
				return new MYCOURSES();
			}
		}
		return null;
	}
	
	private short bytesToShort(LinkedList<Byte> byteArr) {
		short result = (short) ((byteArr.get(0) & 0xff) << 8);
		result += (short) (byteArr.get(1) & 0xff);
		return result;
	}

	public byte[] shortToBytes(short num)
	{
		byte[] bytesArr = new byte[2];
		bytesArr[0] = (byte)((num >> 8) & 0xFF);
		bytesArr[1] = (byte)(num & 0xFF);
		return bytesArr;
	}
	
	public byte[] encode(Response msg) {
		byte[] opcode = shortToBytes(msg.getOpcode());
		byte[] msgOpcode = shortToBytes(msg.getMsg_opcode());
		byte[] optional;
		if(msg.getOpcode()==12 && (msg.getMsg_opcode()==5|msg.getMsg_opcode()==6|msg.getMsg_opcode()==7|msg.getMsg_opcode()==8|msg.getMsg_opcode()==9|msg.getMsg_opcode()==11))
			optional = (msg.getOptional()+"\0").getBytes();
		else
			optional = (msg.getOptional()).getBytes();
		byte[] returnBytes = new byte[opcode.length+msgOpcode.length+optional.length];
		int j=0;
		for (int i=0; i<opcode.length; i++) {
			returnBytes[j] = opcode[i];
			j++;
		}
		for (int i=0; i<msgOpcode.length; i++) {
			returnBytes[j] = msgOpcode[i];
			j++;
		}
		for (int i=0; i<optional.length; i++) {
			returnBytes[j] = optional[i];
			j++;
		}
		return returnBytes;
	}
	
	@Override
	public byte[] encode(Command message) {
		if ( !(message instanceof Response) )
			throw new RuntimeException(
					"message of type " + message.getClass().getSimpleName() + " is not of type Response");
		return this.encode((Response) message);
	}
}

