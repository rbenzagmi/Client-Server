package bgu.spl.net.impl.BGRSServer;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Course {
	private final int courseNum;
	private final String courseName;
	private final int[] kdam;
	private final int capacity;
	private final int numLine;
	
	public Course(int courseNum, String courseName, int[] kdams, int capacity,int numLine) {
		this.courseNum = courseNum;
		this.courseName = courseName;
		this.capacity = capacity;
		this.kdam = kdams;
		this.numLine = numLine;
	}
	
	public int getCourseNum() {return courseNum;}
	
	public String getCourseName() {return courseName;}

	//return the kdams courses of this course in the order of the courses file
	public int[] getKdam() {
		//hashmap for each kdam course and his num of line in the courses file
		ConcurrentHashMap<Integer, Integer> courses = new ConcurrentHashMap<>();
		for (int i=0; i<kdam.length; i++)
			courses.put(Database.getInstance().getCourse(kdam
			[i]).getNumLine(),kdam[i]);
		List<Integer> lines = new LinkedList<>();
		for(Integer line: courses.keySet())
			lines.add(line);
		lines.sort(Integer::compareTo);
		int[] sorted = new int[courses.size()];
		int j=0;
		for(Integer line: lines){
			sorted[j]=courses.get(line);
			j++;
		}
		return sorted;
	}
	
	public int getCapacity() {return capacity;}

	public int getNumLine() {return numLine;}
	
	public int getFreePlace() {return capacity - Database.getInstance().getRegisteredToCourse(courseNum).length;}
}
