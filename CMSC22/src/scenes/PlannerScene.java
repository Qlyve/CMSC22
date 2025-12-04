package scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import uiandlogic.User;

public class PlannerScene extends BaseScene {
    
    private User currentUser;
    
    public PlannerScene(User user) {
        this.currentUser = user;
    }
    
    @Override
    protected Parent buildScene() {
    	// main container
    	HBox mainContainer = new HBox();
        // create and add the sidebar
        Sidebar sidebar = new Sidebar(scale, "Planner", currentUser);
        
        // create background area 
        StackPane backgroundArea = new StackPane();
        backgroundArea.setAlignment(Pos.CENTER);
        backgroundArea.setStyle("-fx-background-color: #D9D9D9;");
        backgroundArea.setPadding(new Insets(35 * scale));
        HBox.setHgrow(backgroundArea, Priority.ALWAYS);
        
        // create main area for content
        StackPane contentArea = new StackPane();
        contentArea.setAlignment(Pos.CENTER);
        contentArea.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #222222; -fx-border-width: 1px;");
        contentArea.setPrefWidth(1050 * scale);
        contentArea.setPrefWidth(970 * scale);
        
        // lay the element in a vbox
        VBox layout = new VBox(20);
//        layout.setPadding(new Insets(20));
        
        // title container 
        HBox titleContainer = new HBox();
        Label title = new Label("Planner");
        title.setPadding(new Insets(10, 0, 0, 10));
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: #222222; -fx-font-weight: bold;");
//        HBox.setHgrow(titleContainer, Priority.ALWAYS);
        titleContainer.getChildren().addAll(title);
        
        // schedule container and enrolled courses container
        HBox schedAndEnrolledContainer = new HBox();
        
        VBox scheduleMainContainer = new VBox();
        scheduleMainContainer.setPrefHeight(542 * scale);
        scheduleMainContainer.setPrefWidth(707 * scale);
        scheduleMainContainer.setStyle("-fx-border-color: #222222; -fx-border-width: .5px;");
        
        
        HBox weeklySchedTitleContainer = new HBox(5);
        weeklySchedTitleContainer.setPadding(new Insets(10));
        Label weeklySched = new Label("Weekly Schedule");
        weeklySched.setStyle("-fx-font-size: 16px; -fx-text-fill: #222222; ");
        Button tempButton1 = new Button("1");
        Button tempButton2 = new Button("2");
        Button tempButton3 = new Button("3");
        
        // makes button on the right side while label on the left side
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        weeklySchedTitleContainer.getChildren().addAll(weeklySched, spacer, tempButton1, tempButton2, tempButton3);
       
        // continer for sched specifically
        StackPane schedContainer = new StackPane();
        Label tempSched = new Label("Schedule here");
        schedContainer.getChildren().addAll(tempSched);
        VBox.setVgrow(schedContainer, Priority.ALWAYS);
        
        scheduleMainContainer.getChildren().addAll(weeklySchedTitleContainer,schedContainer );
        
        // container for enrolled courses
        StackPane enrolledContainer = new StackPane();
        enrolledContainer.setStyle("-fx-border-color: #222222; -fx-border-width: .5px;");
        enrolledContainer.setPrefHeight(542 * scale);
        enrolledContainer.setPrefWidth(343 * scale);
        Label tempEnrolled = new Label("Enrolled Courses Here");
        enrolledContainer.getChildren().addAll(tempEnrolled);
        
        HBox.setHgrow(schedAndEnrolledContainer, Priority.ALWAYS);
        schedAndEnrolledContainer.getChildren().addAll(scheduleMainContainer, enrolledContainer);
        
        // available sections
        HBox sectionContainer = new HBox();
        Label tempSection = new Label("Sections Here");
        HBox.setHgrow(sectionContainer, Priority.ALWAYS);
        sectionContainer.getChildren().addAll(tempSection);
        

        layout.getChildren().addAll(titleContainer,  schedAndEnrolledContainer, sectionContainer);
        contentArea.getChildren().add(layout);
        backgroundArea.getChildren().addAll(contentArea);
        mainContainer.getChildren().addAll(sidebar.getSidebar(), backgroundArea);
        
        return mainContainer;
    }
    // METHODS AND FUNCTIONS
}