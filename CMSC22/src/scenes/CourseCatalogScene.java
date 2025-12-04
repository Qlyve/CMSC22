package scenes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.TableCell;
import uiandlogic.User;
import uiandlogic.Course;
import uiandlogic.Section;


public class CourseCatalogScene extends BaseScene {
    
	private User currentUser;
	   
    public CourseCatalogScene(User user) {
    	this.currentUser = user;
    }
    
    @Override
    protected Parent buildScene() {
    	// for debugging
//    	SceneManager.getDataAccess().viewCourses();
    	Section sec1 = SceneManager.getDataAccess().findSection("CMSC 123", "C");
    	Section sec2 = SceneManager.getDataAccess().findSection("CMSC 124", "ST");
    	Section sec3 = SceneManager.getDataAccess().findSection("CMSC 125", "B-5L");
    	
    	
    	// add sections to user 
    	if (sec1 != null) {
			currentUser.planSection(sec1);
		}
    	if (sec2 != null) {
			currentUser.planSection(sec2);
		}
    	if (sec3 != null) {
			currentUser.planSection(sec3);
		}
    	
    	currentUser.viewPlannedCourses();
    	
    	
    	
    	// get data
    	ArrayList<ObservableList<Course>> listOfLists = SceneManager.getDataAccess().getMasterList();
        ObservableList<Course> masterList = FXCollections.observableArrayList();
        for(ObservableList<Course> list: listOfLists) {
        	masterList.addAll(list);
        }
        
        // main container
    	HBox mainContainer = new HBox();
        // create and add the sidebar
        Sidebar sidebar = new Sidebar(scale, "CourseCatalog", currentUser);
        
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
        layout.setPadding(new Insets(20));
        
        // title container 
        HBox titleContainer = new HBox();
//        titleContainer.setStyle("-fx-border-color: #222222; -fx-border-width: 1px;");
        Label title = new Label("Course Catalog");
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: #222222; -fx-font-weight: bold;");
        HBox.setHgrow(titleContainer, Priority.ALWAYS);
        titleContainer.getChildren().addAll(title);
        
        
        Separator separator = new Separator();
        separator.setPrefWidth(Double.MAX_VALUE);
        separator.setStyle("-fx-background-color: #222222;");
        
        // create a filteredlist for searching later
        FilteredList<Course> filteredList = new FilteredList<>(masterList, p -> true);
        TextField searchField = new TextField();
        searchField.setPromptText("Search");
        searchField.setMaxWidth(302 * scale);


        // searching function
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredList.setPredicate(course -> {
                if (newVal == null || newVal.isEmpty()) return true;
                String lower = newVal.toLowerCase();

                return course.getCourseCode().toLowerCase().contains(lower)
                    || course.getCourseName().toLowerCase().contains(lower)
                    || course.getUnits().toLowerCase().contains(lower)
                    || course.getDescription().toLowerCase().contains(lower);
            });
        });

        // table
        TableView<Course> tableView = new TableView<>();
        tableView.setPrefHeight(879 * scale);
        tableView.setFixedCellSize(110 * scale);
        tableView.setItems(masterList);
        TableColumn<Course, String> codeCol = new TableColumn<>("Course Code");
        TableColumn<Course, String> nameCol = new TableColumn<>("Course Title");
        TableColumn<Course, String> unitsCol = new TableColumn<>("Units");
        TableColumn<Course, String> descCol = new TableColumn<>("Description");

        codeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        unitsCol.setCellValueFactory(new PropertyValueFactory<>("units"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        // for text wrapping in name col
        nameCol.setCellFactory(col -> new TableCell<Course, String>() {
            private final Label label = new Label();

            {
                label.setWrapText(true);      
                label.setMaxWidth(150);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    label.setText(item);
                    setGraphic(label);
                }
            }
        });
        
        // for text wrapping in desc col
        descCol.setCellFactory(col -> new TableCell<Course, String>() {
            private final Label label = new Label();
            {
                label.setWrapText(true);  
                label.setStyle("-fx-font-size: 11px;");
                label.setMaxWidth(650);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    label.setText(item);
                    setGraphic(label);
                }
            }
        });
        
        tableView.getColumns().addAll(codeCol, nameCol, unitsCol, descCol);

        SortedList<Course> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);	
        
        // add the nodes to respective roots to create the layout
        layout.getChildren().addAll(titleContainer, separator,  searchField, tableView);
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