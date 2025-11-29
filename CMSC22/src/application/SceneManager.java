package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uiandlogic.DataAccess;
import uiandlogic.User;

// This is a static class that manages every scene changes as well as updating all the data of the program
public class SceneManager {

    private static Stage primaryStage;
    private static User currentUser;
    private static DataAccess dataAccess; // this is responsible for updating the data base throughout all the controller


    public static void setStage(Stage stage) {
        primaryStage = stage;
    }
    
    // update currently signed in user
    public static void setUser(User user) {
        currentUser = user;
    }
    
    // signed in user for current session (for displaying later)
    public static User getUser() {
        return currentUser;
    }
    
    // to be able to access DataAccess through this class
    public static void setDataAccess(DataAccess da) {
        dataAccess = da;
    }
    
    public static DataAccess getDataAccess() {
        return dataAccess;
    }
    
    // method to switch between scenes
    public static void switchTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            
            Parent root = loader.load();
            
            // set a dimension to maintain consistent sizing through all the scenes
            primaryStage.setScene(new Scene(root, 900, 600)); 
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
