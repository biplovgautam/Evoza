package com.evoza.ui;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class CustomPopupAlert {

    public static void showNotification(String message) {
        Stage notificationStage = new Stage();
        notificationStage.initModality(Modality.APPLICATION_MODAL);
        notificationStage.initStyle(StageStyle.TRANSPARENT);

        VBox vbox = new VBox(10);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 10;");
        vbox.setMinHeight(200);

        // Create a close button
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14; -fx-cursor: hand;");
        closeButton.setOnAction(e -> notificationStage.close());

        // Create a HBox to hold the close button and align it to the top right
        HBox topRightBox = new HBox();
        topRightBox.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        topRightBox.getChildren().add(closeButton);
        topRightBox.setPadding(new Insets((-20), 0, 0, 0));

        Text messageText = new Text(message);
        messageText.setFill(Color.WHITE);
        messageText.setStyle("-fx-font-size: 14;");

        vbox.getChildren().addAll(topRightBox, messageText);

        Scene scene = new Scene(vbox, 300, 100);
        scene.setFill(Color.TRANSPARENT);
        notificationStage.setScene(scene);

        // Close the notification after 3 seconds
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> notificationStage.close());
        delay.play();

        notificationStage.show();
    }
}