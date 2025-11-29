package uiandlogic;

public class Section {
	private String courseCode;
	private String courseName;
	private String sectionCode;
	private String time;
	private String day;
	private String room;
	
	public Section(String CC, String CN, String SC, String time, String day, String room) {
		this.courseCode = CC;
		this.courseName = CN;
		this.sectionCode = SC;
		this.time = time;
		this.day = day;
		this.room = room;
	}
	
	// getters;
	public String getCourseCode()	{return courseCode ;}
	public String getCourseName()	{return courseName ;}
	public String getSectionCode()	{return sectionCode ;}
	public String getTime()			{return time;}
	public String getDay() 			{return day;}
	public String getRoom()			{return room;}

	public void viewState() {
	    System.out.printf("  %-12s %-18s %-15s %-20s\n", 
	        sectionCode, 
	        time, 
	        day, 
	        room);
	}
	
}
