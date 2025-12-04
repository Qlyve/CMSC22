package scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    
    private static Stage primaryStage;

    public static void initialize(Stage stage) {
        primaryStage = stage;
    }
    
    // for switching
    public static void switchTo(BaseScene baseScene) {
        if (primaryStage == null) {
            throw new IllegalStateException("SceneManager was not initialized.");
        }
        
        Scene scene = baseScene.createScene();
        primaryStage.setScene(scene);
    }
    

    public static Stage getStage() {
        return primaryStage;
    }
}