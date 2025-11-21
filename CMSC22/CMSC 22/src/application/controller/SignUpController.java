package application.controller;

import application.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import uiandlogic.User;

public class SignUpController {
    
	// added @FXML to all attributes
	@FXML private TextField emailField;
	@FXML private TextField firstNameField;
	@FXML private TextField middleNameField;
	@FXML private TextField lastNameField;
	@FXML private TextField categoryField;
	@FXML private PasswordField passwordField;
	@FXML private PasswordField confirmPasswordField;
	@FXML private CheckBox showPasswordCheck;

    public void initialize() {
        System.out.println("SignUpController initialized!");
    }
    
    // method that checks the added user and updates the data base based on the newly created user
    @FXML
    private void handleSignUp() {    	
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
        	// output red warning in the ui
            System.out.println("Passwords do not match.");
            return;
        }
        
        // removed one of the parameter since username is automatically set as the first name (for now)
        User newUser = new User(
        	emailField.getText(),
            firstNameField.getText(),
            middleNameField.getText(),
            lastNameField.getText(),
            categoryField.getText(),
            passwordField.getText()
        );
        
        // update userList through SceneManager
        SceneManager.getDataAccess().addUser(newUser);
        
        // console prints just for checking
        System.out.println("Account created!");
        System.out.println("Name: " + newUser.getFirstName() + " " +
        							  newUser.getMiddleName() + " " +
        							  newUser.getLastName());
        System.out.println("Email: " + newUser.getEmailAddress());
        System.out.println("Password: " + newUser.getPassword());
        
        // go back to sign in page
        SceneManager.switchTo("/application/ui/sign_in.fxml");
    }
}