package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    
    private TextField emailField;
    private TextField firstNameField;
    private TextField middleNameField;
    private TextField lastNameField;
    private TextField categoryField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;
    private CheckBox showPasswordCheck;

    public void initialize() {
        System.out.println("SignUpController initialized!");
    }
}