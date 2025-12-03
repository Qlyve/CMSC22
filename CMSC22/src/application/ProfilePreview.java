package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProfilePreview extends Application { // FIX: Must extend Application
	@Override // FIX: Must override the start method
	public void start(Stage primaryStage) {
        try {
            // Ensure this path is correct: /application/ui/profile.fxml
            Parent root = FXMLLoader.load(
                getClass().getResource("/application/ui/profile.fxml")
            );

            Scene scene = new Scene(root, 900, 600); // Using the FXML defined size

            primaryStage.setTitle("Profile Preview");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args); // FIX: Must call Application.launch(args)
    }
}