package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(
                getClass().getResource("/application/ui/sign_in.fxml")
            );

            Scene scene = new Scene(root, 900, 600);

            scene.getStylesheets().add(
                getClass().getResource("/application/styles/signin.css").toExternalForm()
            );

            primaryStage.setTitle("Sign In");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}