package com.evoza.ui;

import java.util.Random;

import com.evoza.browser.BrowserUtils;
import com.evoza.utils.EmailUtil;
import com.evoza.utils.ProfileManager;
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

public class SignupAuthenticationUI {

    private static String capitalize(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }


    public static void openSignupPopup(Stage primaryStage) {
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
        Text titleText = new Text("Signup New Profile");
        titleText.textAlignmentProperty().set(javafx.scene.text.TextAlignment.CENTER);
        titleText.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #ffffff;");
        titlebox.getChildren().addAll(titleText);

        HBox namebox = new HBox();
        VBox.setMargin(namebox, new Insets(25, 0, 0, 0));

        // namebox.setStyle("-fx-background-color:rgb(255, 0, 0);");

        VBox fname = new VBox();
        // fname.setPrefWidth(30);
        // fname.setStyle("-fx-background-color:rgb(0, 0, 0);");
        Text f_nameLabel = new Text("Firstname*");
        f_nameLabel.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold; ");
        // VBox.setMargin(f_nameLabel, new Insets(25, 0, 0, 0));
        TextField fnameField = new TextField();
        fnameField.setStyle("-fx-background-radius: 50; -fx-background-color: #9aafc0; -fx-text-fill: #000000;");
        fnameField.setPrefHeight(45);
        fnameField.setPrefWidth(150);
        VBox.setMargin(fnameField, new Insets(10, 0, 0, 0));
        fname.getChildren().addAll(f_nameLabel,fnameField);

        VBox lname = new VBox();
        HBox.setMargin(lname, new Insets(0, -2, 0, 90));
        // lname.setStyle("-fx-background-color:rgb(0, 0, 0);");
        Text l_nameLabel = new Text("Lastname*");
        l_nameLabel.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold; ");
        // VBox.setMargin(f_nameLabel, new Insets(25, 0, 0, 0));
        TextField lnameField = new TextField();
        lnameField.setStyle("-fx-background-radius: 50; -fx-background-color: #9aafc0; -fx-text-fill: #000000;");
        lnameField.setPrefHeight(45);
        VBox.setMargin(lnameField, new Insets(2, 0, 0, 0));
        lname.getChildren().addAll(l_nameLabel,lnameField);


        namebox.getChildren().addAll(fname,lname);

        Text usernameLabel = new Text("Username*");
        usernameLabel.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold;");
        VBox.setMargin(usernameLabel, new Insets(10, 0, 0, 0));

        TextField usernameField = new TextField();
        usernameField.setStyle("-fx-background-radius: 50; -fx-background-color: #9aafc0; -fx-text-fill: #000000;");
        usernameField.setPrefWidth(80);
        usernameField.setPrefHeight(45);
        VBox.setMargin(usernameField, new Insets(-8, 0, 0, 0));

        Text emailLabel = new Text("Email*");
        emailLabel.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold;");
        VBox.setMargin(emailLabel, new Insets(10, 0, 0, 0));

        TextField emailField = new TextField();

        emailField.setStyle("-fx-background-radius: 50; -fx-background-color: #9aafc0; -fx-text-fill: #000000;");
        emailField.setPrefWidth(80);
        emailField.setPrefHeight(45);
        VBox.setMargin(emailField, new Insets(-8, 0, 0, 0));

        Text passwordLabel = new Text("Password*");
        passwordLabel.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold;");
        VBox.setMargin(passwordLabel, new Insets(10, 0, 0, 0));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setStyle("-fx-background-radius: 50; -fx-background-color: #9aafc0; -fx-text-fill: #000000;");
        passwordField.setPrefWidth(80);
        passwordField.setPrefHeight(45);
        VBox.setMargin(passwordField, new Insets(-8, 0, 0, 0));

        Text confirmpasswordLabel = new Text("Confirm Password*");
        confirmpasswordLabel.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold;");
        VBox.setMargin(confirmpasswordLabel, new Insets(10, 0, 0, 0));

        PasswordField confirmpasswordField = new PasswordField();
        confirmpasswordField.setPromptText("Re-Enter Password");
        confirmpasswordField.setStyle("-fx-background-radius: 50; -fx-background-color: #9aafc0; -fx-text-fill: #000000;");
        confirmpasswordField.setPrefWidth(80);
        confirmpasswordField.setPrefHeight(45);
        VBox.setMargin(confirmpasswordField, new Insets(-8, 0, 0, 0));

        VBox submitbox = new VBox(10);
        submitbox.setAlignment(javafx.geometry.Pos.CENTER);
        Button submitButton = new Button("SignUp");
        submitButton.setStyle("-fx-background-color: #e6e8e9; -fx-text-fill: #000000; -fx-background-radius: 50;");
        submitButton.setPrefWidth(120);
        submitButton.setPrefHeight(40);
        VBox.setMargin(submitButton, new Insets(30, 0, 0, 0));

        submitButton.setOnAction(e -> {
            String username = usernameField.getText().toLowerCase();
            
            String fullname = capitalize(fnameField.getText()) + " " + capitalize(lnameField.getText()); // Capitalize first and last name
            String email = emailField.getText().toLowerCase(); // Assuming you have an emailField
            Random random = new Random();
            int profilePicId = random.nextInt(5) + 1; // Randomize profilePicId from 1 to 5
            boolean isActive = false; // Initially set to false until email verification
            String password = passwordField.getText(); // Assuming you have a passwordField
            String confirmPassword = confirmpasswordField.getText(); // Assuming you have a confirmPasswordField
            
            if (!password.equals(confirmPassword)) {
                CustomPopupAlert.showNotification("Passwords do not match.");
                return;
            }
            else{
                boolean success = ProfileManager.createProfile(username, fullname, email, password, profilePicId, isActive);
                if (success) {
                    CustomPopupAlert.showNotification("User signed up successfully.");
                    System.out.println("User signed up successfully.");
                    LandingPageUI.refresh(primaryStage);
                     // Send a welcome email
                    String subject = "Welcome to Evoza!";
                    String content =  "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; }" +
                    ".container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e6e6e6; border-radius: 10px; background-color: #f9f9f9; }" +
                    ".header { text-align: center; padding: 10px 0; }" +
                    ".header img { max-width: 100px; }" +
                    ".content { padding: 20px; }" +
                    ".footer { text-align: center; padding: 10px 0; font-size: 12px; color: #777; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<div class='header'>" +
                    "<h1>Welcome to Evoza!</h1>" +
                    "<img src='https://i.imgur.com/Bq2BAoR.png' alt='Evoza Logo'>" +
                    "</div>" +
                    "<div class='content'>" +
                    "<h1>Signed up!</h1>" +
                    "<p>Username: " + username + "</p>" +
                    "<p>password: " + password + "</p>" +
                    "<p>Thank you for signing up!</p>" +
                    "<p>Best regards,<br>Evoza Team</p>" +
                    "</div>" +
                    "<div class='footer'>" +
                    "<p>&copy; 2024 Evoza. All rights reserved.</p>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
                    EmailUtil.sendEmail(email, subject, content);
                    authStage.close();
                } else {
                    System.err.println("User signup failed.");
                }
            }
        });
        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #dadada; -fx-text-fill: #000000; -fx-cursor: hand; -fx-background-radius: 50;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #e6e8e9; -fx-text-fill: #000000; -fx-background-radius: 50;"));
        submitbox.getChildren().addAll(submitButton);

        vbox.getChildren().addAll(titleBar, titlebox,namebox, usernameLabel, usernameField, emailLabel, emailField, passwordLabel, passwordField,confirmpasswordLabel,confirmpasswordField, submitbox);

        Scene scene = new Scene(vbox, 430, 650);
        scene.setFill(Color.TRANSPARENT);
        authStage.setScene(scene);
        authStage.showAndWait();
    }
}