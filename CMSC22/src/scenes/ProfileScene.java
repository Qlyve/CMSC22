package scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.GridPane;
import uiandlogic.User;

public class ProfileScene extends BaseScene {
    
    private User user;
    
    public ProfileScene(User user) {
        this.user = user;
    }
    
    @Override
    protected Parent buildScene() {
        HBox mainContainer = new HBox();
        mainContainer.setStyle("-fx-background-color: #E5E5E5;");
        
        // create and add the sidebar
        Sidebar sidebar = new Sidebar(scale, "Profile", user);
        
        // create content area
        VBox contentArea = new VBox(20 * scale);
        contentArea.setPadding(new Insets(40 * scale));
        HBox.setHgrow(contentArea, Priority.ALWAYS);
        
        // profile information container
        VBox profileInfoBox = new VBox(15 * scale);
        profileInfoBox.setStyle("-fx-background-color: white; " +
                               "-fx-border-color: #CCCCCC; " +
                               "-fx-border-width: 1; " +
                               "-fx-border-radius: 5; " +
                               "-fx-background-radius: 5;");
        profileInfoBox.setPadding(new Insets(20 * scale));
        HBox.setHgrow(profileInfoBox, Priority.ALWAYS);
        
        // title
        Label titleLabel = new Label("Profile Information");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        
        // create grid for profile fields
        GridPane grid = new GridPane();
        grid.setHgap(120 * scale);
        grid.setVgap(25 * scale);
        grid.setPadding(new Insets(10 * scale, 0, 0, 0));
        
        // first row
        VBox firstNameBox = createFieldBox("First Name:", user.getFirstName());
        VBox middleNameBox = createFieldBox("Middle Name:", user.getMiddleName());
        VBox lastNameBox = createFieldBox("Last Name:", user.getLastName());
        
        grid.add(firstNameBox, 0, 0);
        grid.add(middleNameBox, 1, 0);
        grid.add(lastNameBox, 2, 0);
        
        // second row
        VBox emailBox = createFieldBox("Email Address:", user.getEmailAddress());
        VBox userTypeBox = createFieldBox("User Type:", user.getUserType());
        
        grid.add(emailBox, 0, 1);
        grid.add(userTypeBox, 1, 1);
        
        profileInfoBox.getChildren().addAll(titleLabel, grid);
        contentArea.getChildren().add(profileInfoBox);
        
        mainContainer.getChildren().addAll(sidebar.getSidebar(), contentArea);
        
        return mainContainer;
    }
    
    private VBox createFieldBox(String labelText, String valueText) {
        VBox box = new VBox(5 * scale);
        
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 15px; -fx-text-fill: #666666;");
        
        Label value = new Label(valueText != null ? valueText : "");
        value.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");
        
        box.getChildren().addAll(label, value);
        return box;
    }
}

