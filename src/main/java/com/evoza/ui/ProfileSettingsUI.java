package com.evoza.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.evoza.utils.ProfileManager;
import com.evoza.utils.Profiles;

public class ProfileSettingsUI {
    public static void openProfileSettings(Stage parentStage, int profileId) {
        Stage settingsStage = new Stage();
        settingsStage.initModality(Modality.APPLICATION_MODAL);
        settingsStage.initStyle(StageStyle.TRANSPARENT);

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(0, 20, 10, 20));
        vbox.setStyle("-fx-background-color: #395f84; -fx-background-radius: 20;");

        // Title bar
        HBox titleBar = new HBox();
        titleBar.setStyle("-fx-background-color: #395f84; -fx-padding: 10;");
        titleBar.setAlignment(Pos.TOP_RIGHT);
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand;");
        closeButton.setOnAction(e -> settingsStage.close());
        titleBar.getChildren().add(closeButton);

        // Title
        Text titleText = new Text("Profile Settings");
        titleText.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #ffffff;");

        // Form fields
        TextField fullnameField = new TextField();
        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();

        styleField(fullnameField, "Full Name");
        styleField(emailField, "Email");
        styleField(passwordField, "Password");

        // Update button
        Button updateButton = new Button("Update Profile");
        updateButton.setStyle("-fx-background-color: #e6e8e9; -fx-text-fill: #000000; -fx-background-radius: 50;");
        updateButton.setPrefWidth(120);
        updateButton.setPrefHeight(40);

        String[] originalPassword = new String[1]; // To store original password

        // Load current profile data
        Profiles currentProfile = ProfileManager.getProfileById(profileId);
        if (currentProfile != null) {
            fullnameField.setText(currentProfile.getFullname());
            emailField.setText(currentProfile.getEmail());
            passwordField.setText(currentProfile.getPassword());
            originalPassword[0] = currentProfile.getPassword();
        }

        updateButton.setOnAction(e -> {
            String newPassword = passwordField.getText().equals(originalPassword[0]) ? null : passwordField.getText();
            if (ProfileManager.updateProfile(profileId, fullnameField.getText(), emailField.getText(), newPassword)) {
                CustomPopupAlert.showNotification("Profile updated successfully");
                settingsStage.close();
            }
        });

        vbox.getChildren().addAll(
            titleBar,
            titleText,
            createFormRow("Full Name", fullnameField),
            createFormRow("Email", emailField),
            createFormRow("Password", passwordField),
            updateButton
        );

        Scene scene = new Scene(vbox, 400, 300);
        scene.setFill(Color.TRANSPARENT);
        settingsStage.setScene(scene);
        settingsStage.show();
    }

    private static void styleField(TextField field, String prompt) {
        field.setPromptText(prompt);
        field.setStyle("-fx-background-radius: 50; -fx-background-color: #9aafc0; -fx-text-fill: #000000;");
        field.setPrefHeight(45);
    }

    private static HBox createFormRow(String label, TextField field) {
        HBox row = new HBox(10);
        Text labelText = new Text(label);
        labelText.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold;");
        row.getChildren().addAll(labelText, field);
        return row;
    }
}