package scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import uiandlogic.User;

public class AboutUsScene extends BaseScene {

    private User currentUser;

    public AboutUsScene(User user) {
        this.currentUser = user;
    }

    @Override
    protected Parent buildScene() {
        HBox mainContainer = new HBox();
        mainContainer.setStyle("-fx-background-color: #E5E5E5;");

        // create and add the sidebar
        Sidebar sidebar = new Sidebar(scale, "AboutUs", currentUser);

        // create content area
        VBox contentArea = new VBox(30 * scale);
        contentArea.setPadding(new Insets(40 * scale));
        contentArea.setAlignment(Pos.TOP_CENTER);
        HBox.setHgrow(contentArea, Priority.ALWAYS);

        // header section
        VBox headerSection = new VBox(10 * scale);
        headerSection.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("About Us");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #2F4156;");
        
        Label subtitleLabel = new Label("Meet the Team");
        subtitleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #666666;");
        
        headerSection.getChildren().addAll(titleLabel, subtitleLabel);

        HBox teamContainer = new HBox(25 * scale);
        teamContainer.setAlignment(Pos.CENTER);
        teamContainer.setPadding(new Insets(20 * scale, 0, 20 * scale, 0));
        
        VBox member1 = createMemberCard(
            "Samuel P. Silva",
            "BS Computer Science",
            "2024-09815",
            "spsilva@up.edu.ph",
            "Led the UI/UX design initiatives and architected comprehensive Figma layouts that defined the application's visual identity. Spearheaded the implementation of backend authentication systems and engineered robust login functionalities with secure credential validation."
        );

        VBox member2 = createMemberCard(
            "Lardner M. Lanao",
            "BS Computer Science",
            "2024-00898",
            "lmlanao@up.edu.ph",
            "Developed and integrated critical button functionalities across multiple interfaces, ensuring seamless user interactions. Designed and implemented the main home screen architecture, incorporating dynamic content rendering and user-specific data display mechanisms."
        );

        VBox member3 = createMemberCard(
            "Andrew Qlyve Paul C. Bundoc",
            "BS Computer Science",
            "2024-06867",
            "acbundoc@up.edu.ph",
            "Architected the entire planner home scene with sophisticated scheduling logic and intuitive course management features. Engineered the foundational scene-switching framework and routing system that enabled seamless navigation throughout the application, establishing core methodologies for the team."
        );

        VBox member4 = createMemberCard(
            "Nino Ferjen C. Ilocto",
            "BS Statistics",
            "2020-05213",
            "ncilocto@up.edu.ph",
            "Served as project lead, coordinating team meetings and driving development timelines to ensure project milestones were achieved. Designed and implemented the complete authentication interface, crafting the sign-in and sign-up screens with professional styling and user-friendly form validation."
        );

        teamContainer.getChildren().addAll(member1, member2, member3, member4);

        VBox projectInfo = createProjectInfoBox();
        
        VBox projectWrapper = new VBox(projectInfo);
        projectWrapper.setAlignment(Pos.CENTER);

        contentArea.getChildren().addAll(headerSection, teamContainer, projectWrapper);
        mainContainer.getChildren().addAll(sidebar.getSidebar(), contentArea);

        return mainContainer;
    }

    private VBox createMemberCard(String name, String course, String studentNumber, String email, String contribution) {
        VBox card = new VBox(12 * scale);
        card.setPadding(new Insets(25 * scale));
        card.setAlignment(Pos.TOP_LEFT);
        card.setPrefWidth(300 * scale);
        card.setPrefHeight(520 * scale); 
        card.setStyle("-fx-background-color: white; " +
                     "-fx-background-radius: 10; " +
                     "-fx-border-color: #CCCCCC; " +
                     "-fx-border-width: 1; " +
                     "-fx-border-radius: 10;");

        // drop shadow effect
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(0);
        shadow.setOffsetY(4);
        shadow.setColor(Color.rgb(0, 0, 0, 0.15));
        card.setEffect(shadow);

        // name
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2F4156;");
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(Double.MAX_VALUE);
        
        // course
        Label courseLabel = new Label(course);
        courseLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555555;");
        courseLabel.setWrapText(true);
        courseLabel.setMaxWidth(Double.MAX_VALUE);

        // student number
        Label studentNumLabel = new Label("Student No: " + studentNumber);
        studentNumLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #666666;");
        studentNumLabel.setWrapText(true);
        studentNumLabel.setMaxWidth(Double.MAX_VALUE);

        // email
        Label emailLabel = new Label("📧 " + email);
        emailLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #3A5169;");
        emailLabel.setWrapText(true);
        emailLabel.setMaxWidth(Double.MAX_VALUE);

        VBox contributionBox = new VBox(5 * scale);
        contributionBox.setPadding(new Insets(15 * scale, 0, 0, 0));
        VBox.setVgrow(contributionBox, Priority.ALWAYS); 
        
        Label contributionTitle = new Label("Key Contributions:");
        contributionTitle.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #2F4156;");
        
        Label contributionText = new Label(contribution);
        contributionText.setStyle("-fx-font-size: 10px; -fx-text-fill: #555555; -fx-line-spacing: 2px;");
        contributionText.setWrapText(true);
        contributionText.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(contributionText, Priority.ALWAYS); 
        
        contributionBox.getChildren().addAll(contributionTitle, contributionText);

        card.getChildren().addAll(nameLabel, courseLabel, studentNumLabel, emailLabel, contributionBox);

        return card;
    }

    private VBox createProjectInfoBox() {
        VBox infoBox = new VBox(15 * scale);
        infoBox.setPadding(new Insets(30 * scale));
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setPrefWidth(1300 * scale);
        infoBox.setStyle("-fx-background-color: #2F4156; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-radius: 10;");

        // drop shadow
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(0);
        shadow.setOffsetY(4);
        shadow.setColor(Color.rgb(0, 0, 0, 0.2));
        infoBox.setEffect(shadow);

        Label projectTitle = new Label("About This Project");
        projectTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label projectDesc = new Label(
            "This Course Scheduler and Planner was created by CMSC 22 CD-5L students\n" +
            "as part of their laboratory requirements. The system helps students plan and\n" +
            "manage their course schedules efficiently."
        );
        projectDesc.setStyle("-fx-font-size: 16px; -fx-text-fill: #E5E5E5; -fx-text-alignment: center;");
        projectDesc.setWrapText(true);
        projectDesc.setAlignment(Pos.CENTER); 
        projectDesc.setMaxWidth(1200 * scale);

        Label courseLabel = new Label("CMSC 22 - Object-Oriented Programming");
        courseLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #CCCCCC; -fx-font-style: italic;");
        courseLabel.setAlignment(Pos.CENTER);

        infoBox.getChildren().addAll(projectTitle, projectDesc, courseLabel);

        return infoBox;
    }
}


