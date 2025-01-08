package com.evoza.ui;


import com.evoza.utils.BookmarkUtils;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BookmarkSaveUI {

    public static void openbookmarksave(Stage primaryStage, int profileId, String url) {
        Stage bookStage = new Stage();
        bookStage.initModality(Modality.APPLICATION_MODAL);
        bookStage.initStyle(StageStyle.TRANSPARENT);

        VBox vbox = new VBox(10);
        vbox.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        vbox.setPadding(new Insets(0, 20, 10, 20));
        vbox.setStyle("-fx-background-color: #9aafc0; -fx-background-radius: 20;");

        // Custom title bar
        HBox titleBar = new HBox();
        titleBar.setStyle("-fx-background-color: #9aafc0; -fx-padding: 10;");
        titleBar.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 20;");
        closeButton.setOnAction(e -> bookStage.close());
        closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #00000; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 20;"));
        closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 20;"));

        titleBar.getChildren().addAll(closeButton);
        HBox.setMargin(closeButton, new Insets(0, -22, 0, 0));
        VBox.setMargin(titleBar, new Insets(-8, 0, 0, 0));

        VBox titlebox = new VBox(10);
        titlebox.setAlignment(javafx.geometry.Pos.CENTER);
        Text titleText = new Text("Save Bookmark");
        titleText.textAlignmentProperty().set(javafx.scene.text.TextAlignment.CENTER);
        titleText.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #ffffff;");
        titlebox.getChildren().addAll(titleText);

        Text titlelabel = new Text("Title");
        titlelabel.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold;");
        VBox.setMargin(titlelabel, new Insets(25, 0, 0, 0));

        TextField titlefield = new TextField();
        titlefield.setStyle("-fx-background-radius: 50; -fx-background-color: #e6e8e9; -fx-text-fill: #000000;");
        titlefield.setPrefWidth(80);
        titlefield.setPrefHeight(45);
        VBox.setMargin(titlefield, new Insets(0, 0, 0, 0));

        Text urlLabel = new Text("Url");
        urlLabel.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold;");
        VBox.setMargin(urlLabel, new Insets(10, 0, 0, 0));

        TextField urlField = new TextField(url);
        urlField.setStyle("-fx-background-radius: 50; -fx-background-color: #e6e8e9; -fx-text-fill: #000000;");
        urlField.setPrefWidth(80);
        urlField.setPrefHeight(45);
        VBox.setMargin(urlField, new Insets(0, 0, 0, 0));




        Button submitButton = new Button("Save");
        submitButton.setStyle("-fx-background-color: #e6e8e9; -fx-text-fill: #000000; -fx-background-radius: 50;");
        submitButton.setPrefWidth(120);
        submitButton.setPrefHeight(40);
        VBox.setMargin(submitButton, new Insets(30, 0, 0, 100));

        submitButton.setOnAction(e -> {
            String title = titlefield.getText();
            String url1 = urlField.getText();
            if (title.isEmpty() || url.isEmpty()) {
                CustomPopupAlert.showNotification("Please fill all the fields");
            } else if (BookmarkUtils.bookmarkExists(profileId, url)) {
                CustomPopupAlert.showNotification("Bookmark already exists");
            } else {
                BookmarkUtils.saveBookmark(profileId, title, url);
                CustomPopupAlert.showNotification("Bookmark saved successfully");
                bookStage.close();
            }

        });
        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #dadada; -fx-text-fill: #000000; -fx-cursor: hand; -fx-background-radius: 50;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #e6e8e9; -fx-text-fill: #000000; -fx-background-radius: 50;"));

        vbox.getChildren().addAll(titleBar, titlebox, titlelabel, titlefield, urlLabel, urlField,  submitButton);

        Scene scene = new Scene(vbox, 350, 400);
        scene.setFill(Color.TRANSPARENT);
        bookStage.setScene(scene);
        bookStage.showAndWait();
    }
}