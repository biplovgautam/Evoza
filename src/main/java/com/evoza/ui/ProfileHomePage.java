package com.evoza.ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProfileHomePage {
    public void start(Stage profileStage, int profileId) {
        profileStage.setTitle("Profile ID: " + profileId);

        BorderPane borderPane = new BorderPane();

        // Add custom title bar
        CustomTitleBar customTitleBar = new CustomTitleBar(profileStage);
        borderPane.setTop(customTitleBar);

        VBox vbox = new VBox(10);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setPadding(new Insets(10));

        Text profileIdText = new Text("Profile ID: " + profileId);
        profileIdText.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        // Add more UI components and logic for the profile home page here
        vbox.getChildren().add(profileIdText);

        borderPane.setCenter(vbox);

        Scene scene = new Scene(borderPane, 800, 600);
        profileStage.setScene(scene);
        profileStage.show();
    }
}