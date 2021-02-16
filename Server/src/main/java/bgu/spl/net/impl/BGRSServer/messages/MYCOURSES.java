package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.User;
import bgu.spl.net.impl.BGRSServer.prot;

import java.util.LinkedList;
import java.util.List;

public class MYCOURSES extends Command {
	
	
	public MYCOURSES() {
		super(11);
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	@Override
	public Command act(prot protocol) {
		User user = protocol.getLoggedInUser();
		if ( user == null || user.isAdmin())
			return new ERROR(this);

		List<Integer> courses = user.getCourses();
		LinkedList<String> strings = new LinkedList<>();
		courses.forEach(x -> strings.add(x.toString()));

		String showCourses = "[" + String.join(",", strings) + "]";
		return new ACK(this, showCourses);
	}
}
