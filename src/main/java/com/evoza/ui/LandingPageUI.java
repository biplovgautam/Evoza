package com.evoza.ui;

import com.evoza.utils.AuthenticationManager;
import com.evoza.utils.AvatarFetcher;
import com.evoza.utils.Profile;
import com.evoza.utils.ProfileFetcher;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.util.List;

public class LandingPageUI {
    public BorderPane start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        HBox mainContainer = new HBox();
        VBox leftContainer = new VBox();
        VBox rightContainer = new VBox(10);

        // Set fixed width for the left container
        leftContainer.setPrefWidth(250);
        leftContainer.setMaxWidth(250);

        // Use percentage-based width for the right container
        rightContainer.setPrefWidth(750);

        leftContainer.setStyle("-fx-background-color: #395f84;");
        rightContainer.setStyle("-fx-background-color: #9aafc0;");

        rightContainer.setAlignment(javafx.geometry.Pos.CENTER);
        rightContainer.setPadding(new Insets(10));
        leftContainer.setAlignment(javafx.geometry.Pos.BOTTOM_CENTER);
        leftContainer.setPadding(new Insets(10));

        // Create containers for logo, title, description, and profiles
        VBox logoTitleDescriptionContainer = new VBox(10);
        logoTitleDescriptionContainer.setAlignment(javafx.geometry.Pos.CENTER); // Center the contents
        GridPane profilesContainer = new GridPane();
        profilesContainer.setHgap(10);
        profilesContainer.setVgap(10);
        profilesContainer.setAlignment(javafx.geometry.Pos.CENTER);

