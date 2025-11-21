package application.controller;

import application.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import uiandlogic.User;

public class SignInController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox showPasswordCheck;

    @FXML
    private Hyperlink createAccountLink;

 
    @FXML
    private void initialize() {
        createAccountLink.setOnAction(event -> openSignUp());
    }

    @FXML
    private void handleSignIn() {
        System.out.println("Email: " + emailField.getText());
        System.out.println("Password: " + passwordField.getText());
        
        for (User user:  SceneManager.getDataAccess().getUsers()) {
        	if(user.getEmailAddress().equals(emailField.getText()) 
        			&& user.getPassword().equals(passwordField.getText())) {
        		SceneManager.switchTo("/application/ui/welcome_page.fxml");
        		return;
        	}
        }
        // dito nalang siguro yung pag show ng red mark sa scene
        System.out.println("Wrong email or password");       
    }
    
    // changed logic of scene switching for simpler logic
    private void openSignUp() {
        /*try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/application/ui/sign_up.fxml")
            );

            Stage stage = (Stage) createAccountLink.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        SceneManager.switchTo("/application/ui/sign_up.fxml");
    }
}