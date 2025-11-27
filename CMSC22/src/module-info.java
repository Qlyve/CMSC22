module CMSC22 {
    requires javafx.controls;
    requires javafx.fxml;

    opens application.ui to javafx.fxml;
    opens application.controller to javafx.fxml;

    exports application;
}