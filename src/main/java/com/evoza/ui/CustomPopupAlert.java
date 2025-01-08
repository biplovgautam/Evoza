package com.evoza.ui;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.function.Consumer;

public class CustomPopupAlert {

    public static void showNotification(String message) {
        Stage notificationStage = new Stage();
        notificationStage.initModality(Modality.APPLICATION_MODAL);
        notificationStage.initStyle(StageStyle.TRANSPARENT);

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-width: 0;");

        Text messageText = new Text(message);
        messageText.setStyle("-fx-font-size: 14;");

        vbox.getChildren().addAll(messageText);

        Scene scene = new Scene(vbox, 300, 100);
        scene.setFill(Color.TRANSPARENT);
        notificationStage.setScene(scene);

        // Close the notification after 3 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> notificationStage.close());
        delay.play();

        notificationStage.show();
    }

    public static void showConfirmation(String message, Consumer<Boolean> responseHandler) {
        Stage confirmationStage = new Stage();
        confirmationStage.initModality(Modality.APPLICATION_MODAL);
        confirmationStage.initStyle(StageStyle.TRANSPARENT);

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setStyle("-fx-background-color:rgb(99, 115, 131); -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-width: 0;");

        Text messageText = new Text(message);
        messageText.setStyle("-fx-font-size: 14;");

        Region spacer = new Region();
        spacer.setPrefHeight(30); // Set the height of the spacer


        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button yesButton = new Button("Yes");
        yesButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5; -fx-cursor: hand;");
        yesButton.setPrefWidth(100); 
        yesButton.setOnAction(e -> {
            responseHandler.accept(true);
            confirmationStage.close();
        });

        Button noButton = new Button("No");
        noButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-background-radius: 5; -fx-cursor: hand;");
        noButton.setPrefWidth(100); 
        noButton.setOnAction(e -> {
            responseHandler.accept(false);
            confirmationStage.close();
        });

        buttonBox.getChildren().addAll(yesButton, noButton);

        vbox.getChildren().addAll(messageText,spacer, buttonBox);

        Scene scene = new Scene(vbox, 300, 150);
        scene.setFill(Color.TRANSPARENT);
        confirmationStage.setScene(scene);

        confirmationStage.show();
    }
}