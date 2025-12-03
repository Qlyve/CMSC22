package scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import uiandlogic.User;

public class ProfileScene extends BaseScene {
    
    private User currentUser;
    
    public ProfileScene(User user) {
        this.currentUser = user;
    }
    
    @Override
    protected Parent buildScene() {
        HBox mainContainer = new HBox();
        
        // create and add the sidebar
        Sidebar sidebar = new Sidebar(scale, "Profile", currentUser);
        
        // create main area for content
        StackPane contentArea = new StackPane();
        contentArea.setAlignment(Pos.CENTER);
        HBox.setHgrow(contentArea, Priority.ALWAYS);
        
        VBox homeContent = new VBox(20);
        homeContent.setAlignment(Pos.CENTER);
        homeContent.setPadding(new Insets(50));
        
        Label welcomeLabel = new Label("PLANNER SCENE!");
        welcomeLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        homeContent.getChildren().addAll(welcomeLabel );
        contentArea.getChildren().add(homeContent);
        
        mainContainer.getChildren().addAll(sidebar.getSidebar(), contentArea);
        
        return mainContainer;
    }
}
