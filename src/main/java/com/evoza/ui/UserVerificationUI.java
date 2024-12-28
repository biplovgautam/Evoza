package com.evoza.ui;

import com.evoza.utils.AuthenticationManager;
import com.evoza.utils.BrowserUtils;
import com.evoza.utils.EmailUtil;
import com.evoza.utils.ProfileManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserVerificationUI {

    public static void openVerificationPopup(Stage primaryStage, String username, String email,int profileId) {
        Stage verificationStage = new Stage();
        verificationStage.initModality(Modality.APPLICATION_MODAL);
        verificationStage.initStyle(StageStyle.TRANSPARENT);

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
        closeButton.setOnAction(e -> verificationStage.close());
        closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #00000; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 20;"));
        closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 20;"));

        titleBar.getChildren().addAll(closeButton);
        HBox.setMargin(closeButton, new Insets(0, -22, 0, 0));
        VBox.setMargin(titleBar, new Insets(-8, 0, 0, 0));

        VBox titlebox = new VBox(10);
        titlebox.setAlignment(javafx.geometry.Pos.CENTER);
        Text titleText = new Text("Verify User");
        titleText.textAlignmentProperty().set(javafx.scene.text.TextAlignment.CENTER);
        titleText.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #ffffff;");
        titlebox.getChildren().addAll(titleText);

        Text emailLabel = new Text("Email");
        emailLabel.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold;");
        VBox.setMargin(emailLabel, new Insets(10, 0, 0, 0));

        TextField emailField = new TextField(email);
        emailField.setEditable(false);
        emailField.setStyle("-fx-background-radius: 50; -fx-background-color: #9aafc0; -fx-text-fill: #000000;");
        emailField.setPrefWidth(80);
        emailField.setPrefHeight(45);
        VBox.setMargin(emailField, new Insets(5, 0, 0, 0));

        Text usernameLabel = new Text("Username");
        usernameLabel.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold;");
        VBox.setMargin(usernameLabel, new Insets(25, 0, 0, 0));

        TextField usernameField = new TextField(username);
        usernameField.setEditable(false);
        usernameField.setStyle("-fx-background-radius: 50; -fx-background-color: #9aafc0; -fx-text-fill: #000000;");
        usernameField.setPrefWidth(80);
        usernameField.setPrefHeight(45);
        VBox.setMargin(usernameField, new Insets(5, 0, 0, 0));

        

        Text otpLabel = new Text("OTP*");
        otpLabel.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold;");
        VBox.setMargin(otpLabel, new Insets(10, 0, 0, 0));

        TextField otpField = new TextField();
        otpField.setPromptText("Enter 4-digit OTP");
        otpField.setStyle("-fx-background-radius: 50; -fx-background-color: #9aafc0; -fx-text-fill: #000000;");
        otpField.setPrefWidth(80);
        otpField.setPrefHeight(45);
        VBox.setMargin(otpField, new Insets(5, 0, 0, 0));

        HBox submitbox = new HBox(10);
        VBox.setMargin(submitbox, new Insets(0, 0, 0, 20));


        Text resendOtpText = new Text("Re-send otp?");
        resendOtpText.setStyle("-fx-font-size: 14; -fx-fill: #9aafc0; -fx-cursor: hand;");
        resendOtpText.setOnMouseEntered(e -> resendOtpText.setStyle("-fx-font-size: 14; -fx-fill:  #dadada; -fx-cursor: hand; -fx-underline: true;"));
        resendOtpText.setOnMouseExited(e -> resendOtpText.setStyle("-fx-font-size: 14; -fx-fill:  #9aafc0; -fx-cursor: hand;"));
        HBox.setMargin(resendOtpText, new Insets(5, 10, 0, 0));

        resendOtpText.setOnMouseClicked(e -> {
            // Handle re-send OTP action here
            String otp_nbr = ProfileManager.generateAndStoreOTP(profileId);
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
                    "<h1>OTP Re-sent!</h1>" +
                    "<p>Username: " + username + "</p>" +
                    "<p>OTP: " + otp_nbr + "</p>" +
                    "<p>Thank you for trusting us!</p>" +
                    "<p>Best regards,<br>Evoza Team</p>" +
                    "</div>" +
                    "<div class='footer'>" +
                    "<p>&copy; 2024 Evoza. All rights reserved.</p>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
                    EmailUtil.sendEmail(email, subject, content);
                    CustomPopupAlert.showNotification("OTP sent check you mail");
        });

        Button submitButton = new Button("Verify");
        submitButton.setStyle("-fx-background-color: #e6e8e9; -fx-text-fill: #000000; -fx-background-radius: 50;");
        submitButton.setPrefWidth(120);
        submitButton.setPrefHeight(40);
        HBox.setMargin(submitButton, new Insets(20, 0, 0, 80));

        submitButton.setOnAction(e -> {
            boolean isverified = ProfileManager.verifyOTP(profileId, otpField.getText());
            if (isverified) {
                verificationStage.close();
                BrowserUtils.openProfileHomePage(primaryStage, profileId);
            } else {
                CustomPopupAlert.showNotification("Invalid OTP. Please try again.");
                System.err.println("wrong otp failed.");
            }
        });
        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #dadada; -fx-text-fill: #000000; -fx-cursor: hand; -fx-background-radius: 50;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #e6e8e9; -fx-text-fill: #000000; -fx-background-radius: 50;"));
        submitbox.getChildren().addAll(resendOtpText,submitButton);

        vbox.getChildren().addAll(titleBar, titlebox, usernameLabel, usernameField, emailLabel, emailField, otpLabel, otpField, submitbox);

        Scene scene = new Scene(vbox, 400, 500);
        scene.setFill(Color.TRANSPARENT);
        verificationStage.setScene(scene);
        verificationStage.showAndWait();
    }
    


}