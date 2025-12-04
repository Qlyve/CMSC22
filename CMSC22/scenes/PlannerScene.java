package scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PlannerScene extends BaseScene {
    
    private String username;
    
    public PlannerScene(String username) {
        this.username = username;
    }
    
    @Override
    protected Parent buildScene() {
        HBox mainContainer = new HBox();
        
        // create and add the sidebar
        Sidebar sidebar = new Sidebar(scale, "Planner");
        
        // create main area for content
        StackPane contentArea = new StackPane();
        contentArea.setAlignment(Pos.CENTER);
        HBox.setHgrow(contentArea, Priority.ALWAYS);
        
        VBox homeContent = new VBox(20);
        homeContent.setAlignment(Pos.CENTER);
        homeContent.setPadding(new Insets(50));
        
        Label welcomeLabel = new Label("PROFILE SCENE!");
        welcomeLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        homeContent.getChildren().addAll(welcomeLabel );
        contentArea.getChildren().add(homeContent);
        
        mainContainer.getChildren().addAll(sidebar.getSidebar(), contentArea);
        
        return mainContainer;
    }
    // METHODS AND FUNCTIONS
}