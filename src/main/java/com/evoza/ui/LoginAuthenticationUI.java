package com.evoza.ui;

import com.evoza.browser.BrowserUtils;
import com.evoza.utils.AuthenticationManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginAuthenticationUI {

    public static void openAuthenticationPopup(Stage primaryStage, String username, String email, int profileId) {
        Stage authStage = new Stage();
        authStage.initModality(Modality.APPLICATION_MODAL);
        authStage.initStyle(StageStyle.TRANSPARENT);

        VBox vbox = new VBox(10);
        vbox.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        vbox.setPadding(new Insets(0, 20, 10, 20));
        vbox.setStyle("-fx-background-color: #395f84; -fx-background-radius: 20;");

        // Custom title bar
        HBox titleBar = new HBox();
        titleBar.setStyle("-fx-background-color: #395f84; -fx-padding: 10;");
        titleBar.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 20;");
        closeButton.setOnAction(e -> authStage.close());
        closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #00000; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 20;"));
        closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 20;"));

        titleBar.getChildren().addAll(closeButton);
        HBox.setMargin(closeButton, new Insets(0, -22, 0, 0));
        VBox.setMargin(titleBar, new Insets(-8, 0, 0, 0));

        VBox titlebox = new VBox(10);
        titlebox.setAlignment(javafx.geometry.Pos.CENTER);
        Text titleText = new Text("Login Authentication");
        titleText.textAlignmentProperty().set(javafx.scene.text.TextAlignment.CENTER);
        titleText.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #ffffff;");
        titlebox.getChildren().addAll(titleText);

        Text usernameLabel = new Text("Username:");
        usernameLabel.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold;");
        VBox.setMargin(usernameLabel, new Insets(25, 0, 0, 0));

        TextField usernameField = new TextField(username);
        usernameField.setEditable(false);
        usernameField.setStyle("-fx-background-radius: 50; -fx-background-color: #9aafc0; -fx-text-fill: #000000;");
        usernameField.setPrefWidth(80);
        usernameField.setPrefHeight(45);
        VBox.setMargin(usernameField, new Insets(5, 0, 0, 0));

        Text emailLabel = new Text("Email:");
        emailLabel.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold;");
        VBox.setMargin(emailLabel, new Insets(10, 0, 0, 0));

        TextField emailField = new TextField(email);
        emailField.setEditable(false);
        emailField.setStyle("-fx-background-radius: 50; -fx-background-color: #9aafc0; -fx-text-fill: #000000;");
        emailField.setPrefWidth(80);
        emailField.setPrefHeight(45);
        VBox.setMargin(emailField, new Insets(5, 0, 0, 0));

        Text passwordLabel = new Text("Password:");
        passwordLabel.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold;");
        VBox.setMargin(passwordLabel, new Insets(10, 0, 0, 0));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setStyle("-fx-background-radius: 50; -fx-background-color: #9aafc0; -fx-text-fill: #000000;");
        passwordField.setPrefWidth(80);
        passwordField.setPrefHeight(45);
        passwordField.setOnMouseEntered(e -> passwordField.setStyle("-fx-background-color: #9aafc0; -fx-text-fill: #000000; -fx-cursor: hand; -fx-background-radius: 40;"));
        passwordField.setOnMouseExited(e -> passwordField.setStyle("-fx-background-color: #9aafc0; -fx-text-fill: #000000; -fx-background-radius: 50;"));
        VBox.setMargin(passwordField, new Insets(5, 0, 0, 0));

        VBox submitbox = new VBox(10);
        submitbox.setAlignment(javafx.geometry.Pos.CENTER);
        Button submitButton = new Button("Login");
        submitButton.setStyle("-fx-background-color: #e6e8e9; -fx-text-fill: #000000; -fx-background-radius: 50;");
        submitButton.setPrefWidth(120);
        submitButton.setPrefHeight(40);
        VBox.setMargin(submitButton, new Insets(30, 0, 0, 0));

        submitButton.setOnAction(e -> {
            boolean isAuthenticated = AuthenticationManager.authenticate(username, passwordField.getText());
            if (isAuthenticated) {
                BrowserUtils.openProfileHomePage(primaryStage, profileId);
                authStage.close();
            } else {
                CustomPopupAlert.showNotification("Invalid password. Please try again.");
                System.err.println("Authentication failed.");
            }
        });
        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #dadada; -fx-text-fill: #000000; -fx-cursor: hand; -fx-background-radius: 50;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #e6e8e9; -fx-text-fill: #000000; -fx-background-radius: 50;"));
        submitbox.getChildren().addAll(submitButton);

        vbox.getChildren().addAll(titleBar, titlebox, usernameLabel, usernameField, emailLabel, emailField, passwordLabel, passwordField, submitbox);

        Scene scene = new Scene(vbox, 400, 500);
        scene.setFill(Color.TRANSPARENT);
        authStage.setScene(scene);
        authStage.showAndWait();
    }
}