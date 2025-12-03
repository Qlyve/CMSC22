package application.controller;

import application.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import uiandlogic.User;
import javafx.scene.control.Button;

public class ProfileController {
	// FXML elements to display user data
    @FXML private Label firstNameLabel;
    @FXML private Label middleNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label emailLabel;
    @FXML private Label userTypeLabel;

    // FXML buttons for navigation
    @FXML private Button dashboardBtn;
    @FXML private Button coursesBtn;
    @FXML private Button plannerBtn;
    @FXML private Button aboutBtn;


    // Initializes the controller after its root element has been completely processed.
    @FXML
    public void initialize() {
        // Load the current user data and populate the labels
        User currentUser = SceneManager.getUser();

        if (currentUser != null) {
            firstNameLabel.setText(currentUser.getFirstName());
            // middle name might be null or empty
            String middleName = currentUser.getMiddleName();
            middleNameLabel.setText(middleName != null && !middleName.isEmpty() ? middleName : "N/A");
            lastNameLabel.setText(currentUser.getLastName());
            emailLabel.setText(currentUser.getEmailAddress());
            userTypeLabel.setText(currentUser.getUserType());
        } else {
            // Handle case where no user is logged in (e.g., set placeholders or log error)
            System.err.println("No user currently logged in to display profile.");
            // For preview, we can manually set a dummy user if needed, but in the real app,
            // this indicates a navigation error.
        }
    }

    // Navigation handlers
    @FXML
    private void showDashboard() {
        SceneManager.switchTo("/application/ui/dashboard.fxml");
    }

    @FXML
    private void showProfile() {
        // Already on the profile page
    }

    @FXML
    private void showCourses() {
        SceneManager.switchTo("/application/ui/course_catalog.fxml");
    }

    @FXML
    private void showPlanner() {
        // SceneManager.switchTo("/application/ui/planner.fxml");
    }

    @FXML
    private void showAbout() {
        // SceneManager.switchTo("/application/ui/about.fxml");
    }
    
    @FXML
    private void handleLogout() {
        SceneManager.setUser(null);
        SceneManager.switchTo("/application/ui/sign_in.fxml");
    }
}
