package uiandlogic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataAccess {
	
	// class is mainly for creation reading updating and deleting basically modifying ng stuff.
	// users pa lang to for reading and writing need dito ng pagread din sa csv and such 
	private static final Path USER_PATH = Paths.get("src/data/users.txt");
	private ObservableList<User> userList;
	
	public DataAccess() {
		this.userList =  FXCollections.observableArrayList();
		loadUsers();
	 }
	 
	// auto updates the list na makikita sa main
	public ObservableList<User> getUsers(){
		return userList;
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
	      					attributes[5], 
	      					attributes[6]);	
	      				
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
	 					user.getUsername(),
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
		
}
