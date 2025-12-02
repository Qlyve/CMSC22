package scenes;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
    	// USE admin admin for email and password for now
        // initialize the SceneManager with the primary stage
        SceneManager.initialize(primaryStage);
        
        // set window properties
        primaryStage.setTitle("CMSC Planner");
        primaryStage.setWidth(1024);
        primaryStage.setHeight(768);
        
        // show login scene first
        SceneManager.switchTo(new LoginScene());
        
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}