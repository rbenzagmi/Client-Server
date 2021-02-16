package bgu.spl.net.impl.BGRSServer;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {
	
	private final ConcurrentHashMap<Integer, Course> courses = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

	//to prevent user from creating new Database
	private Database() {
		initialize("./Courses.txt");
	}

	private static class SingletonHolder {
		private static final Database instance = new Database();
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() {
		return SingletonHolder.instance;
	}

	//the user try to register to the system and return true if he success, and false if not
	public boolean try_Register(String userName, String password, boolean is_admin) {
		assert userName != null;
		assert password != null;
		
		User user = new User(userName, password, is_admin);

		return users.putIfAbsent(userName,user)==null;
	}
	
	public Course getCourse(int courseNum) {
		return courses.get(courseNum);
	}

	//return the names of the users that registered to this course
	public String[] getRegisteredToCourse(int courseNum) {
		ArrayList<String> lst = new ArrayList<>();
		users.values().forEach(user -> {
			if (user.isRegistered(courseNum))
				lst.add(user.getUserName());
		});
		return lst.toArray(new String[0]);
	}
	
	public User getUser(String userName) {
		return users.get(userName);
	}
	
	/**
	 * loades the courses from the file path specified
	 * into the Database, returns true if successful.
	 */
	boolean initialize(String coursesFilePath) {
		try {
			List<String> fileStream = Files.readAllLines(Paths.get(coursesFilePath));
			int numberOfLine = 0;
			//all the lines in the course file
			for (String line : fileStream) {
				numberOfLine++;
				String[] parts = line.split("[|]");
				int numCourse = Integer.parseInt(parts[0]);
				String nameCourse = parts[1];
				String kdam = parts[2];
				String[] kdams = (kdam.substring(1, kdam.length() - 1)).split(",");
				int len = "".equals(kdams[0]) ? 0 : kdams.length;
				int[] kdamsCourses = new int[len];
				for (int j = 0; j < len; j++)
					kdamsCourses[j] = Integer.parseInt(kdams[j]);
				int capacity = Integer.parseInt(parts[3]);
				Course course = new Course(numCourse, nameCourse, kdamsCourses, capacity,numberOfLine);
				//each line is a course and now we add the course to our hashmap
				courses.putIfAbsent(numCourse, course);
			}
			return true;
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
