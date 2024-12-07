package com.evoza.ui;

import com.evoza.utils.AvatarFetcher;
import com.evoza.utils.Profile;
import com.evoza.utils.ProfileFetcher;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.List;

public class LandingPageUI {
    public BorderPane start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        HBox mainContainer = new HBox();
        VBox leftContainer = new VBox();
        VBox rightContainer = new VBox(10);

        leftContainer.setPrefWidth(250);
        leftContainer.setMaxWidth(250);
        rightContainer.setPrefWidth(750); // 3/4 of 1000px width

        leftContainer.setStyle("-fx-background-color: #395f84;");
        rightContainer.setStyle("-fx-background-color: #9aafc0;");

        rightContainer.setAlignment(javafx.geometry.Pos.CENTER);
        rightContainer.setPadding(new Insets(10));
        leftContainer.setAlignment(javafx.geometry.Pos.BOTTOM_CENTER);
        leftContainer.setPadding(new Insets(10));

        // Add circular logo image above the title text
        Image logoImage = new Image(getClass().getResourceAsStream("/images/icons/logo.png"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitHeight(100);
        logoImageView.setFitWidth(100);
        Circle clip = new Circle(50, 50, 50);
        logoImageView.setClip(clip);

        Text titleText = new Text("Who's using Evoza?");
        titleText.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #000000;");

        Text descriptionTitle = new Text("Evoza Browser offers a seamless browsing experience with advanced features:\n\n");
        descriptionTitle.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-fill: #515f6a;");

        Text descriptionContent = new Text(
                "- Customizable Interface: Personalize your browser with themes and extensions.\n" +
                "- Multiple Profiles: Separate your browsing activities with multiple profiles.\n" +
                "- Enhanced Security: Protect your data with built-in security features.\n" +
                "- Guest Mode: Browse privately without saving history."
        );
        descriptionContent.setStyle("-fx-font-size: 14; -fx-fill: #515f6a;");

        TextFlow descriptionTextFlow = new TextFlow(descriptionTitle, descriptionContent);
        descriptionTextFlow.setPadding(new Insets(10));
        descriptionTextFlow.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);




        // Create a clickable box for guest mode
        Image userImage = new Image(getClass().getResourceAsStream("/images/icons/user.png"));
        HBox guestModeBox = createClickableBox("Guest/Private Mode", userImage);
        guestModeBox.setOnMouseClicked(e -> openProfileHomePage(primaryStage, -1)); // Use -1 for guest mode
        VBox.setMargin(guestModeBox, new Insets(0, 0, 20, 0)); 
        leftContainer.getChildren().addAll(guestModeBox);


        rightContainer.getChildren().addAll(logoImageView, titleText, descriptionTextFlow);

        // Fetch profiles from the database and add them to the UI
        List<Profile> profiles = ProfileFetcher.fetchAllProfiles();
        for (Profile profile : profiles) {
            Image avatarImage = AvatarFetcher.fetchAvatarById(profile.getProfilePicId());
            HBox profileBox = createClickableBox(profile.getUsername(), avatarImage);
            profileBox.setOnMouseClicked(e -> openProfileHomePage(primaryStage, profile.getProfileId()));
            rightContainer.getChildren().add(profileBox);
        }

        HBox.setHgrow(leftContainer, Priority.ALWAYS);
        HBox.setHgrow(rightContainer, Priority.ALWAYS);

        mainContainer.getChildren().addAll(leftContainer, rightContainer);
        root.setCenter(mainContainer);

        return root;
    }

    private HBox createClickableBox(String text, Image avatarImage) {
        HBox box = new HBox(10);
        if (text.equals("Guest/Private Mode")) {
            box.setPrefSize(70, 50);
            box.setStyle("-fx-background-color: #c5c8cc; -fx-border-color: #000000; -fx-background-radius: 50px; -fx-border-width: 0px;");

        } 
        else {
            box.setPrefSize(100, 100);
            box.setStyle("-fx-background-color: #d9d9d9; -fx-border-color: #000000;  -fx-background-radius: 50px; -fx-border-width: 0px;");

        }
        box.setAlignment(javafx.geometry.Pos.CENTER);

        if (avatarImage != null) {
            ImageView imageView = new ImageView(avatarImage);
            if(text.equals("Guest/Private Mode")) {
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
            } else {
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
            }
            
            box.getChildren().add(imageView);
        }

        Text boxText = new Text(text);
        boxText.setFill(Color.BLACK);
        boxText.setStyle("-fx-font-size: 14;");

        box.getChildren().add(boxText);
        return box;
    }

    private void openProfileHomePage(Stage primaryStage, int profileId) {
        ProfileHomePage profileHomePage = new ProfileHomePage();
        profileHomePage.start(primaryStage, profileId);
    }
}