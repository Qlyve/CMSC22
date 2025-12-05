package uiandlogic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataAccess {
	
	// class is mainly for creation reading updating and deleting basically modifying ng stuff.
	// users pa lang to for reading and writing need dito ng pagread din sa csv and such 
	private static DataAccess instance;
	// paths
	private static final Path USER_PATH = Paths.get("src/data/users.txt");
	private static final Path SECTIONS_PATH = Paths.get("src/data/course_offerings.csv");
	private static final Path CMSC_COURSES_PATH = Paths.get("src/data/ics_cmsc_courses.csv");
	private static final Path MSCS_COURSES_PATH = Paths.get("src/data/ics_mscs_courses.csv");
	private static final Path MIT_COURSES_PATH = Paths.get("src/data/ics_mit_courses.csv");
	private static final Path PHD_COURSES_PATH = Paths.get("src/data/ics_phd_courses.csv");
	
	// lists
	private ObservableList<Course> CMSCCourses = FXCollections.observableArrayList();
	private ObservableList<Course> MSCSCourses = FXCollections.observableArrayList();
	private ObservableList<Course> MITCourses  = FXCollections.observableArrayList();
	private ObservableList<Course> PHDCourses  = FXCollections.observableArrayList();
	
	private ArrayList<Path> coursePaths = new ArrayList<Path>(Arrays.asList(
			CMSC_COURSES_PATH,
			MSCS_COURSES_PATH, 
			MIT_COURSES_PATH, 
			PHD_COURSES_PATH
			));
	

	private ArrayList<ObservableList<Course>> allCourseLists = new ArrayList<>(Arrays.asList(
		    CMSCCourses,
		    MSCSCourses,
		    MITCourses,
		    PHDCourses
		));
	
	private ObservableList<User> userList;
	
	public DataAccess() {
		this.userList =  FXCollections.observableArrayList();
		loadUsers();
		loadCourses();
		loadSections();
	 }
	 
	// auto updates the list na makikita sa main
	public ObservableList<User> getUsers(){
		return userList;
	}
	
	// helper method to parse CSV line since description has commmas
	private String[] parseCSVLine(String line) {
		List<String> result = new ArrayList<>();
		StringBuilder current = new StringBuilder();
		boolean inQuotes = false;
		
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			
			if (c == '"') {
				inQuotes = !inQuotes;
			} else if (c == ',' && !inQuotes) {
				result.add(current.toString().trim());
				current = new StringBuilder();
			} else {
				current.append(c);
			}
		}
		result.add(current.toString().trim());
		return result.toArray(new String[0]);
	}
	
	// load courses
	private void loadCourses() {
		int i = 0;
		String degree = null;
		BufferedReader reader = null;
		try {
	      	for(Path path : coursePaths) {
	      		reader = Files.newBufferedReader(path);	
	      		String line = reader.readLine();
	      		line = 	reader.readLine(); // skip header 
	      		
	      		// loops through the end of file
	      		while(line != null) {
//	      			String[] attributes = line.split(",");
	      			String[] attributes = parseCSVLine(line);
	      			
	      			switch(i) {
  					case 0:
  						degree = "BSCS";
  						break;
  					case 1:
  						degree = "MSCS";
  						break;
  					case 2:
  						degree = "MIT";
  						break;
  					case 3:
  						degree = "PHD";
  						break;
  					}
	      			
	      			
	      			// create the course
	      			Course newCourse = new Course(
	      					attributes[0],
	      					attributes[1], 
	      					attributes[2],
	      					attributes[3],
	      					degree
	      					);

	      			
	      			allCourseLists.get(i).add(newCourse);
	      			line = reader.readLine();
	      		} 
	      		reader.close();
	      		i += 1;
	      		}
	      	}catch (IOException e) {
	      		// TODO Auto-generated catch block
	      		e.printStackTrace();
	      		return ;
	      	}
	}

	// load sections
	private void loadSections() {
		BufferedReader reader = null;
		try {
	      	reader = Files.newBufferedReader(SECTIONS_PATH);	
	      	String line = reader.readLine();
	      	line = 	reader.readLine(); // skip school year
	      	line = 	reader.readLine(); // skip header
	      		
	      	// loops through the end of file
	      	while(line != null) {
	      		String[] attributes = line.split(",");
	      			
	      		// create the section
	      		// skip course title and units since its on the class course 
	      		Section newSection = new Section(
	      				attributes[0],
	      				attributes[1],
	      				attributes[3], 
	      				attributes[4],
	      				attributes[5],
	      				attributes[6]
	      				);
	      			
	      		// add the section to course if same course code
	      		for(ObservableList<Course> specificTypeCourseList: allCourseLists) {
	      			for(Course course : specificTypeCourseList) {
	      				if(course.getCourseCode().equals(newSection.getCourseCode())) {
	      					course.addSection(newSection);
	      				}
	      			}
	      		}
	      		line = reader.readLine();
	      	} 
	      	reader.close();
	      }catch (IOException e) {
	      	// TODO Auto-generated catch block
	      	e.printStackTrace();
	      	return ;
	     	}
		}
	
	// load users
	private void loadUsers() {
      	BufferedReader reader = null;
      	try {
      		reader = Files.newBufferedReader(USER_PATH);	
      		String line = reader.readLine();
      		
      		// loops through the end of file
      		while(line != null) {
      				
      			String[] attributes = line.split(",", -1); // to keep anything empty (For middle name which is optional)
      			// create the user 
      			User newUser = new User(
      					attributes[0], 
      					attributes[1],
      					attributes[2], 
      					attributes[3], 
      					attributes[4], 
      					attributes[5]);	// removed one attribute because username is already based on the first name
      				
      			userList.add(newUser);
      			line = reader.readLine();	
      	} 
      		reader.close();
      	}catch (IOException e) {
      		// TODO Auto-generated catch block
      		e.printStackTrace();
      		return ;
      	}
	}
	
	// saves users by reading
	private void saveUsers() {
		BufferedWriter writer = null;
	 	
	 	try {
	 		writer = Files.newBufferedWriter(USER_PATH);
	 		
	 		for(User user : userList) {
	 			String middle = user.getMiddleName() == null ? "" : user.getMiddleName(); // create the instance of a middle name even if not present to be empty
	 			String line = String.join(",",
	 					user.getEmailAddress(),
	 					user.getFirstName(),
	 					middle,
	 					user.getLastName(),
	 					user.getUserType(),
	 					user.getPassword()
	 					);
	 				
	 			writer.write(line);
	 			writer.newLine();
	 		}
	 			
	 		writer.close();
	 			
	 	} catch (IOException e) {
	 		e.printStackTrace();
	 	}
	}
	
	public void addUser(User newUser) {
		this.userList.add(newUser);
		saveUsers();
	}
	
	public ArrayList<ObservableList<Course>> getMasterList(){
		return allCourseLists;
	}
	
	public void viewUsers() {
		for(User user : userList) {
			user.viewUser();
		}
	}
	
	public void viewCourses() {
		for(ObservableList<Course> specificTypeCourseList : allCourseLists ) {
			for(Course course : specificTypeCourseList) {
				course.viewCourse();
			}
		}
	}
	
    public static DataAccess getInstance() {
        if (instance == null) {
            instance = new DataAccess();
        }
        return instance;
    }
    
    public Section findSection(String courseCode, String sectionCode) {
        for (ObservableList<Course> list : allCourseLists) {
            for (Course course : list) {
                if (course.getCourseCode().equals(courseCode)) {
                    for (Section sec : course.getSection()) {
                        if (sec.getSectionCode().equals(sectionCode)) {
                            return sec;
                        }
                    }
                }
            }
        }
        System.out.println(courseCode + sectionCode + "Was not found!");
        return null;
    }
}