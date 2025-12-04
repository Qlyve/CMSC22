package application.controller;

import application.SceneManager;
import uiandlogic.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SignUpController {
    
    @FXML private TextField emailField;
    @FXML private TextField firstNameField;
    @FXML private TextField middleNameField;
    @FXML private TextField lastNameField;

    @FXML private ComboBox<String> categoryCombo;

    @FXML private PasswordField passwordField;
    @FXML private TextField passwordVisibleField;

    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField confirmPasswordVisibleField;

    @FXML private CheckBox showPasswordCheck;

    @FXML
    public void initialize() {
        System.out.println("SignUpController initialized!");

        passwordVisibleField.textProperty().bindBidirectional(passwordField.textProperty());
        confirmPasswordVisibleField.textProperty().bindBidirectional(confirmPasswordField.textProperty());
    }


    @FXML
    private void togglePassword() {
        boolean show = showPasswordCheck.isSelected();


        passwordVisibleField.setVisible(show);
        passwordVisibleField.setManaged(show);
        passwordField.setVisible(!show);
        passwordField.setManaged(!show);

        confirmPasswordVisibleField.setVisible(show);
        confirmPasswordVisibleField.setManaged(show);
        confirmPasswordField.setVisible(!show);
        confirmPasswordField.setManaged(!show);
    }


    @FXML
    private void handleSignUp() {

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            System.out.println("Passwords do not match.");
            return;
        }

        User newUser = new User(
            emailField.getText(),
            firstNameField.getText(),
            middleNameField.getText(),
            lastNameField.getText(),
            categoryCombo.getValue(),  
            passwordField.getText()
        );

        SceneManager.getDataAccess().addUser(newUser);

        System.out.println("Account created!");

        SceneManager.switchTo("/application/ui/sign_in.fxml");
    }
}