package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import uiandlogic.DataAccess;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
        	// this basically just loads the existing user from users.txt
        	DataAccess dataAccess = new DataAccess();
        	
        	// this line sets the data access for the current session which 
        	// lets the program edit the d ata base just by calling SceneManager 
        	// (and access all methods inside DataAccess)
        	SceneManager.setDataAccess(dataAccess); 
        	
            SceneManager.setStage(primaryStage);

            Parent root = FXMLLoader.load(
                getClass().getResource("/application/ui/sign_in.fxml")
            );
            
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}