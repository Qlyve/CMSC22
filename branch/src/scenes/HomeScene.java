package scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.effect.DropShadow;
import uiandlogic.User;

public class HomeScene extends BaseScene {

    private User currentUser;

    public HomeScene(User user) {
        this.currentUser = user;
    }

    @Override
    protected Parent buildScene() {
        HBox mainContainer = new HBox();
        mainContainer.setStyle("-fx-background-color: #E5E5E5;");
        
        // Sidebar
        Sidebar sidebar = new Sidebar(scale, "Home", currentUser);
        
        // Right side of the screen (main content)
        StackPane contentArea = new StackPane();
        contentArea.setAlignment(Pos.CENTER);
        contentArea.setPrefWidth(970 * scale);
        HBox.setHgrow(contentArea, Priority.ALWAYS);
        
        VBox contentPanels = new VBox(20);
        contentPanels.setAlignment(Pos.TOP_CENTER);
        contentPanels.setPadding(new Insets(40));
        
        // Top panel that contains the "Welcome" and description
        VBox topPanel = new VBox(10);
        topPanel.setPadding(new Insets(20));
        topPanel.setAlignment(Pos.CENTER_LEFT);

        topPanel.setBackground(new Background(
            new BackgroundFill(Color.web("#f5f5f5"), new CornerRadii(10), Insets.EMPTY)
        ));

        Label greetingLabel = new Label("Good day, " + currentUser.getFirstName() + "!");
        greetingLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        
        Label descriptionLabel = new Label("Welcome to *website name*! This is a website that assists students with course registration plan. This website allows students to add, delete, and edit courses in their planner.\n" +
        								   "\n" +
        								   "This system is co-created by a group of students from CMSC 22 CD-5L in compliance to their requirement in CMSC 22 laboratory. To get started, please refer to the instructions below.");
        descriptionLabel.setStyle("-fx-font-size: 18px;");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setPadding(new Insets(0, 5, 5, 5));
        
        topPanel.getChildren().add(greetingLabel);
        topPanel.getChildren().add(descriptionLabel);

        topPanel.setMaxWidth(1000);
        
        // Bottom panel that contains the instructions
        VBox bottomPanel = new VBox(10);
        bottomPanel.setPadding(new Insets(20));
        bottomPanel.setAlignment(Pos.TOP_LEFT);

        bottomPanel.setBackground(new Background(
            new BackgroundFill(Color.web("#ffffff"), new CornerRadii(10), Insets.EMPTY)
        ));

        Label instructionHeader = new Label("How to Create a Planner:");
        instructionHeader.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label steps = new Label(
            "1. Click the planner tab found in the sidebar.\n" +
            "2. You may scroll through all the listed courses in the bottom part of the page or you may type in the course that you want in the search bar.\n" +
            "3. If you have found your course, click the bar that corresponds to the course that you want to show all available sections.\n" +
            "4. Click the add button in the action column to add your preferred section to your planner.\n"
        );

        steps.setStyle("-fx-font-size: 18px;");
        steps.setWrapText(true);
        steps.setPadding(new Insets(0, 5, 5, 5));
        
        Label notesHeader = new Label("Notes:");
        notesHeader.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        Label notes = new Label("● If no section appeared below the course bar, it means the course is not being offered for the current semester.\n" +
        						"● Users are not able to add two different sections from the same course.\n" + 
        						"● Users are not able able to add sections that are conflicting with their current planner.\n" +
        						"● You may view the full details of the course in the Course Catalog tab in the sidebar.");
        
        notes.setStyle("-fx-font-size: 18px;");
        notes.setWrapText(true);
        notes.setPadding(new Insets(0, 5, 5, 5));
        
        bottomPanel.getChildren().addAll(instructionHeader, steps, notesHeader, notes);
        bottomPanel.setMaxWidth(1000);
        
        // Create shadow effects
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(0);
        shadow.setOffsetY(4);
        shadow.setRadius(10);
        shadow.setColor(Color.color(0, 0, 0, 0.25));

        // Apply to both panels
        topPanel.setEffect(shadow);
        bottomPanel.setEffect(shadow);

        // Add both panels to the content container
        contentPanels.getChildren().addAll(topPanel, bottomPanel);
        
        ScrollPane scroll = new ScrollPane(contentPanels);
        scroll.setFitToWidth(true); 
        scroll.setPannable(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);  
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle("-fx-background-color: transparent;");
        
        // Add content panels into the central content area
        contentArea.getChildren().add(scroll);

        // Final composition
        mainContainer.getChildren().addAll(sidebar.getSidebar(), contentArea);
        System.out.println("Pressed Home! Switching");
        return mainContainer;
    }
}
