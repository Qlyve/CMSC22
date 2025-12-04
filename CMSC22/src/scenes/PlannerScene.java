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
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import uiandlogic.Course;
import uiandlogic.Section;
import uiandlogic.Schedule;
import uiandlogic.User;

public class PlannerScene extends BaseScene {
    
    private User currentUser;
    private Schedule schedule;
    
    // for refreshing schedule when updating
    private VBox enrolledListContainer; 
    private Runnable refreshDropdownsReference; 
    
    public PlannerScene(User user) {
        this.currentUser = user;
        this.schedule = new Schedule(user, scale);
        this.enrolledListContainer = new VBox(5); // Spacing 5 between enrolled items
    }
    
    @Override
    protected Parent buildScene() {
    	// main container
    	HBox mainContainer = new HBox();
    	// sidebar
        Sidebar sidebar = new Sidebar(scale, "Planner", currentUser);
        
        // background area the grey part
        StackPane backgroundArea = new StackPane();
        backgroundArea.setAlignment(Pos.CENTER);
        backgroundArea.setStyle("-fx-background-color: #E5E5E5;");
        backgroundArea.setPadding(new Insets(35 * scale));
        HBox.setHgrow(backgroundArea, Priority.ALWAYS);
        
        // where the actual elements
        StackPane contentArea = new StackPane();
        contentArea.setAlignment(Pos.CENTER);
        contentArea.setStyle("-fx-background-color: white;-fx-background-radius: 10; -fx-border-color: #CCCCCC; -fx-border-width: 1; -fx-border-radius: 10;");
        contentArea.setPrefWidth(970 * scale);
        
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(0);
        shadow.setOffsetY(4);
        shadow.setColor(Color.rgb(0, 0, 0, 0.2));
        contentArea.setEffect(shadow);
        
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        
        // title container
        HBox titleContainer = new HBox();
        Label title = new Label("Planner");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2F4156;");
        titleContainer.getChildren().addAll(title);
        titleContainer.setAlignment(Pos.CENTER); 
        
        // schedule and planned/enrolled container
        HBox schedAndEnrolledContainer = new HBox(20);
        
        // schedule container
        VBox scheduleMainContainer = new VBox();
        scheduleMainContainer.setPrefWidth(827 * scale);
        HBox.setHgrow(scheduleMainContainer, Priority.ALWAYS);
        
        // weekly title container
        HBox weeklySchedTitleContainer = new HBox();
        weeklySchedTitleContainer.setPadding(new Insets(0, 0, 10, 0));
        Label weeklySched = new Label("Weekly Schedule");
        weeklySched.setStyle("-fx-font-size: 16px; -fx-text-fill: #222222; ");
        
        weeklySchedTitleContainer.getChildren().addAll(weeklySched);
       
        // schedule panel from schedule
        ScrollPane schedContainer = schedule.getScrollableGrid();
        schedContainer.setStyle("-fx-background-color: white; -fx-background: white;");
        schedContainer.setPadding(new Insets(0,-10,0,15));
        schedContainer.setPrefHeight(500 * scale); 
        VBox.setVgrow(schedContainer, Priority.ALWAYS);
        
        scheduleMainContainer.getChildren().addAll(weeklySchedTitleContainer, schedContainer);
        
        //  enrolled/planned container
        VBox enrolledArea = new VBox(10);
        enrolledArea.setPrefWidth(243 * scale);
        enrolledArea.setStyle("-fx-border-color: #CCCCCC; -fx-border-width: 1; -fx-background-color: #F9F9F9; -fx-padding: 10;");
        
        Label enrolledTitle = new Label("Planned Courses");
        enrolledTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #2F4156;");
        
        // scrollPane for the enrolled list
        ScrollPane enrolledScroll = new ScrollPane(enrolledListContainer);
        enrolledScroll.setFitToWidth(true);
        enrolledScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        enrolledScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        VBox.setVgrow(enrolledScroll, Priority.ALWAYS);
        
        enrolledArea.getChildren().addAll(enrolledTitle, enrolledScroll);
        
        schedAndEnrolledContainer.getChildren().addAll(scheduleMainContainer, enrolledArea);

        // add everything to layout
        // implement a runable to update and the planned list to buildSection so it can update
        Runnable updateEnrolledList = () -> refreshEnrolledList();
        
        layout.getChildren().addAll(titleContainer, schedAndEnrolledContainer, buildSection(updateEnrolledList));
        
        contentArea.getChildren().add(layout);
        backgroundArea.getChildren().addAll(contentArea);
        mainContainer.getChildren().addAll(sidebar.getSidebar(), backgroundArea);
        
        refreshEnrolledList();
        
        return mainContainer;
    }
    