        // Add circular logo image above the title text
        Image logoImage = new Image(getClass().getResourceAsStream("/images/icons/logo.png"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitHeight(100);
        logoImageView.setFitWidth(100);
        Circle clip = new Circle(50, 50, 50);
        logoImageView.setClip(clip);

        Text titleText = new Text("Who's using Evoza?");
        titleText.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #000000;");

        // Create a TextFlow for the description text
        Text descriptionTitle = new Text("Evoza Browser offers a seamless browsing experience with advanced features:\n\n");
        descriptionTitle.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-fill: #515f6a;");

        Text descriptionContent = new Text(
                "- Multiple Profiles: Separate your browsing activities with multiple profiles.\n" +
                "- Guest Mode: Browse privately without saving history.\n" +
                "- Customizable Interface: Personalize your browser with themes and extensions.\n" +
                "- Enhanced Security: Protect your data with built-in security features.\n"
        );
        descriptionContent.setStyle("-fx-font-size: 14; -fx-fill: #515f6a;");

        TextFlow descriptionTextFlow = new TextFlow(descriptionTitle, descriptionContent);
        descriptionTextFlow.setMaxWidth(800);
        descriptionTextFlow.setPadding(new Insets(10));
        descriptionTextFlow.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // Add elements to the logoTitleDescriptionContainer
        logoTitleDescriptionContainer.getChildren().addAll(logoImageView, titleText, descriptionTextFlow);

        // Create a clickable box for guest mode
        Image userImage = new Image(getClass().getResourceAsStream("/images/icons/user.png"));
        HBox guestModeBox = createClickableBox("Guest/Private Mode", userImage);
        guestModeBox.setOnMouseClicked(e -> openProfileHomePage(primaryStage, -1)); // Use -1 for guest mode
        guestModeBox.setPrefSize(150, 50);
        VBox.setMargin(guestModeBox, new Insets(0, 0, 20, 0)); // Margin at the bottom

        leftContainer.getChildren().addAll(guestModeBox);

        // Fetch profiles from the database and add them to the profilesContainer
        List<Profile> profiles = ProfileFetcher.fetchAllProfiles();
        int column = 0;
        int row = 0;
        for (Profile profile : profiles) {
            Image avatarImage = AvatarFetcher.fetchAvatarById(profile.getProfilePicId());
            HBox profileBox = createClickableBox(profile.getUsername(), avatarImage);
            profileBox.setOnMouseClicked(e -> openAuthenticationPopup(primaryStage,profile.getUsername(), profile.getProfileId()));
            profilesContainer.add(profileBox, column, row);
            column++;
            if (column == 5) { // Change this value to set the number of columns
                column = 0;
                row++;
            }
        }

        // Add a clickable box for adding a new profile
        Image adduser = new Image(getClass().getResourceAsStream("/images/icons/add-user.png"));
        
        HBox addProfileBox = createClickableBox("Add Profile", adduser);
        addProfileBox.setOnMouseClicked(e -> openSignupPopup());
        profilesContainer.add(addProfileBox, column, row);

        // Add containers to the rightContainer
        rightContainer.getChildren().addAll(logoTitleDescriptionContainer, profilesContainer);

        HBox.setHgrow(leftContainer, Priority.NEVER);
        HBox.setHgrow(rightContainer, Priority.ALWAYS);

        mainContainer.getChildren().addAll(leftContainer, rightContainer);
        root.setCenter(mainContainer);

        return root;
    }

    private HBox createClickableBox(String text, Image avatarImage) {
        HBox box = new HBox(10);
        if (text.equals("Guest/Private Mode")) {
            box.setPrefSize(80, 50);
            box.setStyle("-fx-background-color: #c5c8cc; -fx-border-color: #000000; -fx-border-width: 0px; -fx-background-radius: 50; -fx-border-radius: 50;");
        } 
        
        else {
            box.setPrefSize(150, 150); // Increased size for profile boxes
            box.setStyle("-fx-background-color: #d9d9d9; -fx-border-color: #000000; -fx-border-width: 0px; -fx-background-radius: 15; -fx-border-radius: 15;");
        }
        box.setAlignment(javafx.geometry.Pos.CENTER);

        if (avatarImage != null) {
            ImageView imageView = new ImageView(avatarImage);
            if (text.equals("Guest/Private Mode")) {
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
            }
            else if (text.equals("Add Profile")) {
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
            } 
            else {
                imageView.setFitHeight(70); // Increased size for profile images
                imageView.setFitWidth(70);
            }
            box.getChildren().add(imageView);
        }

        Text boxText = new Text(text);
        boxText.setFill(Color.BLACK);
        boxText.setStyle("-fx-font-size: 14;");

        box.getChildren().add(boxText);
        // Add hover effect
        box.setOnMouseEntered(e -> {
            if (text.equals("Guest/Private Mode")) {
                box.setStyle("-fx-cursor: hand; -fx-background-color: #c5c8cc;  -fx-border-width: 0px; -fx-background-radius: 50; -fx-border-radius: 15;");
            } else {
                box.setStyle("-fx-cursor: hand; -fx-background-color: #e6e8e9;-fx-border-width: 0px; -fx-background-radius: 15; -fx-border-radius: 15;");
            }
        });
        box.setOnMouseExited(e -> {
            if (text.equals("Guest/Private Mode")) {
                box.setStyle("-fx-background-color: #c3c5c6; -fx-border-color: #000000; -fx-border-width: 0px; -fx-background-radius: 50; -fx-border-radius: 15;");
            } else {
                box.setStyle("-fx-background-color: #d9d9d9; -fx-border-color: #000000; -fx-border-width: 0px; -fx-background-radius: 15; -fx-border-radius: 15;");
            }
        });
        return box;
    }

    private void openProfileHomePage(Stage primaryStage, int profileId) {
        ProfileHomePage profileHomePage = new ProfileHomePage();
        profileHomePage.start(primaryStage, profileId);
    }

    private void openSignupPopup() {
        Stage signupStage = new Stage();
        signupStage.initModality(Modality.APPLICATION_MODAL);
        signupStage.initStyle(StageStyle.UTILITY);

        VBox vbox = new VBox(10);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setPadding(new Insets(10));

        Text titleText = new Text("Sign Up");
        titleText.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        // Add more UI components for signup form here
        // For example, TextFields for username, email, password, etc.

        vbox.getChildren().add(titleText);

        Scene scene = new Scene(vbox, 300, 200);
        signupStage.setScene(scene);
        signupStage.showAndWait();
    }
    private void openAuthenticationPopup(Stage primaryStage, String username, int profileId) {
        Stage authStage = new Stage();
        authStage.initModality(Modality.APPLICATION_MODAL);
        // authStage.initStyle(StageStyle.UNDECORATED); // Remove default title bar
        authStage.initStyle(StageStyle.TRANSPARENT);


        VBox vbox = new VBox(10);
        vbox.setAlignment(javafx.geometry.Pos.TOP_LEFT); // Align contents to the top center
        vbox.setPadding(new Insets(0,20,10,20));
        vbox.setStyle("-fx-background-color: #395f84; -fx-background-radius: 20;"); // Set background color


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
        HBox.setMargin(closeButton, new Insets(0, (-22), 0, 0));
        VBox.setMargin(titleBar, new Insets((-8), (-0), 0, 0));
        
        VBox titlebox = new VBox(10);
        titlebox.setAlignment(javafx.geometry.Pos.CENTER);
        Text titleText = new Text("Login Authentication");
        titleText.textAlignmentProperty().set(javafx.scene.text.TextAlignment.CENTER);
        titleText.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #ffffff;"); // Set text color
        // VBox.setMargin(titleText, new Insets((-5), 0, 0, 0));
        titlebox.getChildren().addAll(titleText);


        Text usernameLabel = new Text("Username:");
        usernameLabel.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold;");
        VBox.setMargin(usernameLabel, new Insets(25, 0, 0, 0)); // Add top margin
    
        TextField usernameField = new TextField(username);
        usernameField.setEditable(false);
        usernameField.setStyle("-fx-background-radius: 50; -fx-background-color: #9aafc0; -fx-text-fill: #000000;");
        usernameField.setPrefWidth(80); // Set the width
        usernameField.setPrefHeight(45);
        VBox.setMargin(usernameField, new Insets(5, 0, 0, 0)); // Add top margin

        Text passwordLabel = new Text("Password:");
        passwordLabel.setStyle("-fx-font-size: 14; -fx-fill: #ffffff; -fx-font-weight: bold;");
        VBox.setMargin(passwordLabel, new Insets(10, 0, 0, 0)); // Add top margin

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        passwordField.setStyle("-fx-background-radius: 50; -fx-background-color: #9aafc0; -fx-text-fill: #000000;");
        passwordField.setPrefWidth(80); // Set the width
        passwordField.setPrefHeight(45);
        passwordField.setOnMouseEntered(e -> passwordField.setStyle("-fx-background-color: #9aafc0; -fx-text-fill: #000000; -fx-cursor: hand; -fx-background-radius: 45;"));
        passwordField.setOnMouseExited(e -> passwordField.setStyle("-fx-background-color: #9aafc0; -fx-text-fill: #000000; -fx-background-radius: 50;"));
        VBox.setMargin(passwordField, new Insets(5, 0, 0, 0)); // Add top margin

        VBox submitbox = new VBox(10);
        submitbox.setAlignment(javafx.geometry.Pos.CENTER);
        Button submitButton = new Button("Login");
        submitButton.setStyle("-fx-background-color: #e6e8e9; -fx-text-fill: #000000; -fx-background-radius: 50;"); // Set button color and radius
        submitButton.setPrefWidth(120);
        submitButton.setPrefHeight(40);
        VBox.setMargin(submitButton, new Insets(50, 0, 0, 0)); // Add top margin

        submitButton.setOnAction(e -> {
            // Use AuthenticationManager to authenticate
            boolean isAuthenticated = AuthenticationManager.authenticate(username, passwordField.getText());
            if (isAuthenticated) {
                openProfileHomePage(primaryStage, profileId);
                authStage.close();
            } else {
                // Show an error message or handle authentication failure
                System.err.println("Authentication failed.");
            }
        });
        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #dadada; -fx-text-fill: #000000; -fx-cursor: hand; -fx-background-radius: 50;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #e6e8e9; -fx-text-fill: #000000; -fx-background-radius: 50;"));
        submitbox.getChildren().addAll(submitButton);

        vbox.getChildren().addAll(titleBar, titlebox, usernameLabel,usernameField, passwordLabel, passwordField, submitbox);

        Scene scene = new Scene(vbox, 400, 500);
        scene.setFill(Color.TRANSPARENT); // Set the scene fill to transparent
        authStage.setScene(scene);
        authStage.showAndWait();
    }
}