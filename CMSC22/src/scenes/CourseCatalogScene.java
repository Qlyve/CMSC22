package scenes;

import java.util.ArrayList;

import application.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableCell;
import uiandlogic.User;
import uiandlogic.Course;


public class CourseCatalogScene extends BaseScene {
    
	private User currentUser;
	   
    public CourseCatalogScene(User user) {
    	this.currentUser = user;
    }
    
    @Override
    protected Parent buildScene() {
    	
    	
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
        Label title = new Label("Course Catalog");
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: #222222; -fx-font-weight: bold;");
        HBox.setHgrow(titleContainer, Priority.ALWAYS);
        titleContainer.getChildren().addAll(title);
        
        // line design
        Separator separator = new Separator();
        separator.setPrefWidth(Double.MAX_VALUE);
        separator.setStyle("-fx-background-color: #222222;");
        
        // create a filtered list for searching later
        FilteredList<Course> filteredList = new FilteredList<>(masterList, p -> true);
        TextField searchField = new TextField();
        searchField.setPromptText("Search");
        searchField.setMaxWidth(302 * scale);
        
        ComboBox<String> degreeFilter = new ComboBox<>();
        degreeFilter.getItems().add("All");
        for(Course course : masterList) {
            String degree = course.getTypeDegree();
            if(!degreeFilter.getItems().contains(degree)) {
                degreeFilter.getItems().add(degree);
            }
        }
        degreeFilter.setValue("All");
        degreeFilter.setPrefWidth(200 * scale);
        
        // put search and dropdown together
        HBox filterContainer = new HBox(10);
        filterContainer.getChildren().addAll(searchField, degreeFilter);


        // searching function
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredList.setPredicate(course -> {
                // check dropdown filter
                String selectedDegree = degreeFilter.getValue();
                if(selectedDegree != null && !selectedDegree.equals("All")) {
                    if(!course.getTypeDegree().equals(selectedDegree)) {
                        return false;
                    }
                }
                
                // Check search text
                if (newVal == null || newVal.isEmpty()) return true;
                String lower = newVal.toLowerCase();

                return course.getCourseCode().toLowerCase().contains(lower)
                    || course.getCourseName().toLowerCase().contains(lower);
            });
        });
        
        // for drop down
        degreeFilter.valueProperty().addListener((obs, oldVal, newVal) -> {
            filteredList.setPredicate(course -> {
                // check dropdown filter
                if(newVal != null && !newVal.equals("All")) {
                    if(!course.getTypeDegree().equals(newVal)) {
                        return false;
                    }
                }
                
                // Check search text
                String searchText = searchField.getText();
                if (searchText == null || searchText.isEmpty()) return true;
                String lower = searchText.toLowerCase();

                return course.getCourseCode().toLowerCase().contains(lower)
                    || course.getCourseName().toLowerCase().contains(lower);
            });
        });
        

        // table
        TableView<Course> tableView = new TableView<>();
        tableView.setPrefHeight(879 * scale);
        tableView.setFixedCellSize(110 * scale);
        tableView.setItems(masterList);
        TableColumn<Course, String> degCol = new TableColumn<>("Type/Degree");
        TableColumn<Course, String> codeCol = new TableColumn<>("Course Code");
        TableColumn<Course, String> nameCol = new TableColumn<>("Course Title");
        TableColumn<Course, String> unitsCol = new TableColumn<>("Units");
        TableColumn<Course, String> descCol = new TableColumn<>("Description");

        degCol.setCellValueFactory(new PropertyValueFactory<>("typeDegree"));
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
        
        // for text wrapping in description col
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
        
        tableView.getColumns().addAll(degCol, codeCol, nameCol, unitsCol, descCol);

        SortedList<Course> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);	
        
        // add the nodes to create the layout
        layout.getChildren().addAll(titleContainer, separator,  filterContainer, tableView);
        contentArea.getChildren().add(layout);
        backgroundArea.getChildren().addAll(contentArea);
        mainContainer.getChildren().addAll(sidebar.getSidebar(), backgroundArea);
        
        return mainContainer;
    }
    
    // METHODS AND FUNCTIONS
}