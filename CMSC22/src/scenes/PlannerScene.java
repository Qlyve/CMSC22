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
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uiandlogic.Course;
import uiandlogic.Section;
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

        layout.getChildren().addAll(titleContainer,  schedAndEnrolledContainer, buildSection());
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
        header.setStyle("-fx-background-color: #2F4156; -fx-background-radius: 5;");
        
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
		
		// ==Search Bar==
		HBox searchBar = new HBox();
		searchBar.setSpacing(10);
		searchBar.setAlignment(Pos.CENTER);

		TextField searchField = new TextField();
		searchField.setPromptText("Search specific course...");
		searchField.setPrefWidth(800);
		searchField.setPrefHeight(70);
		searchField.setStyle("-fx-border-color: #2F4156; -fx-border-width: 2; -fx-border-radius: 5;");
		
		searchBar.getChildren().add(searchField);
		sectionContainer.getChildren().add(searchBar);
		
		// Add header between the search bar and the main content
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
        
        Runnable refreshDropdowns = () -> {

            dropdownContainer.getChildren().clear();
            String search = searchField.getText().toLowerCase().trim();
        
		    for (Course course: masterList) {
		    	
		    	if (!search.isEmpty()) {
	                boolean match =
	                        course.getCourseCode().toLowerCase().contains(search) ||
	                        course.getCourseName().toLowerCase().contains(search);
	
	                if (!match) continue;
	            }
		    	
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
	
		        HBox dropCodeCol = makeColumn(dropColCourseCode, 65);
		        dropCodeCol.setAlignment(Pos.CENTER_LEFT);
		        HBox dropTitleCol = makeColumn(dropColCourseTitle, 120);
		        HBox dropUnitsCol = makeColumn(dropColUnits, 50);
		        HBox dropSectionCol = makeColumn(dropColSection, 50);
		        HBox dropTimeCol = makeColumn(dropColTime, 90);
		        HBox dropActionCol = makeColumn(dropColAction, 100);
	
		        rowHeader.getChildren().addAll(dropCodeCol, dropTitleCol, dropUnitsCol, dropSectionCol, dropTimeCol, dropActionCol);
	
		        dropdown.setGraphic(rowHeader);
		        	
		        VBox tableContainer = new VBox();	        
		        tableContainer.setSpacing(0);
	
		        for (Section section: course.getSection()) {
		        	
		        	if(!section.getSectionCode().contains("-")) {
		        		continue;
		        	}	
		        	
		            HBox row = new HBox();
		            row.setSpacing(40);
		            row.setPadding(new Insets(7));
		            row.setStyle("-fx-border-color: black; -fx-border-width: 0.7; -fx-border-radius: 5;");
		            
		            Label contentCode = new Label(section.getCourseCode());
		            Label contentName = new Label(section.getCourseName());
		            Label contentUnit = new Label(course.getUnits());
		            Label contentSection = new Label(section.getSectionCode());
		            Label contentTime = new Label(section.getTime());
		            Label contentAction = new Label("Edit");
	
		            row.getChildren().addAll(
	            		makeColumn(contentCode, 80),
	            		makeColumn(contentName, 132),
	            		makeColumn(contentUnit, 37),
		                makeColumn(contentSection, 60),
		                makeColumn(contentTime, 90),
		                makeColumn(contentAction, 100)
		            );
	
		            tableContainer.getChildren().add(row);
		        }
		        
		        if(course.getSection().isEmpty()) {
		        	Label noSection = new Label("No sections available.");
		        	noSection.setPadding(new Insets(5));
		        	noSection.setFont(new Font(16));
		        	
		        	tableContainer.getChildren().add(noSection);
		        }
	        	
		        dropdown.setContent(tableContainer);
		        dropdown.setExpanded(false);
		        dropdownContainer.getChildren().add(dropdown);
		    }
		    
		    if (dropdownContainer.getChildren().isEmpty()) {
	            Label emptyMsg = new Label("No courses match your search.");
	            emptyMsg.setPadding(new Insets(10));
	            emptyMsg.setStyle("-fx-font-size: 14;");
	            dropdownContainer.getChildren().add(emptyMsg);
	        }
        };
	    
        refreshDropdowns.run();
        
        searchField.textProperty().addListener((obs, oldVal, newVal) -> refreshDropdowns.run());
        
	    ScrollPane scrollPane = new ScrollPane(dropdownContainer);
	    scrollPane.setFitToWidth(true);
	    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
	    scrollPane.setPrefHeight(200);

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