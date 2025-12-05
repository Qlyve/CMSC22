package uiandlogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SectionPlanner {
    
    private User currentUser;
    private Schedule schedule;
    private double scale;
    private Runnable onListUpdate;
    
    private VBox mainContainer;
    private VBox dropdownContainer;
    private TextField searchField;
    private FilteredList<Course> filteredList;
    private Runnable refreshDropdownsReference;
    
    public SectionPlanner(User user, Schedule schedule, double scale, Runnable onListUpdate) {
        this.currentUser = user;
        this.schedule = schedule;
        this.scale = scale;
        this.onListUpdate = onListUpdate;
        
        buildSectionPlanner();
    }
    
    // build the main section planner UI
    private void buildSectionPlanner() {
        mainContainer = new VBox();
        mainContainer.setSpacing(5);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        
        // ==Header==
        HBox header = buildHeader();
        
        // ==Search Bar==
        HBox searchBar = buildSearchBar();
        
        // ==Content Section==
        dropdownContainer = new VBox();
        dropdownContainer.setSpacing(5);
        
        // filter the masterlist as you can only add courses based on your current degree/type
        setupFilteredList();
        
        // setup search listener
        setupSearchListener();
        
        // add to main container
        mainContainer.getChildren().addAll(searchBar, header);
        
        ScrollPane scrollPane = new ScrollPane(dropdownContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefHeight(200);
        mainContainer.getChildren().add(scrollPane);
        
        // initial populate
        refreshDropdowns();
    }
    
    // build header row
    private HBox buildHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(3));
        header.setSpacing(5);
        header.setStyle("-fx-background-color: #2F4156; -fx-background-radius: 3;");
        header.setAlignment(Pos.CENTER);
        
        Label colCourseCode = new Label("Course Code");
        Label colCourseTitle = new Label("Course Title");
        Label colUnits = new Label("Units");
        Label colSection = new Label("Section");
        Label colDay = new Label("Day");
        Label colTime = new Label("Time");
        Label colAction = new Label("Action");
        
        List<Label> labels = Arrays.asList(colCourseCode, colCourseTitle, colUnits, colSection, colDay, colTime, colAction);
        
        for (Label lbl : labels) {
            lbl.setTextFill(Color.WHITE);
            lbl.setStyle("-fx-font-weight: bold; -fx-font-size: 11px;");
        }
        
        header.getChildren().addAll(
            makeColumn(colCourseCode, 120),
            makeColumn(colCourseTitle, 150),
            makeColumn(colUnits, 60),
            makeColumn(colSection, 80),
            makeColumn(colDay, 80),
            makeColumn(colTime, 100),
            makeColumn(colAction, 100)
        );
        
        header.setPrefWidth(700);
        return header;
    }
    
    // build search bar
    private HBox buildSearchBar() {
        HBox searchBar = new HBox();
        searchBar.setSpacing(10);
        searchBar.setAlignment(Pos.CENTER);
        
        searchField = new TextField();
        searchField.setPromptText("Search specific course...");
        searchField.setPrefWidth(500);
        searchField.setPrefHeight(35);
        searchField.setStyle("-fx-border-color: #2F4156; -fx-border-width: 1.5; -fx-border-radius: 2; -fx-font-size: 12px;");
        
        searchBar.getChildren().add(searchField);
        return searchBar;
    }
    
    // filter the masterlist as you can only add courses based on your current degree/type
    private void setupFilteredList() {
        ArrayList<ObservableList<Course>> listOfLists = SceneManager.getDataAccess().getMasterList();
        ObservableList<Course> masterList = FXCollections.observableArrayList();
        
        // check here
        String userDegree = currentUser.getUserType();
        
        for (ObservableList<Course> list : listOfLists) {
            for (Course course : list) {
                // filter courses by user dergree/type
                if (course.getTypeDegree().equals(userDegree)) {
                    masterList.add(course);
                }
            }
        }
        
        // create filtered list instead
        filteredList = new FilteredList<>(masterList, p -> true);
    }
    
    // search filter
    private void setupSearchListener() {
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredList.setPredicate(course -> {
                if (newVal == null || newVal.isEmpty()) return true;
                
                // only filter from coursecode or name
                String lower = newVal.toLowerCase().trim();
                return course.getCourseCode().toLowerCase().contains(lower) ||
                       course.getCourseName().toLowerCase().contains(lower);
            });
            // refresh dropdown display
            refreshDropdowns();
        });
    }
    
    // refresh dropdown display
    public void refreshDropdowns() {
        dropdownContainer.getChildren().clear();
        boolean autoExpand = !searchField.getText().isEmpty();
        
        for (Course course : filteredList) {
            TitledPane dropdown = new TitledPane();
            
            HBox rowHeader = new HBox();
            rowHeader.setSpacing(5);
            rowHeader.setPadding(new Insets(2));
            rowHeader.setAlignment(Pos.CENTER);
            
            Label dropColCourseCode = new Label(course.getCourseCode());
            Label dropColCourseTitle = new Label(course.getCourseName());
            Label dropColUnits = new Label(course.getUnits());
            Label dropColSection = new Label("-");
            Label dropColDay = new Label("-");
            Label dropColTime = new Label("-");
            Label dropColAction = new Label("");
            
            List<Label> dropLabels = Arrays.asList(dropColCourseCode, dropColCourseTitle, dropColUnits,
                                                    dropColSection, dropColDay, dropColTime, dropColAction);
            for (Label lbl : dropLabels) {
                lbl.setTextFill(Color.BLACK);
                lbl.setStyle("-fx-font-size: 11px;");
            }
            
            rowHeader.getChildren().addAll(
                makeColumn(dropColCourseCode, 120, Pos.CENTER),
                makeColumn(dropColCourseTitle, 150, Pos.CENTER),
                makeColumn(dropColUnits, 60, Pos.CENTER),
                makeColumn(dropColSection, 80, Pos.CENTER),
                makeColumn(dropColDay, 80, Pos.CENTER),
                makeColumn(dropColTime, 100, Pos.CENTER),
                makeColumn(dropColAction, 100, Pos.CENTER)
            );
            
            dropdown.setGraphic(rowHeader);
            VBox tableContainer = new VBox();
            tableContainer.setSpacing(0);
            
            // create a list to track processed sections
            List<Section> processedSections = new ArrayList<>();
            
            // loop through sections for this course
            for (Section section : course.getSection()) {
                // skip sections with TBA in time or day
                if (section.getTime() != null && section.getTime().toUpperCase().contains("TBA")) continue;
                if (section.getDay() != null && section.getDay().toUpperCase().contains("TBA")) continue;
                
                // skip if already processed
                if (processedSections.contains(section)) continue;
                
                // check if there is a paired lab
                String sectionCode = section.getSectionCode();
                List<Section> pairedLabs = new ArrayList<>();
                
                // check if section has no dash and an L meaning its a lecture section
                if (!sectionCode.contains("-") && !sectionCode.endsWith("L")) {
                    // look for labs
                    for (Section potentialLab : course.getSection()) {
                        String labCode = potentialLab.getSectionCode();
                        // find labs with same lecture code with - and ends with L
                        if (labCode.startsWith(sectionCode + "-") && labCode.endsWith("L")) {
                            // skip TBA sections for lab too
                            if (potentialLab.getTime() != null && potentialLab.getTime().toUpperCase().contains("TBA")) continue;
                            if (potentialLab.getDay() != null && potentialLab.getDay().toUpperCase().contains("TBA")) continue;
                            pairedLabs.add(potentialLab);
                        }
                    }
                } else if (sectionCode.contains("-") && sectionCode.endsWith("L")) {
                    // this is a lab section check if lecture is already processed
                    String lectureCode = sectionCode.substring(0, sectionCode.indexOf("-"));
                    boolean lectureProcessed = false;
                    for (Section processed : processedSections) {
                        if (processed.getSectionCode().equals(lectureCode)) {
                            lectureProcessed = true;
                            break;
                        }
                    }
                    if (lectureProcessed) continue; // skip this lab as it was already paired
                } else if (sectionCode.endsWith("L") && !sectionCode.contains("-")) {
                    // standalone lab if no dash but ends with L
                }
                
                // create the combined row lab + section details
                HBox combinedRow = createCombinedSectionRow(section, pairedLabs, course);
                tableContainer.getChildren().add(combinedRow);
                
                processedSections.add(section);
                processedSections.addAll(pairedLabs);
            }
            
            // if no valid sections after filtering
            if (tableContainer.getChildren().isEmpty()) {
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
        
        // for not showing anything
        if (dropdownContainer.getChildren().isEmpty()) {
            Label emptyMsg = new Label("No courses match your search.");
            emptyMsg.setPadding(new Insets(10));
            emptyMsg.setStyle("-fx-font-size: 10px;");
            
            dropdownContainer.getChildren().add(emptyMsg);
        }
    }
    
    // create the combine section row
    private HBox createCombinedSectionRow(Section lectureSection, List<Section> availableLabs, Course course) {
        HBox row = new HBox();
        row.setSpacing(5);
        row.setPadding(new Insets(3));
        row.setStyle("-fx-border-color: black; -fx-border-width: 0.5; -fx-border-radius: 2;");
        row.setAlignment(Pos.CENTER);
        
        Label contentCode = new Label(lectureSection.getCourseCode());
        Label contentName = new Label(lectureSection.getCourseName());
        Label contentUnit = new Label(course.getUnits());
        
        // show info
        HBox sectionDisplay = new HBox(3);
        sectionDisplay.setAlignment(Pos.CENTER);
        
        Label lectureSectionLabel = new Label(lectureSection.getSectionCode());
        lectureSectionLabel.setStyle("-fx-font-size: 10px;");
        sectionDisplay.getChildren().add(lectureSectionLabel);
        
        // lab selector
        final ComboBox<Section> labSelector;
        if (!availableLabs.isEmpty()) {
            // dropdown for section selection
            labSelector = new ComboBox<>();
            labSelector.getItems().addAll(availableLabs);
            labSelector.setPromptText("Select Lab");
            labSelector.setPrefWidth(80);
            labSelector.setStyle("-fx-font-size: 9px;");
            
            // show only section code
            labSelector.setButtonCell(new javafx.scene.control.ListCell<Section>() {
                @Override
                protected void updateItem(Section item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("Select Lab");
                    } else {
                        setText(item.getSectionCode());
                    }
                }
            });
            labSelector.setCellFactory(lv -> new javafx.scene.control.ListCell<Section>() {
                @Override
                protected void updateItem(Section item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getSectionCode());
                }
            });
            
            Label plusLabel = new Label(" + ");
            plusLabel.setStyle("-fx-font-size: 10px;");
            sectionDisplay.getChildren().addAll(plusLabel, labSelector);
        } else {
            // no labs available if null
            labSelector = null;
        }
        
        // combine day and time info of labs + lec
        String lectureDayText = lectureSection.getDay() != null ? lectureSection.getDay() : "-";
        String lectureTimeText = lectureSection.getTime() != null ? lectureSection.getTime() : "-";
        
        Label contentDay = new Label(lectureDayText);
        Label contentTime = new Label(lectureTimeText);
        contentDay.setStyle("-fx-font-size: 10px;");
        contentTime.setStyle("-fx-font-size: 10px;");
        
        List<Label> contentLabels = Arrays.asList(contentCode, contentName, contentUnit);
        for (Label l : contentLabels) l.setStyle("-fx-font-size: 10px;");
        
        Button actionButton = new Button();
        actionButton.setPrefWidth(70);
        actionButton.setPrefHeight(20);
        
        // update display
        if (labSelector != null) {
            labSelector.setOnAction(e -> {
                Section selectedLab = labSelector.getValue();
                if (selectedLab != null) {
                    String combinedDay = lectureDayText;
                    String combinedTime = lectureTimeText;
                    
                    if (selectedLab.getDay() != null) {
                        combinedDay += " / " + selectedLab.getDay();
                    }
                    if (selectedLab.getTime() != null) {
                        combinedTime += " / " + selectedLab.getTime();
                    }
                    
                    contentDay.setText(combinedDay);
                    contentTime.setText(combinedTime);
                } else {
                    // show only lect info no lab selected
                    contentDay.setText(lectureDayText);
                    contentTime.setText(lectureTimeText);
                }
            });
        }
        
        // checkers
        boolean isLecturePlanned = currentUser.getPlannedCourses().contains(lectureSection);
        boolean isCoursePlanned = currentUser.isCoursePlanned(course.getCourseCode());
        boolean isLectureConflict = schedule.checkConflict(lectureSection, currentUser.getPlannedCourses());
        
        if (isLecturePlanned) {
            // lec planned show remove
            actionButton.setText("Remove");
            actionButton.setStyle("-fx-background-color: #D9534F; -fx-text-fill: white; -fx-font-size: 8px;");
            
            // update info
            List<Section> plannedLabs = new ArrayList<>();
            for (Section plannedSec : currentUser.getPlannedCourses()) {
                if (plannedSec.getCourseCode().equals(lectureSection.getCourseCode()) &&
                    plannedSec.getSectionCode().contains("-") &&
                    plannedSec.getSectionCode().startsWith(lectureSection.getSectionCode() + "-")) {
                    plannedLabs.add(plannedSec);
                }
            }
            
            if (!plannedLabs.isEmpty()) {
                String combinedDay = lectureDayText;
                String combinedTime = lectureTimeText;
                for (Section lab : plannedLabs) {
                    if (lab.getDay() != null) combinedDay += " / " + lab.getDay();
                    if (lab.getTime() != null) combinedTime += " / " + lab.getTime();
                }
                contentDay.setText(combinedDay);
                contentTime.setText(combinedTime);
            }
            
            actionButton.setOnAction(e -> {
                // remove lect and all lab
                currentUser.removeSection(lectureSection);
                
                List<Section> labsToRemove = new ArrayList<>();
                for (Section plannedSec : currentUser.getPlannedCourses()) {
                    if (plannedSec.getCourseCode().equals(lectureSection.getCourseCode()) &&
                        plannedSec.getSectionCode().contains("-") &&
                        plannedSec.getSectionCode().startsWith(lectureSection.getSectionCode() + "-")) {
                        labsToRemove.add(plannedSec);
                    }
                }
                for (Section lab : labsToRemove) {
                    currentUser.removeSection(lab);
                }
                
                // update schedule list and plannedlist
                schedule.setUser(currentUser);
                refreshDropdowns();
                onListUpdate.run();
            });
            
            // disable lab selector if there is already planned
            if (labSelector != null) labSelector.setDisable(true);
            
        } else if (isCoursePlanned) {
            // if the course is planned with different section, cannot add another
            actionButton.setText("Unavailable");
            actionButton.setStyle("-fx-background-color: #808080; -fx-text-fill: white; -fx-font-size: 8px;");
            actionButton.setDisable(true);
            if (labSelector != null) labSelector.setDisable(true);
            
        } else if (isLectureConflict) {
            // lect has time conflict
            actionButton.setText("Conflict");
            actionButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-size: 8px;");
            actionButton.setDisable(true);
            if (labSelector != null) labSelector.setDisable(true);
            
        } else {
            // course not planned prepare to add
            actionButton.setText("Add");
            actionButton.setStyle("-fx-background-color: #2F4156; -fx-text-fill: white; -fx-font-size: 8px;");
            actionButton.setOnAction(e -> {
                // if labs exist must select one
                if (labSelector != null && labSelector.getValue() == null) {
                    // show error if tried to add without selecting lab
                    actionButton.setText("Select Lab!");
                    actionButton.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white; -fx-font-size: 8px;");
                    
                    javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(2));
                    pause.setOnFinished(ev -> {
                        actionButton.setText("Add");
                        actionButton.setStyle("-fx-background-color: #2F4156; -fx-text-fill: white; -fx-font-size: 8px;");
                    });
                    pause.play();
                    return;
                }
                
                Section selectedLab = labSelector != null ? labSelector.getValue() : null;
                
                // check conflicts
                boolean lectureConflicts = schedule.checkConflict(lectureSection, currentUser.getPlannedCourses());
                boolean labConflicts = selectedLab != null && schedule.checkConflict(selectedLab, currentUser.getPlannedCourses());
                
                if (lectureConflicts || labConflicts) {
                    // show error conflict
                    actionButton.setText("Conflict!");
                    actionButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-size: 8px;");
                    
                    javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(2));
                    pause.setOnFinished(ev -> {
                        actionButton.setText("Add");
                        actionButton.setStyle("-fx-background-color: #2F4156; -fx-text-fill: white; -fx-font-size: 8px;");
                    });
                    pause.play();
                    return;
                }
                
                // add lecture
                currentUser.getPlannedCourses().add(lectureSection);
                
                // add selected lab
                if (selectedLab != null) {
                    currentUser.getPlannedCourses().add(selectedLab);
                }
                
                // update schedule list and plannedlist
                schedule.setUser(currentUser);
                refreshDropdowns();
                onListUpdate.run();
            });
        }
        
        row.getChildren().addAll(
            makeColumn(contentCode, 120, Pos.CENTER),
            makeColumn(contentName, 150, Pos.CENTER),
            makeColumn(contentUnit, 60, Pos.CENTER),
            makeColumn(sectionDisplay, 80, Pos.CENTER),
            makeColumn(contentDay, 80, Pos.CENTER),
            makeColumn(contentTime, 100, Pos.CENTER),
            makeColumn(actionButton, 100, Pos.CENTER)
        );
        
        return row;
    }
    
    // helper method to create columns
    private HBox makeColumn(javafx.scene.Node node, double width) {
        return makeColumn(node, width, Pos.CENTER);
    }
    
    private HBox makeColumn(javafx.scene.Node node, double width, Pos alignment) {
        HBox box = new HBox(node);
        box.setAlignment(alignment);
        box.setPrefWidth(width);
        return box;
    }
    
    // getter for main container
    public VBox getContainer() {
        return mainContainer;
    }
}