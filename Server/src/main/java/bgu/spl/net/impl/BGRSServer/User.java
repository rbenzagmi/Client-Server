package bgu.spl.net.impl.BGRSServer;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class User {
	private final boolean isAdmin;
	private final String userName;
	private final String password;
	private final AtomicBoolean isLoggedIn = new AtomicBoolean(false);
	private final ConcurrentHashMap<Integer, Boolean> courses = new ConcurrentHashMap<>();
	
	public User(String userName, String password, boolean isAdmin) {
		this.isAdmin = isAdmin;
		this.userName = userName;
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean try_login() {
		return isLoggedIn.compareAndSet(false, true);
	}
	
	public boolean try_logout() {
		return isLoggedIn.compareAndSet(true, false);
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	public boolean isRegistered(int courseNum) {
		return courses.containsKey(courseNum);
	}
	
	public boolean register(int courseNum) {
		return courses.putIfAbsent(courseNum, false) == null;
	}
	
	public boolean unregister(int courseNum) {
		return courses.remove(courseNum) != null;
	}


	//return the courses that this user is registered to
	public LinkedList<Integer> getCourses() {
		ConcurrentHashMap<Integer, Integer> coursesReg = new ConcurrentHashMap<>();
		for(Integer course: courses.keySet())
			coursesReg.put(Database.getInstance().getCourse(course).getNumLine(),course);
		List<Integer> lines = new LinkedList<>();
		for(Integer line: coursesReg.keySet())
			lines.add(line);
		lines.sort(Integer::compareTo);
		LinkedList<Integer> sorted = new LinkedList<>();
		int j=0;
		for(Integer line: lines){
			sorted.add(coursesReg.get(line));
			j++;
		}
		return sorted;
	}
}
