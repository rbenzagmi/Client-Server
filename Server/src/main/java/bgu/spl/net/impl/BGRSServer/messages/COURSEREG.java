package bgu.spl.net.impl.BGRSServer.messages;

import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.Course;
import bgu.spl.net.impl.BGRSServer.User;
import bgu.spl.net.impl.BGRSServer.prot;

public class COURSEREG extends Command {
	private int courseNum;

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+": "+courseNum;
	}
	public COURSEREG() {
		super(5);
	}
	
	@Override
	public Command act(prot protocol) {
		User user = protocol.getLoggedInUser();
		Course course = db.getCourse(this.courseNum);
		ERROR error = new ERROR(this);
		if(course!=null)
		{
			synchronized (course)
			{
				if (user == null
						|| user.isAdmin()
						|| course.getFreePlace() == 0
						|| user.isRegistered(courseNum))
					return error;

				int[] kdams = course.getKdam();
				for (int kdam : kdams) {
					if (!user.isRegistered(kdam))
						return error;
				}

				user.register(courseNum);
				return new ACK(this);
			}
		}
		else
			return error;
	}
}
