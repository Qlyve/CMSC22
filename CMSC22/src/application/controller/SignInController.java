package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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