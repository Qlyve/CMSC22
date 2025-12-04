package application.controller;

import application.SceneManager;
import uiandlogic.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    @FXML private Label emailError;
    @FXML private Label firstNameError;
    @FXML private Label middleNameError;
    @FXML private Label lastNameError;
    @FXML private Label categoryError;
    @FXML private Label passwordError;
    @FXML private Label confirmPasswordError;

    @FXML
    public void initialize() {

        passwordVisibleField.textProperty().bindBidirectional(passwordField.textProperty());
        confirmPasswordVisibleField.textProperty().bindBidirectional(confirmPasswordField.textProperty());


        categoryCombo.getItems().addAll("BSCS", "MIT", "MSCS", "PHD");
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
        boolean valid = true;
        clearErrors();
        
        String email = emailField.getText().trim();

        if (emailField.getText().isEmpty()) {
            showError(emailField, emailError, "*Please add your Email Address");
            valid = false;
        } else if (!email.endsWith("@up.edu.ph")) {
            showError(emailField, emailError, "*Please end your email in @up.edu.ph");
            valid = false;
        }


        if (firstNameField.getText().isEmpty()) {
            showError(firstNameField, firstNameError, "*Please add your First Name");
            valid = false;
        }


        if (middleNameField.getText().isEmpty()) {
            showError(middleNameField, middleNameError, "*Please add your Middle Name");
            valid = false;
        }


        if (lastNameField.getText().isEmpty()) {
            showError(lastNameField, lastNameError, "*Please add your Last Name");
            valid = false;
        }


        if (categoryCombo.getValue() == null) {
            categoryError.setText("*Please select a category");
            categoryError.setVisible(true);
            categoryError.setManaged(true);
            categoryCombo.setStyle("-fx-border-color: red;");
            valid = false;
        }


        if (passwordField.getText().isEmpty()) {
            showError(passwordField, passwordError, "*Please add your password");
            valid = false;
        }


        if (confirmPasswordField.getText().isEmpty()) {
            showError(confirmPasswordField, confirmPasswordError, "*Please add your password");
            valid = false;
        }


        if (!passwordField.getText().isEmpty() && !confirmPasswordField.getText().isEmpty()) {
            if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                confirmPasswordError.setText("Passwords do not match");
                confirmPasswordError.setVisible(true);
                confirmPasswordError.setManaged(true);

                highlightPasswordMismatch();
                valid = false;
            }
        }

        if (!valid) return;


        User newUser = new User(
            emailField.getText(),
            firstNameField.getText(),
            middleNameField.getText(),
            lastNameField.getText(),
            categoryCombo.getValue(),
            passwordField.getText()
        );


        saveUserToFile(newUser);


        SceneManager.getDataAccess().addUser(newUser);


        SceneManager.switchTo("/application/ui/sign_in.fxml");
    }

    private void highlightPasswordMismatch() {
        passwordField.setStyle("-fx-border-color: red;");
        passwordVisibleField.setStyle("-fx-border-color: red;");
        confirmPasswordField.setStyle("-fx-border-color: red;");
        confirmPasswordVisibleField.setStyle("-fx-border-color: red;");
    }

    private void showError(TextField field, Label label, String message) {
        label.setText(message);
        label.setVisible(true);
        label.setManaged(true);
        field.setStyle("-fx-border-color: red;");
    }

    private void showError(PasswordField field, Label label, String message) {
        label.setText(message);
        label.setVisible(true);
        label.setManaged(true);
        field.setStyle("-fx-border-color: red;");
        if (field == passwordField) passwordVisibleField.setStyle("-fx-border-color: red;");
        if (field == confirmPasswordField) confirmPasswordVisibleField.setStyle("-fx-border-color: red;");
    }

    private void clearErrors() {

        emailError.setVisible(false); emailError.setManaged(false);
        firstNameError.setVisible(false); firstNameError.setManaged(false);
        middleNameError.setVisible(false); middleNameError.setManaged(false);
        lastNameError.setVisible(false); lastNameError.setManaged(false);
        categoryError.setVisible(false); categoryError.setManaged(false);
        passwordError.setVisible(false); passwordError.setManaged(false);
        confirmPasswordError.setVisible(false); confirmPasswordError.setManaged(false);


        emailField.setStyle("");
        firstNameField.setStyle("");
        middleNameField.setStyle("");
        lastNameField.setStyle("");
        categoryCombo.setStyle("");
        passwordField.setStyle("");
        passwordVisibleField.setStyle("");
        confirmPasswordField.setStyle("");
        confirmPasswordVisibleField.setStyle("");
    }

    private void saveUserToFile(User user) {
        try {
            
            File file = new File("src/data/users.txt");


            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(user.getEmailAddress() + "," +
                             user.getFirstName() + "," +
                             user.getMiddleName() + "," +
                             user.getLastName() + "," +
                             user.getUserType() + "," +
                             user.getPassword());
                writer.newLine();
            }

            System.out.println("User saved to: " + file.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}