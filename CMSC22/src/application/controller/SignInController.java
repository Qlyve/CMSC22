package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uiandlogic.*;

public class SignInController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox showPasswordCheck;

    @FXML
    private Hyperlink createAccountLink;
    
    private DataAccess dataAccess;

 
    @FXML
    private void initialize() {
        createAccountLink.setOnAction(event -> openSignUp());
    }

    @FXML
    private void handleSignIn() {
        String email = emailField.getText();
        String password = passwordField.getText();

        DataAccess dataAccess = DataAccess.getInstance(); 
        Login loginSystem = new Login(dataAccess);
        User authenticatedUser = loginSystem.authenticate(email, password);

        if (authenticatedUser != null) {
            openDashboard(authenticatedUser);
        } else {
            // Show error
        }
    }
    



    private void openSignUp() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/application/ui/sign_up.fxml")
            );

            Stage stage = (Stage) createAccountLink.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}