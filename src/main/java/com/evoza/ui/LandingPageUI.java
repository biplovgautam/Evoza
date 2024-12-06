package com.evoza.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LandingPageUI {
    public VBox start(Stage primaryStage) throws Exception {
        return FXMLLoader.load(getClass().getResource("/fxml/LandingPage.fxml"));
    }
}