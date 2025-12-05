package uiandlogic;

public class Login {
	
	private final DataAccess dataAccess;
	
	// constructor
    public Login(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }
	
    // methods
    
    
    // check if user exist in data if and is exact user and password login
    public User authenticate(String username, String password) {
        for (User user : dataAccess.getUsers()) {
            if (user.getUsername().equals(username) &&
                user.getPassword().equals(password)) {
            	System.out.printf("Login successful welcome %s!\n" , user.getUsername() );
				dataAccess.loadUserPlan(user);
                return user;
            }
        }
        System.out.println("Invalid username or password");
        return null;
    }

}
	
	