    // method to handle list
    private void refreshEnrolledList() {
        enrolledListContainer.getChildren().clear();
        
        if (currentUser.getPlannedCourses().isEmpty()) {
            Label empty = new Label("No courses enrolled.");
            empty.setStyle("-fx-text-fill: #777; -fx-font-style: italic; -fx-font-size: 11px;");
            enrolledListContainer.getChildren().add(empty);
            return;
        }

        for (Section section : currentUser.getPlannedCourses()) {
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 3; -fx-padding: 5;");
            
            VBox info = new VBox(2);
            Label code = new Label(section.getCourseCode());
            code.setStyle("-fx-font-weight: bold; -fx-font-size: 8px;");
            Label secInfo = new Label("Section: " + section.getSectionCode());
            secInfo.setStyle("-fx-font-size: 10px; -fx-text-fill: #555;");
            info.getChildren().addAll(code, secInfo);
            HBox.setHgrow(info, Priority.ALWAYS);
            
            Button removeBtn = new Button("x");
            removeBtn.setStyle("-fx-background-color: #D9534F; -fx-text-fill: white; -fx-font-size: 9px; -fx-min-width: 20px; -fx-max-height: 20px;");
            removeBtn.setOnAction(e -> {
                currentUser.removeSection(section);
                // update the grid/refresh it
                schedule.setUser(currentUser); 
                // update list
                refreshEnrolledList();    
                // update dropdown
                if (refreshDropdownsReference != null) {
                    refreshDropdownsReference.run(); 
                }
            });
            
            row.getChildren().addAll(info, removeBtn);
            enrolledListContainer.getChildren().add(row);
        }
    }
    
