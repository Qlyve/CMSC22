package application.controller;

import application.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import scenes.HomeScene;
import uiandlogic.User;

public class SignInController {

    @FXML
    private TextField emailField;

    @FXML
    private CheckBox showPasswordCheck;

    @FXML
    private Hyperlink createAccountLink;

    @FXML
    private Hyperlink forgotPasswordLink;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private TextField passwordVisibleField;

    @FXML
    private Label errorLabel;
    
    @FXML
    private Label emailDomainErrorLabel;

    
    @FXML
    private void initialize() {
    	 passwordVisibleField.textProperty().bindBidirectional(passwordField.textProperty());
        createAccountLink.setOnAction(event -> openSignUp());
        
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    @FXML
    private void handleSignIn() {
    	String email = emailField.getText().trim();
        String password = passwordField.getText();
        
        System.out.println("Email: " + emailField.getText());
        System.out.println("Password: " + passwordField.getText());
        
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
        emailDomainErrorLabel.setVisible(false);
        emailDomainErrorLabel.setManaged(false);
        emailField.setStyle("");
        passwordField.setStyle("");
        passwordVisibleField.setStyle("");
        
        if (!email.endsWith("@up.edu.ph")) {
            emailDomainErrorLabel.setText("*Please end your email with @up.edu.ph");
            emailDomainErrorLabel.setVisible(true);
            emailDomainErrorLabel.setManaged(true);

            emailField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return;
        }
        
        boolean validUser = false;
        
        for (User user:  SceneManager.getDataAccess().getUsers()) {
        	if(user.getEmailAddress().equals(email) 
        			&& user.getPassword().equals(password)) {
        		SceneManager.switchTo(new HomeScene(user));
        		validUser = true;
        		break;
        	}
        }
        if (!validUser) {
        	errorLabel.setText("*Wrong email or password");
        	errorLabel.setVisible(true);
        	errorLabel.setManaged(true);
        	
        	 emailField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
             passwordField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
             passwordVisibleField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        	
            System.out.println("Wrong email or password");
        }else {
        	errorLabel.setVisible(false);
        	errorLabel.setManaged(false);
        	
        	emailField.setStyle("");
            passwordField.setStyle("");
            passwordVisibleField.setStyle("");
        }
    }
    
    private void openSignUp() {
        SceneManager.switchTo("/application/ui/sign_up.fxml");
    }
    
    @FXML
    private void openForgotPassword() {
    	SceneManager.switchTo("/application/ui/forgot_password.fxml");
    }
    @FXML
    private void togglePassword() {
        boolean show = showPasswordCheck.isSelected();


        passwordVisibleField.setVisible(show);
        passwordVisibleField.setManaged(show);
        passwordField.setVisible(!show);
        passwordField.setManaged(!show);

    }
}

	