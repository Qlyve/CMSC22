// JUST DRAFT FOR OTHER SCENES

package scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LoginScene extends BaseScene {

    private TextField usernameField;
    private PasswordField passwordField;

    @Override
    protected Parent buildScene() {

        // main container
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(50));

        // for centering content
        VBox centerBox = new VBox(15);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setMaxWidth(568 * scale);
        centerBox.setMaxHeight(598 * scale);

        // title
        Label title = new Label("Sign-in");

        // username container
        VBox usernameBox = new VBox(5);
        Label usernameLabel = new Label("EMAIL:");

        usernameField = new TextField();
        usernameField.setPromptText("Enter email");
        usernameField.setPrefWidth(466.36);
        usernameField.setPrefHeight(50);

        usernameBox.getChildren().addAll(usernameLabel, usernameField);

        // password container
        VBox passwordBox = new VBox(5);
        Label passwordLabel = new Label("PASSWORD:");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setPrefWidth(466.36);
        passwordField.setPrefHeight(50);

        // show password
        CheckBox checkPassBox = new CheckBox("show");

        passwordBox.getChildren().addAll(passwordLabel, passwordField, checkPassBox);

        // sign in button
        Button loginButton = new Button("SIGN IN");
        loginButton.setPrefWidth(466.36);
        loginButton.setPrefHeight(50);
        loginButton.setOnAction(e -> handleLogin());

        // message label (for errors/success)
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");

        // add all components to centerBox
        centerBox.getChildren().addAll(
            title,
            usernameBox,
            passwordBox,
            loginButton,
            messageLabel
        );

        // Set centerBox to the center of BorderPane
        root.setCenter(centerBox);

        return root;
    }


    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Simple validation
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in all fields");
            return;
        }

        // Simple authentication (replace with real logic)
        if (username.equals("admin") && password.equals("admin")) {
            // Login successful - switch to home scene
            SceneManager.switchTo(new HomeScene(username));
        } else {
            showAlert("Error", "Invalid username or password");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}