    private VBox buildSection(Runnable onListUpdate) {
    	VBox sectionContainer = new VBox();
    	sectionContainer.setSpacing(5);
    	sectionContainer.setAlignment(Pos.TOP_CENTER); 
    	
    	// ==Header==
    	HBox header = new HBox();
        header.setPadding(new Insets(3)); // Reduced padding
        header.setSpacing(5);
        header.setStyle("-fx-background-color: #2F4156; -fx-background-radius: 3;");
        
		Label colCourseCode = new Label("Course Code");
		Label colCourseTitle = new Label("Course Title");
		Label colUnits = new Label("Units");
		Label colSection = new Label("Section");
		Label colTime = new Label("Time");
		Label colAction = new Label("Action");
		
		List<Label> labels = Arrays.asList(colCourseCode, colCourseTitle, colUnits, colSection, colTime, colAction);

		for (Label lbl : labels) {
		    lbl.setTextFill(Color.WHITE);
		    lbl.setStyle("-fx-font-weight: bold; -fx-font-size: 11px;"); 
		}
		
		header.getChildren().addAll(
			makeColumn(colCourseCode, 150),
			makeColumn(colCourseTitle, 130),
			makeColumn(colUnits,  100),
			makeColumn(colSection, 100),
			makeColumn(colTime, 120),
			makeColumn(colAction, 150)
		);
		
		// ==Search Bar==
		HBox searchBar = new HBox();
		searchBar.setSpacing(10);
		searchBar.setAlignment(Pos.CENTER);

		TextField searchField = new TextField();
		searchField.setPromptText("Search specific course...");
		searchField.setPrefWidth(800);
		searchField.setPrefHeight(35); 
		searchField.setStyle("-fx-border-color: #2F4156; -fx-border-width: 1.5; -fx-border-radius: 2; -fx-font-size: 12px;");
		
		searchBar.getChildren().add(searchField);
		sectionContainer.getChildren().add(searchBar);
		
		header.setPrefWidth(700); 
		sectionContainer.getChildren().add(header);
		
		// ==Content Section==
		VBox dropdownContainer = new VBox();
	    dropdownContainer.setSpacing(5); 
	    
	    ArrayList<ObservableList<Course>> listOfLists = SceneManager.getDataAccess().getMasterList();
        ObservableList<Course> masterList = FXCollections.observableArrayList();
        for(ObservableList<Course> list: listOfLists) {
        	masterList.addAll(list);
        }
        
        // function to refresh dropdown reference
        this.refreshDropdownsReference = new Runnable() {
            @Override
            public void run() {
                dropdownContainer.getChildren().clear();
                String search = searchField.getText().toLowerCase().trim();
            
    		    for (Course course: masterList) {
    		    	if (!search.isEmpty()) {
    	                boolean match = course.getCourseCode().toLowerCase().contains(search) ||
    	                                course.getCourseName().toLowerCase().contains(search);
    	                if (!match) continue;
    	            }
    		    	
    		        TitledPane dropdown = new TitledPane();
    		        boolean autoExpand = !search.isEmpty();
    	
    		        HBox rowHeader = new HBox();
    		        rowHeader.setSpacing(40);
    		        rowHeader.setPadding(new Insets(2)); 
    	
    		        Label dropColCourseCode = new Label(course.getCourseCode());
    		        Label dropColCourseTitle = new Label(course.getCourseName());
    		        Label dropColUnits = new Label(course.getUnits()); 
    		        Label dropColSection = new Label("-");
    		        Label dropColTime = new Label("-");
    		        Label dropColAction = new Label("");
    	
    		        List<Label> dropLabels = Arrays.asList(dropColCourseCode, dropColCourseTitle, dropColUnits, dropColSection, dropColTime, dropColAction);
    		        for (Label lbl : dropLabels) {
    		            lbl.setTextFill(Color.BLACK);
    		            lbl.setStyle("-fx-font-size: 11px;"); 
    		        }
    	
    		        rowHeader.getChildren().addAll(
    		        		makeColumn(dropColCourseCode, 65, Pos.CENTER_LEFT),
    		        		makeColumn(dropColCourseTitle, 120),
    		        		makeColumn(dropColUnits, 50),
    		        		makeColumn(dropColSection, 50),
    		        		makeColumn(dropColTime, 90),
    		        		makeColumn(dropColAction, 100)
    		        );
    	
    		        dropdown.setGraphic(rowHeader);
    		        VBox tableContainer = new VBox();	        
    		        tableContainer.setSpacing(0);
    	
    		        for (Section section: course.getSection()) {
    		        	if(!section.getSectionCode().contains("-")) continue;
    		        	
    		            HBox row = new HBox();
    		            row.setSpacing(20);
    		            row.setPadding(new Insets(3)); 
    		            row.setStyle("-fx-border-color: black; -fx-border-width: 0.5; -fx-border-radius: 2;");
    		            
    		            Label contentCode = new Label(section.getCourseCode());
    		            Label contentName = new Label(section.getCourseName());
    		            Label contentUnit = new Label(course.getUnits());
    		            Label contentSection = new Label(section.getSectionCode());
    		            Label contentTime = new Label(section.getTime());
    		            

    		            List<Label> contentLabels = Arrays.asList(contentCode, contentName, contentUnit, contentSection, contentTime);
    		            for(Label l : contentLabels) l.setStyle("-fx-font-size: 10px;");
    		            
    		            Button actionButton = new Button();
    		            actionButton.setPrefWidth(70); 
    		            actionButton.setPrefHeight(20);
    		            actionButton.setStyle("-fx-font-size: 8px;"); 
    		            
    		            boolean isExactSectionPlanned = currentUser.getPlannedCourses().contains(section);
    		            boolean isCoursePlanned = currentUser.isCoursePlanned(course.getCourseCode());

    		            if (isExactSectionPlanned) {
    		                actionButton.setText("Remove");
    		                actionButton.setStyle("-fx-background-color: #D9534F; -fx-text-fill: white; -fx-font-size: 8px;");
    		                actionButton.setOnAction(e -> {
    		                    currentUser.removeSection(section);
    		                    schedule.setUser(currentUser);
    		                    this.run(); 
    		                    // refresh planned list
    		                    onListUpdate.run(); 
    		                });
    		            } else if (isCoursePlanned) {
    		                actionButton.setText("Unavailable");
    		                actionButton.setDisable(true);
    		            } else {
    		                actionButton.setText("Add");
    		                actionButton.setStyle("-fx-background-color: #2F4156; -fx-text-fill: white; -fx-font-size: 8px;");
    		                actionButton.setOnAction(e -> {
    		                    boolean success = currentUser.planSection(section);
    		                    if(success) {
    		                    	schedule.setUser(currentUser);
    		                        this.run(); 
    		                        // refresh planned list
    		                        onListUpdate.run(); 
    		                    }
    		                });
    		            }
    	
    		            row.getChildren().addAll(
    	            		makeColumn(contentCode, 80),
    	            		makeColumn(contentName, 132),
    	            		makeColumn(contentUnit, 37),
    		                makeColumn(contentSection, 60),
    		                makeColumn(contentTime, 90),
    		                makeColumn(actionButton, 100)
    		            );
    	
    		            tableContainer.getChildren().add(row);
    		        }
    		        
    		        if(course.getSection().isEmpty()) {
    		        	Label noSection = new Label("No sections available.");
    		            StackPane msgContainer = new StackPane(noSection);
    		        	noSection.setPadding(new Insets(5));
    		        	noSection.setStyle("-fx-font-size: 10px;");
    		        	
    		        	tableContainer.getChildren().add(msgContainer);
    		        }
    	        	
    		        dropdown.setContent(tableContainer);
    		        dropdown.setExpanded(autoExpand); 
    		        
    		        dropdownContainer.getChildren().add(dropdown);
    		    }
    		    
    		    if (dropdownContainer.getChildren().isEmpty()) {
    	            Label emptyMsg = new Label("No courses match your search.");
    	            emptyMsg.setPadding(new Insets(10));
    	            emptyMsg.setStyle("-fx-font-size: 10px;");
    	            
    	            dropdownContainer.getChildren().add(emptyMsg);
    	        }
            }
        };
	    
        refreshDropdownsReference.run();
        searchField.textProperty().addListener((obs, oldVal, newVal) -> refreshDropdownsReference.run());
        
	    ScrollPane scrollPane = new ScrollPane(dropdownContainer);
	    scrollPane.setFitToWidth(true);
	    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
	    scrollPane.setPrefHeight(200); 

	    sectionContainer.getChildren().add(scrollPane);
		
    	return sectionContainer;
    }
    
    private HBox makeColumn(javafx.scene.Node node, double width) {
        return makeColumn(node, width, Pos.CENTER);
    }
    
    private HBox makeColumn(javafx.scene.Node node, double width, Pos alignment) {
        HBox box = new HBox(node);
        box.setAlignment(alignment);
        box.setPrefWidth(width);
        return box;
    }
}