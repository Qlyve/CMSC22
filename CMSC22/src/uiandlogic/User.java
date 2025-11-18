package uiandlogic;

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

    // constructor
    public User(String username, String emailAddress, String firstName,
                String middleName, String lastName, String userType, String password) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.userType = userType;
        this.password = password;
    }
    
    // getters
    public String getUsername()     { return username; }
    public String getEmailAddress() { return emailAddress; }
    public String getFirstName()    { return firstName; }
    public String getMiddleName()   { return middleName; }
    public String getLastName()     { return lastName; }
    public String getUserType()     { return userType; }
    public String getPassword()     { return password; }
    
    // methods
    
    // view user
    public void viewUser() {
    	System.out.printf("Username: %s\n Address: %s\n Firstname: %s\n Middlename: %s\n Lastname: %s\n"
    			+ "Type: %s\n Password: %s\n \n", username, emailAddress, firstName, middleName, lastName, userType, password);
    }
    
    
  
}