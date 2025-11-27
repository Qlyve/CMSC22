package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

// Minimal controller just for preview
public class DashboardController {

    @FXML private Label userNameLabel;
    @FXML private Label userProgramLabel;
    @FXML private Label studentNumberLabel;
    @FXML private Button dashboardBtn;
    @FXML private Button coursesBtn;
    @FXML private Button plannerBtn;
    @FXML private Button aboutBtn;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> filterComboBox;

    
    @FXML
    private void initialize() {
        // just for preview
        System.out.println("Dashboard loaded!");
    }
    
    @FXML private void showDashboard() { }
    @FXML private void showCourses() { }
    @FXML private void showPlanner() { }
    @FXML private void showAbout() { }
    @FXML private void handleLogout() { }
}