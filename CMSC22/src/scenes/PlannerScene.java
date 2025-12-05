package scenes;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import uiandlogic.Section;
import uiandlogic.Schedule;
import uiandlogic.SectionPlanner;
import uiandlogic.User;

public class PlannerScene extends BaseScene {
    
    private User currentUser;
    private Schedule schedule;
    private SectionPlanner sectionPlanner;
    
    // for refreshing schedule when updating
    private VBox enrolledListContainer;
    
    
    // constructor
    public PlannerScene(User user) {
        this.currentUser = user;
        this.schedule = new Schedule(user, scale);
        this.enrolledListContainer = new VBox(5);
    }
    
    // ui
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
        schedContainer.setPadding(new Insets(0, -10, 0, 15));
        schedContainer.setPrefHeight(500 * scale);
        VBox.setVgrow(schedContainer, Priority.ALWAYS);
        
        scheduleMainContainer.getChildren().addAll(weeklySchedTitleContainer, schedContainer);
        
        // enrolled/planned container
        VBox enrolledArea = new VBox(10);
        enrolledArea.setPrefWidth(450 * scale);
        HBox.setHgrow(enrolledArea, Priority.SOMETIMES);
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
        
        // create section planner
        sectionPlanner = new SectionPlanner(currentUser, schedule, scale, updateEnrolledList);
        
        layout.getChildren().addAll(titleContainer, schedAndEnrolledContainer, sectionPlanner.getContainer());
        
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
        
        // track which sections have been displayed to avoid duplicates
        List<Section> displayedSections = new ArrayList<>();
        
        for (Section section : currentUser.getPlannedCourses()) {
            // skip if already displayed as part of a pair
            if (displayedSections.contains(section)) continue;
            
            // check if this section has a paired lab section
            List<Section> pairedLabs = new ArrayList<>();
            Section pairedLecture = null;
            String sectionCode = section.getSectionCode();
            
            // if section is a lecture (no dash, no "L" suffix), find all paired labs
            if (!sectionCode.contains("-") && !sectionCode.endsWith("L")) {
                // this is a lecture, look for its labs
                for (Section potentialLab : currentUser.getPlannedCourses()) {
                    String labCode = potentialLab.getSectionCode();
                    if (potentialLab.getCourseCode().equals(section.getCourseCode()) &&
                        labCode.startsWith(sectionCode + "-") && labCode.endsWith("L")) {
                        pairedLabs.add(potentialLab);
                    }
                }
            } else if (sectionCode.contains("-") && sectionCode.endsWith("L")) {
                // this is a lab, check if its lecture is also planned
                String lectureCode = sectionCode.substring(0, sectionCode.indexOf("-"));
                for (Section potentialLecture : currentUser.getPlannedCourses()) {
                    if (potentialLecture.getCourseCode().equals(section.getCourseCode()) &&
                        potentialLecture.getSectionCode().equals(lectureCode)) {
                        pairedLecture = potentialLecture;
                        // skip this lab as it will be shown with lecture
                        displayedSections.add(section);
                        break;
                    }
                }
                if (displayedSections.contains(section)) continue;
            }
            
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 3; -fx-padding: 5;");
            
            VBox info = new VBox(2);
            Label code = new Label(section.getCourseCode());
            code.setStyle("-fx-font-weight: bold; -fx-font-size: 8px;");
            
            // show combined section info if there are paired labs
            String sectionDisplay = section.getSectionCode();
            if (!pairedLabs.isEmpty()) {
                for (Section lab : pairedLabs) {
                    sectionDisplay += " + " + lab.getSectionCode();
                }
            }
            Label secInfo = new Label("Section: " + sectionDisplay);
            secInfo.setStyle("-fx-font-size: 10px; -fx-text-fill: #555;");
            
            // show day and time info including lab if exists
            String dayDisplay = section.getDay() != null ? section.getDay() : "-";
            String timeDisplay = section.getTime() != null ? section.getTime() : "-";
            if (!pairedLabs.isEmpty()) {
                for (Section lab : pairedLabs) {
                    if (lab.getDay() != null) dayDisplay += " / " + lab.getDay();
                    if (lab.getTime() != null) timeDisplay += " / " + lab.getTime();
                }
            }
            Label dayInfo = new Label("Day: " + dayDisplay);
            dayInfo.setStyle("-fx-font-size: 9px; -fx-text-fill: #666;");
            Label timeInfo = new Label("Time: " + timeDisplay);
            timeInfo.setStyle("-fx-font-size: 9px; -fx-text-fill: #666;");
            
            info.getChildren().addAll(code, secInfo, dayInfo, timeInfo);
            HBox.setHgrow(info, Priority.ALWAYS);
            
            Button removeBtn = new Button("x");
            removeBtn.setStyle("-fx-background-color: #D9534F; -fx-text-fill: white; -fx-font-size: 9px; -fx-min-width: 20px; -fx-max-height: 20px;");
            
            // store final reference to paired lecture for lambda
            final Section finalPairedLecture = pairedLecture;
            
            removeBtn.setOnAction(e -> {
                // remove the section 
                currentUser.removeSection(section);
                
                // if lect, remove all paired labs
                for (Section lab : pairedLabs) {
                    currentUser.removeSection(lab);
                }
                
                if (finalPairedLecture != null) {
                    currentUser.removeSection(finalPairedLecture);
                    
                    // remove any other labs paired with that lecture if there is
                    List<Section> siblingLabs = new ArrayList<>();
                    for (Section plannedSec : currentUser.getPlannedCourses()) {
                        if (plannedSec.getCourseCode().equals(finalPairedLecture.getCourseCode()) &&
                            plannedSec.getSectionCode().contains("-") &&
                            plannedSec.getSectionCode().startsWith(finalPairedLecture.getSectionCode() + "-") &&
                            !plannedSec.equals(section)) {
                            siblingLabs.add(plannedSec);
                        }
                    }
                    for (Section siblingLab : siblingLabs) {
                        currentUser.removeSection(siblingLab);
                    }
                }
                
                // update the grid/refresh it
                schedule.setUser(currentUser);
                // update list
                refreshEnrolledList();
                // update dropdown
                if (sectionPlanner != null) {
                    sectionPlanner.refreshDropdowns();
                }
            });
            
            row.getChildren().addAll(info, removeBtn);
            enrolledListContainer.getChildren().add(row);
            
            // mark all sections as displayed
            displayedSections.add(section);
            displayedSections.addAll(pairedLabs);
        }
    }
}