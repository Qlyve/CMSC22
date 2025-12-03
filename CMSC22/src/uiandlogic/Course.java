package uiandlogic;

import java.util.ArrayList;

public class Course {
	private String courseCode;
	private String courseName;
	private String units;
	private String description;
	private String typeDegree;
	private ArrayList<Section> sections;
	
	public Course(String CC, String CN, String units, String description, String degree) {
		this.courseCode = CC;
		this.courseName = CN;
		this.units = units;
		this.description = description;
		this.typeDegree = degree;
		this.sections = new ArrayList<Section>() ; 
	}
	
	// getters
	public String getCourseCode()			{return courseCode;}
	public String getCourseName()			{return courseName;}
	public String getUnits()					{return units;}
	public String getDescription()			{return description;}
	public String getTypeDegree()			{return typeDegree;}
	public ArrayList<Section> getSection()	{return sections;}
	
	protected void addSection(Section newSection) {
		sections.add(newSection);
	}
	
		
		public void viewCourse() {
			int i = 1;
		    System.out.println("\n================================================================================");
		    System.out.printf("  Course Code : %s\n", courseCode);
		    System.out.printf("  Course Name : %s\n", courseName);
		    System.out.printf("  Units       : %s\n", units);
		    System.out.println("--------------------------------------------------------------------------------");
		    System.out.printf("  Description : %s\n", description);
		    System.out.println("--------------------------------------------------------------------------------");
		    System.out.println("  Available Sections:");
		    System.out.println();
		    System.out.printf("  %-3s %-12s %-18s %-15s %-20s\n", 
		        "#", "Section", "Time", "Days", "Room");
		    System.out.println("--------------------------------------------------------------------------------");
		    
			for(Section section : sections) {
				System.out.printf("  %-3d", i);
				section.viewState();
				i ++;
			}
			
		    System.out.println("================================================================================\n");
		}

}
