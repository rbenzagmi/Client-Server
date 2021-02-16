package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.User;
import bgu.spl.net.impl.BGRSServer.prot;
import bgu.spl.net.impl.BGRSServer.Course;

import java.util.Arrays;

public class COURSESTAT extends Command {
	
	private int courseNum;

	
	@Override
	public String toString() {
		return this.getClass().getSimpleName()+": "+courseNum;
	}
	
	public COURSESTAT() {
		super(7);
	}
	
	@Override
	public Command act(prot protocol) {
		User user = protocol.getLoggedInUser();
		Course ci = db.getCourse(courseNum);
		ERROR error = new ERROR(this);

		if(ci!=null)
		{
			synchronized (ci)
			{
				if ( user==null || !user.isAdmin())
					return error;

				String nameCourse = ci.getCourseName();
				int freePlaces = ci.getFreePlace();
				int capacity = ci.getCapacity();
				String[] sortRegUsers = db.getRegisteredToCourse(ci.getCourseNum());
				Arrays.sort(sortRegUsers);

				String showReg = "[" + String.join(",", sortRegUsers) + "]";
				String optional =
						"Course: (" + courseNum + ") " + nameCourse + "\n" +
								"Seats Available: " + freePlaces + "/" + capacity + "\n" +
								"Students Registered: " + showReg;
				return new ACK(this,optional);
			}
		}
		else
			return error;
	}
}
