module CMSC22 {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;

    opens application.ui to javafx.fxml;
    opens application.controller to javafx.fxml;
    opens uiandlogic to javafx.base;
    

    exports application;
}