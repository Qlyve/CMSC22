package scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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

        Sidebar sidebar = new Sidebar(scale, "AboutUs", currentUser);

        VBox contentArea = new VBox(25 * scale);
        contentArea.setPadding(new Insets(25 * scale, 20 * scale, 40 * scale, 20 * scale));
        contentArea.setAlignment(Pos.CENTER);
        HBox.setHgrow(contentArea, Priority.ALWAYS);

        VBox headerSection = new VBox(5 * scale);
        headerSection.setAlignment(Pos.CENTER);
        headerSection.setPadding(new Insets(0, 0, 15 * scale, 0));

        Label titleLabel = new Label("About Us");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2F4156;");

        Label subtitleLabel = new Label("Meet the Team");
        subtitleLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666666;");

        headerSection.getChildren().addAll(titleLabel, subtitleLabel);

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
                "Coordinated team meetings and driving development timelines to ensure project milestones were achieved. Designed and implemented the complete authentication interface, crafting the sign-in and sign-up screens with professional styling and user-friendly form validation."
        );

        VBox teamCardsWrapper = new VBox(20 * scale);
        teamCardsWrapper.setAlignment(Pos.TOP_CENTER); 
        teamCardsWrapper.setPadding(new Insets(0, 0, 20 * scale, 0));
        
        teamCardsWrapper.getChildren().addAll(member1, member2, member3, member4);
        
        javafx.scene.control.ScrollPane teamScrollPane = new javafx.scene.control.ScrollPane();
        teamScrollPane.setContent(teamCardsWrapper);
        teamScrollPane.setFitToWidth(true);
        teamScrollPane.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);
        teamScrollPane.setVbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED);
        teamScrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent; -fx-border-color: transparent;");
        
        VBox.setVgrow(teamScrollPane, Priority.ALWAYS);

        VBox projectInfo = createProjectInfoBox();
        VBox projectWrapper = new VBox(projectInfo);
        projectWrapper.setAlignment(Pos.CENTER);
        projectWrapper.setPadding(new Insets(20 * scale, 0, 0, 0));

        contentArea.getChildren().addAll(headerSection, teamScrollPane, projectWrapper);
        mainContainer.getChildren().addAll(sidebar.getSidebar(), contentArea);

        return mainContainer;
    }

    private VBox createMemberCard(String name, String course, String studentNumber, String email, String contribution) {
        VBox card = new VBox(12 * scale);
        card.setPadding(new Insets(20 * scale, 25 * scale, 20 * scale, 25 * scale));
        card.setAlignment(Pos.TOP_LEFT);
        card.setPrefWidth(1300 * scale); // Use pref width for consistent sizing
        card.setMaxWidth(1500 * scale);
        card.setMinHeight(280 * scale);
        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 16;" +
                "-fx-border-color: #E0E0E0;" +
                "-fx-border-radius: 16;" +
                "-fx-border-width: 1;"
        );

        DropShadow shadow = new DropShadow();
        shadow.setRadius(12);
        shadow.setOffsetY(5);
        shadow.setColor(Color.rgb(0, 0, 0, 0.15));
        card.setEffect(shadow);

        HBox topRow = new HBox(20 * scale);
        topRow.setAlignment(Pos.CENTER_LEFT);
        
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2F4156;");
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        
        Label courseLabel = new Label(course);
        courseLabel.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;" +
                "-fx-background-color: #2F4156;" +
                "-fx-padding: 6 15 6 15;" +
                "-fx-background-radius: 20;"
        );
        
        topRow.getChildren().addAll(nameLabel, courseLabel);

        HBox middleRow = new HBox(30 * scale);
        middleRow.setAlignment(Pos.CENTER_LEFT);
        middleRow.setPadding(new Insets(6 * scale, 0, 12 * scale, 0));
        
        Label studentNumLabel = new Label("🎓 Student No: " + studentNumber);
        studentNumLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #777777;");
        studentNumLabel.setWrapText(true);
        
        Label emailLabel = new Label("📧 " + email);
        emailLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #3A5169;");
        emailLabel.setWrapText(true);
        
        middleRow.getChildren().addAll(studentNumLabel, emailLabel);

        VBox contributionBox = new VBox(8 * scale);
        contributionBox.setPadding(new Insets(10 * scale, 0, 0, 0));
        VBox.setVgrow(contributionBox, Priority.ALWAYS); 
        
        Label contributionTitle = new Label("Key Contributions");
        contributionTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #2F4156;");

        Label contributionText = new Label(contribution);
        contributionText.setStyle("-fx-font-size: 13px; -fx-text-fill: #555555; -fx-line-spacing: 4px;");
        contributionText.setWrapText(true);
        contributionText.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(contributionText, Priority.ALWAYS); 
        
        contributionBox.getChildren().addAll(contributionTitle, contributionText);

        card.getChildren().addAll(
                topRow,
                middleRow,
                contributionBox
        );

        return card;
    }

    private VBox createProjectInfoBox() {
        VBox infoBox = new VBox(12 * scale);
        infoBox.setPadding(new Insets(25 * scale, 35 * scale, 25 * scale, 35 * scale));
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setPrefWidth(1100 * scale);
        infoBox.setMaxWidth(1200 * scale);
        infoBox.setMinHeight(180 * scale);
        infoBox.setPrefHeight(200 * scale);
        infoBox.setStyle(
                "-fx-background-color: #2F4156;" +
                "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;"
        );

        DropShadow shadow = new DropShadow();
        shadow.setRadius(10);
        shadow.setOffsetX(0);
        shadow.setOffsetY(4);
        shadow.setColor(Color.rgb(0, 0, 0, 0.2));
        infoBox.setEffect(shadow);

        Label projectTitle = new Label("About This Project");
        projectTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox descContainer = new VBox(4 * scale);
        descContainer.setAlignment(Pos.CENTER);
        
        Label line1 = new Label("This course scheduler and planner was created by CMSC 22 CD-5L students as part of their laboratory requirements.");
        line1.setStyle("-fx-font-size: 14px; -fx-text-fill: #E5E5E5; -fx-text-alignment: center;");
        line1.setWrapText(true);
        line1.setAlignment(Pos.CENTER);
        line1.setMaxWidth(Double.MAX_VALUE);
        
        Label line2 = new Label(" The system helps students plan and manage their course schedules efficiently.");
        line2.setStyle("-fx-font-size: 14px; -fx-text-fill: #E5E5E5; -fx-text-alignment: center;");
        line2.setWrapText(true);
        line2.setAlignment(Pos.CENTER);
        line2.setMaxWidth(Double.MAX_VALUE);
        
        descContainer.getChildren().addAll(line1, line2);

        Label courseLabel = new Label("CMSC 22 - Object-Oriented Programming");
        courseLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #CCCCCC; -fx-font-style: italic;");
        courseLabel.setAlignment(Pos.CENTER);
        courseLabel.setWrapText(true);

        infoBox.getChildren().addAll(projectTitle, descContainer, courseLabel);

        return infoBox;
    }
}

