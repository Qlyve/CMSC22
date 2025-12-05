package uiandlogic;

import java.util.ArrayList;

public class User {
	// for sure madadagan to since dapat nasasave ung schedule and stuff kung pano babasahin din ng
	// function natin.
    private final String username;
    private final String emailAddress;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String userType; 
    private final String password;
    private ArrayList<Section> plannedCourses;

    // constructor
	public User(/* String username, */String emailAddress, String firstName,
                String middleName, String lastName, String userType, String password) {
        this.username = firstName; // set the firstName as the username since username was not asked during account creation
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.userType = userType;
        this.password = password;
        this.plannedCourses = new ArrayList<>();
    }
    
    // getters
    public String getUsername()     { return username; }
    public String getEmailAddress() { return emailAddress; }
    public String getFirstName()    { return firstName; }
    public String getMiddleName()   { return middleName; }
    public String getLastName()     { return lastName; }
    public String getUserType()     { return userType; }
    public String getPassword()     { return password; }
    public ArrayList<Section> getPlannedCourses() { return plannedCourses;}
    
    // methods
    
    // view user
    public void viewUser() {
    	System.out.printf("Username: %s\n Address: %s\n Firstname: %s\n Middlename: %s\n Lastname: %s\n"
    			+ "Type: %s\n Password: %s\n \n", username, emailAddress, firstName, middleName, lastName, userType, password);
    }
    
    // view planned courses
	public void viewPlannedCourses() {
		for (Section sec : plannedCourses) {
			sec.viewState();
		}
	}
    
    // add section
    public boolean planSection(Section section) {
    	// section is already in plan
        if (plannedCourses.contains(section)) {
            System.out.println("Section already in plan!");
            return false;
        }
        
        // checks if course code already in the plan 
        boolean courseExists = plannedCourses.stream()
                .anyMatch(s -> s.getCourseCode().equals(section.getCourseCode()));
        
        if(courseExists) {
             System.out.println("Course " + section.getCourseCode() + " is already planned. Remove it first.");
             return false;
        }

        plannedCourses.add(section);
        System.out.println("planning " + section.getCourseCode());
        return true;
    }
    
    public boolean isCoursePlanned(String courseCode) {
        for(Section s : plannedCourses) {
            if(s.getCourseCode().equals(courseCode)) return true;
        }
        return false;
    }
    
    
    // remove section
    public void removeSection(Section section) {
        if (!plannedCourses.contains(section)) {
            System.out.println("Section not in plan!");
        } else {
            plannedCourses.remove(section); 
            System.out.println(section.getCourseCode() + " " + section.getSectionCode() + " successfully removed.");
        }
    }
    
    
}
    
    
    
    
    
  
