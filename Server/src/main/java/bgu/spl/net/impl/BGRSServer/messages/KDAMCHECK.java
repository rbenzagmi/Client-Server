package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.Course;
import bgu.spl.net.impl.BGRSServer.User;
import bgu.spl.net.impl.BGRSServer.prot;

import java.util.Arrays;

public class KDAMCHECK extends Command {
	
	
	public KDAMCHECK() {
		super(6);
	}
	
	private int courseNum;

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+": "+courseNum;
	}
	@Override
	public Command act(prot protocol) {
		User user = protocol.getLoggedInUser();
		Course course = db.getCourse(this.courseNum);
		ERROR error = new ERROR(this);

		//we did synchronized to prevent access from many students
		if(course!=null)
		{
			synchronized (course)
			{
				if (user == null || user.isAdmin())
					return error;

				int[] kdams = course.getKdam();
				String kdamsCourse = Arrays.toString(kdams).replaceAll(" ","");
				return new ACK(this, kdamsCourse);
			}
		}
		else
			return error;
	}
}
