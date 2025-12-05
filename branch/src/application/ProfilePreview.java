package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProfilePreview extends Application { 
	@Override 
	public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(
                getClass().getResource("/application/ui/profile.fxml")
            );

            Scene scene = new Scene(root, 900, 600); 

            primaryStage.setTitle("Profile Preview");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args); 
    }
}
