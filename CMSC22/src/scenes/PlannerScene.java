package scenes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import uiandlogic.Course;
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
        HBox.setHgrow(sectionContainer, Priority.ALWAYS);
        
		
        sectionContainer.getChildren().addAll(buildSection());
        

        layout.getChildren().addAll(titleContainer,  schedAndEnrolledContainer, sectionContainer);
        contentArea.getChildren().add(layout);
        backgroundArea.getChildren().addAll(contentArea);
        mainContainer.getChildren().addAll(sidebar.getSidebar(), backgroundArea);
        
        return mainContainer;
    }
    
    // METHODS AND FUNCTIONS
    private VBox buildSection() {
    	VBox sectionContainer = new VBox();
    	sectionContainer.setSpacing(10);
    	sectionContainer.setPadding(new Insets(10));
    	
    	// ==Header==
    	HBox header = new HBox();
        header.setPadding(new Insets(10));
        header.setSpacing(40);
        header.setStyle("-fx-background-color: #293b4d; -fx-background-radius: 5;");
        
		Label colCourseCode = new Label("Course Code");
		Label colCourseTitle = new Label("Course Title");
		Label colUnits = new Label("Units");
		Label colSection = new Label("Section");
		Label colTime = new Label("Time");
		Label colAction = new Label("Action");
		
		List<Label> labels = Arrays.asList(
		        colCourseCode, colCourseTitle, colUnits, colSection, colTime, colAction
		);

		for (Label lbl : labels) {
		    lbl.setTextFill(Color.WHITE);
		    lbl.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
		}
		
		HBox codeCol = makeColumn(colCourseCode, 150);
		HBox titleCol = makeColumn(colCourseTitle, 130);
		HBox unitsCol = makeColumn(colUnits,  100);
		HBox sectionCol = makeColumn(colSection, 100);
		HBox timeCol = makeColumn(colTime, 120);
	    HBox actionCol  = makeColumn(colAction, 150);

		header.getChildren().addAll(
				codeCol,
				titleCol,
				unitsCol,
				sectionCol,
				timeCol,
				actionCol
		);
		
		header.setPrefWidth(700); 
		sectionContainer.setAlignment(Pos.CENTER); 
		
		sectionContainer.getChildren().add(header);
		
		// ==Content Section==
		VBox dropdownContainer = new VBox();
	    dropdownContainer.setSpacing(5);
	    
	    ArrayList<ObservableList<Course>> listOfLists = SceneManager.getDataAccess().getMasterList();
        ObservableList<Course> masterList = FXCollections.observableArrayList();
        for(ObservableList<Course> list: listOfLists) {
        	masterList.addAll(list);
        }

	    for (Course course: masterList) {
	        TitledPane dropdown = new TitledPane();

	        HBox rowHeader = new HBox();
	        rowHeader.setSpacing(40);
	        rowHeader.setPadding(new Insets(5));

	        Label dropColCourseCode = new Label(course.getCourseCode());
	        Label dropColCourseTitle = new Label(course.getCourseName());
	        Label dropColUnits = new Label(course.getUnits()); 
	        Label dropColSection = new Label("-");
	        Label dropColTime = new Label("-");
	        Label dropColAction = new Label("Action");

	        List<Label> dropLabels = Arrays.asList(dropColCourseCode, dropColCourseTitle, dropColUnits, dropColSection, dropColTime, dropColAction);
	        for (Label lbl : dropLabels) {
	            lbl.setTextFill(Color.BLACK);
	            lbl.setStyle("-fx-font-size: 13;");
	        }

	        HBox dropCodeCol = makeColumn(dropColCourseCode, 80);
	        dropCodeCol.setAlignment(Pos.CENTER_LEFT);
	        HBox dropTitleCol = makeColumn(dropColCourseTitle, 100);
	        HBox dropUnitsCol = makeColumn(dropColUnits, 50);
	        HBox dropSectionCol = makeColumn(dropColSection, 70);
	        HBox dropTimeCol = makeColumn(dropColTime, 67);
	        HBox dropActionCol = makeColumn(dropColAction, 130);

	        rowHeader.getChildren().addAll(dropCodeCol, dropTitleCol, dropUnitsCol, dropSectionCol, dropTimeCol, dropActionCol);

	        dropdown.setGraphic(rowHeader);

	        Label placeholder = new Label("sections");
	        placeholder.setPadding(new Insets(10));
	        dropdown.setContent(placeholder);

	        dropdown.setExpanded(false);
	        dropdownContainer.getChildren().add(dropdown);
	    }
	    
	    ScrollPane scrollPane = new ScrollPane(dropdownContainer);
	    scrollPane.setFitToWidth(true);
	    scrollPane.setPrefHeight(250);

	    sectionContainer.getChildren().add(scrollPane);
		
    	return sectionContainer;
    }
    
    private HBox makeColumn(Label label, double width) {
        HBox box = new HBox(label);
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(width);
        return box;
    }
}