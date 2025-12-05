package scenes;

import javafx.scene.Scene;
import javafx.scene.Parent;


public abstract class BaseScene {
	
	// scale to keep the same size in figma
	final double scale = .71;
    
    protected abstract Parent buildScene();
    
    public Scene createScene() {
        Parent root = buildScene();
        return new Scene(root);
    }
    
}