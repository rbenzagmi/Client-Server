package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.User;
import bgu.spl.net.impl.BGRSServer.prot;

import java.util.LinkedList;
import java.util.List;

public class STUDENTSTAT extends Command {
	
	private String userName;
	
	public STUDENTSTAT() {
		super(8);
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+": "+this.userName;
	}
	@Override
	public Command act(prot protocol) {
		User user = protocol.getLoggedInUser();
		User user_to_check = db.getUser(userName);

		if ( user == null || !user.isAdmin() || user_to_check == null )
			return new ERROR(this);

		String showRegCourses = "Student: " + userName + "\nCourses: [";

		List<Integer> courses = user_to_check.getCourses();
		LinkedList<String> strings = new LinkedList<>();
		courses.forEach(x -> strings.add(x.toString()));

		showRegCourses += String.join(",", strings) + "]";
		return new ACK(this, showRegCourses);
	}
}
