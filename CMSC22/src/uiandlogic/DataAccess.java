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
	
	private static DataAccess instance;
	
	// paths
	private static final Path USER_PATH = Paths.get("src/data/users.txt");
	private static final Path SECTIONS_PATH = Paths.get("src/data/course_offerings.csv");
	private static final Path CMSC_COURSES_PATH = Paths.get("src/data/ics_cmsc_courses.csv");
	private static final Path MSCS_COURSES_PATH = Paths.get("src/data/ics_mscs_courses.csv");
	private static final Path MIT_COURSES_PATH = Paths.get("src/data/ics_mit_courses.csv");
	private static final Path PHD_COURSES_PATH = Paths.get("src/data/ics_phd_courses.csv");
	
	// path for storing planned courses
	private static final Path PLANNED_COURSES_PATH = Paths.get("src/data/planned_courses.txt");
	
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
		this.userList = FXCollections.observableArrayList();
		loadUsers();
		loadCourses();
		loadSections();
		loadPlannedCourses(); // load saved planned courses
	}
	
	public ObservableList<User> getUsers(){
		return userList;
	}
	
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
	
	private void loadCourses() {
		int i = 0;
		String degree = null;
		BufferedReader reader = null;
		try {
	      	for(Path path : coursePaths) {
	      		reader = Files.newBufferedReader(path);	
	      		String line = reader.readLine();
	      		line = reader.readLine(); // skip header 
	      		
	      		while(line != null) {
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
	    } catch (IOException e) {
	      	e.printStackTrace();
	      	return;
	    }
	}

	private void loadSections() {
		BufferedReader reader = null;
		try {
	      	reader = Files.newBufferedReader(SECTIONS_PATH);	
	      	String line = reader.readLine();
	      	line = reader.readLine(); // skip school year
	      	line = reader.readLine(); // skip header
	      		
	      	while(line != null) {
	      		String[] attributes = line.split(",");
	      			
	      		Section newSection = new Section(
	      				attributes[0],
	      				attributes[1],
	      				attributes[3], 
	      				attributes[4],
	      				attributes[5],
	      				attributes[6]
	      		);
	      			
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
	    } catch (IOException e) {
	      	e.printStackTrace();
	      	return;
	    }
	}
	
	private void loadUsers() {
      	BufferedReader reader = null;
      	try {
      		reader = Files.newBufferedReader(USER_PATH);	
      		String line = reader.readLine();
      		
      		while(line != null) {
      			String[] attributes = line.split(",", -1);
      			User newUser = new User(
      					attributes[0], 
      					attributes[1],
      					attributes[2], 
      					attributes[3], 
      					attributes[4], 
      					attributes[5]);	
      				
      			userList.add(newUser);
      			line = reader.readLine();	
      		} 
      		reader.close();
      	} catch (IOException e) {
      		e.printStackTrace();
      		return;
      	}
	}
	
	// load planned courses from file
	private void loadPlannedCourses() {
		BufferedReader reader = null;
		try {
			// create file if it doesn't exist
			if (!Files.exists(PLANNED_COURSES_PATH)) {
				Files.createFile(PLANNED_COURSES_PATH);
				return;
			}
			
			reader = Files.newBufferedReader(PLANNED_COURSES_PATH);
			String line = reader.readLine();
			
			while (line != null) {
				// username,courseCode,sectionCode
				String[] parts = line.split(",");
				if (parts.length == 3) {
					String username = parts[0];
					String courseCode = parts[1];
					String sectionCode = parts[2];
					
					// find the user
					User user = findUserByUsername(username);
					if (user != null) {
						// find the section
						Section section = findSection(courseCode, sectionCode);
						if (section != null) {
							user.planSection(section);
						}
					}
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// save planned courses to file
	public void savePlannedCourses() {
		BufferedWriter writer = null;
		try {
			writer = Files.newBufferedWriter(PLANNED_COURSES_PATH);
			
			for (User user : userList) {
				for (Section section : user.getPlannedCourses()) {
					String line = String.join(",",
							user.getUsername(),
							section.getCourseCode(),
							section.getSectionCode()
					);
					writer.write(line);
					writer.newLine();
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// helper method to find user by username
	private User findUserByUsername(String username) {
		for (User user : userList) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}
	
	private void saveUsers() {
		BufferedWriter writer = null;
	 	try {
	 		writer = Files.newBufferedWriter(USER_PATH);
	 		
	 		for(User user : userList) {
	 			String middle = user.getMiddleName() == null ? "" : user.getMiddleName();
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
        return null;
    }
}
