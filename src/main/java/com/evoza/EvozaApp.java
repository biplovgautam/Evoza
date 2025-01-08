package com.evoza;

import com.evoza.ui.CustomTitleBar;
import com.evoza.ui.LandingPageUI;
import com.evoza.utils.DatabaseHelper;

import java.io.InputStream;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EvozaApp extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.initStyle(StageStyle.UNDECORATED); // Remove default title bar
        openLandingPage();
    }

    public static void openLandingPage() {
        try {
            // Initialize avatars
            DatabaseHelper.initializeAvatars();

            BorderPane root = LandingPageUI.start(primaryStage);
            BorderPane borderPane = new BorderPane();
            borderPane.setTop(new CustomTitleBar(primaryStage)); // Add custom title bar
            borderPane.setCenter(root);

            Scene scene = new Scene(borderPane, 1024, 768);

            primaryStage.setTitle("Evoza Web Browser");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true); // Allow resizing
            primaryStage.setMaximized(true); // Open in maximized mode

            // Set the application icon
            InputStream iconStream = EvozaApp.class.getResourceAsStream("/images/icons/logo.png");
            if (iconStream == null) {
                System.err.println("Icon resource not found!");
            } else {
                primaryStage.getIcons().add(new Image(iconStream));
            }

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}