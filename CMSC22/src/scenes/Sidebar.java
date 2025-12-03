package scenes;

import application.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uiandlogic.User;

public class Sidebar {
    
    private double scale;
    private VBox sidebarContainer;
    // for tracking current scene
    private String currentPage;
    private User currentUser;
    
    public Sidebar(double scale, String currentPage, User user) {
        this.scale = scale;
        this.currentPage = currentPage;
        this.currentUser = user;
        buildSidebar();
    }
    
    private void buildSidebar() {
        sidebarContainer = new VBox(6);
        sidebarContainer.setPrefWidth(322 * scale);
        sidebarContainer.setStyle("-fx-background-color: #2F4156; -fx-stroke: #222222; -fx-border-width: 1");
        sidebarContainer.setAlignment(Pos.TOP_CENTER);

        // title
        HBox titleContainer = new HBox();
        titleContainer.setPrefHeight(128 * scale);
        titleContainer.setPrefWidth(320 * scale);
        titleContainer.setAlignment(Pos.CENTER);
        
        Label title = new Label("ICS");
        title.setStyle("-fx-font-size: 48px; -fx-text-fill: white; -fx-font-weight: bold;");
        titleContainer.setStyle("-fx-background-color: #253344;");
        titleContainer.getChildren().add(title);

        // buttons
        Button profileButton = createSidebarButton("Profile", "/icons/profile_icon.png");
        Button homeButton = createSidebarButton("Home", "/icons/home_icon.png");
        Button CCButton = createSidebarButton("Course Catalog", "/icons/course_catalog_icon.png");
        Button plannerButton = createSidebarButton("Planner", "/icons/planner_icon.png");
        Button aboutUsButton = createSidebarButton("About us", "/icons/about_us_icon.png");
        Button signOutButton = createSidebarButton("Sign out", "/icons/sign_out_icon.png");

        // button actions
        profileButton.setOnAction(e -> navigateTo("Profile"));
        homeButton.setOnAction(e -> navigateTo("Home"));
        CCButton.setOnAction(e -> navigateTo("CourseCatalog"));
        plannerButton.setOnAction(e -> navigateTo("Planner"));
        aboutUsButton.setOnAction(e -> navigateTo("AboutUs"));
        signOutButton.setOnAction(e -> signOut());

        // highlight active page
        highlightActiveButton(profileButton, "Profile");
        highlightActiveButton(homeButton, "Home");
        highlightActiveButton(CCButton, "CourseCatalog");
        highlightActiveButton(plannerButton, "Planner");
        highlightActiveButton(aboutUsButton, "AboutUs");

        // spacer to push sign out button at bottom
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebarContainer.getChildren().addAll(
            titleContainer,
            profileButton,
            homeButton,
            CCButton,
            plannerButton,
            aboutUsButton,
            spacer,
            signOutButton
        );
    }
    
    private Button createSidebarButton(String text, String iconPath) {
    	ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
        icon.setFitWidth(27);
        icon.setFitHeight(27);

        // Create label
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        // Put icon + label in HBox
        HBox content = new HBox(10, icon, label);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPrefWidth(260 * scale); 
        
    	Button button = new Button();
        button.setGraphic(content);
        button.setPrefWidth(302 * scale);
        button.setPrefHeight(56.12 * scale);
        button.setAlignment(Pos.CENTER);
        button.setUserData(false); // for tracking which is active
        button.setStyle("-fx-background-color: #2F4156; -fx-text-fill: white; -fx-font-size: 12px;");
        
        // hover effect
        button.setOnMouseEntered(e -> {
        	// if NOT active DONT change styles	
            if (!(boolean) button.getUserData()) {
                button.setStyle("-fx-background-color: #3A5169; -fx-text-fill: white; -fx-font-size: 12px;");
            }
        });
        button.setOnMouseExited(e -> {
            if (!(boolean) button.getUserData()) {
                button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 12px;");
            }
        });
        
        return button;
    }
    
    private void highlightActiveButton(Button button, String pageName) {
        if (currentPage.equals(pageName)) {
        	// CHANGE style if active
        	button.setUserData(true);
            button.setStyle("-fx-background-color: #253344; -fx-text-fill: white; -fx-font-size: 12px;");
        }
    }
    
    
    // change to account instead of user
    // navigation
    private void navigateTo(String page) {
        switch(page) {
            case "Profile":
            	System.out.println("Pressed Profile! Switching");
                SceneManager.switchTo(new ProfileScene(currentUser));
                break;
            case "Home":
                SceneManager.switchTo(new HomeScene(currentUser));
                break;
            case "CourseCatalog":
            	System.out.println("Pressed Course Catalog! Switching");
                SceneManager.switchTo(new CourseCatalogScene(currentUser));
                break;
            case "Planner":
            	System.out.println("Pressed Planner! Switching");
                SceneManager.switchTo(new PlannerScene(currentUser));
                break;
            case "AboutUs":
            	System.out.println("Pressed About Us! Switching");
                SceneManager.switchTo(new AboutUsScene(currentUser));
                break;
        }
    }
    
    private void signOut() {
        SceneManager.switchTo("/application/ui/sign_in.fxml");
    }
    
    public VBox getSidebar() {
        return sidebarContainer;
    }
}