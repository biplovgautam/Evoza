package com.evoza.ui;

import com.evoza.utils.AuthenticationManager;
import com.evoza.utils.AvatarFetcher;
import com.evoza.utils.Profiles;
import com.evoza.utils.ProfileManager;
import com.evoza.utils.ProfileFetcher;
import com.evoza.ui.UserVerificationUI;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
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
import com.evoza.browser.BrowserUtils;


import java.util.List;

public class LandingPageUI {
    private static BorderPane root;
    

    private static GridPane profilesContainer;
    private static VBox rightContainer;
    private static Stage primaryStage; // Define primaryStage as a class variable

    public static BorderPane start(Stage stage) {
        primaryStage = stage; // Initialize the class variable
        root = createRoot(primaryStage);
        return root;
    }

    public static BorderPane createRoot(Stage primaryStage) {
        root = new BorderPane();

        HBox mainContainer = new HBox();
        VBox leftContainer = new VBox();
        rightContainer = new VBox(10);

        // Set fixed width for the left container
        leftContainer.setPrefWidth(250);
        leftContainer.setMaxWidth(250);

        // Use percentage-based width for the right container
        rightContainer.setPrefWidth(750);

        leftContainer.setStyle("-fx-background-color: #395f84;");
        rightContainer.setStyle("-fx-background-color: #9aafc0;");

        // Create a clickable box for guest mode
        HBox guestModeBox = new HBox(10);
        guestModeBox.setPrefSize(150, 50);
        guestModeBox.setStyle("-fx-background-color: #c5c8cc; -fx-border-color: #000000; -fx-border-width: 0px; -fx-background-radius: 50; -fx-border-radius: 50;");
        guestModeBox.setAlignment(javafx.geometry.Pos.CENTER);
        Image userImage = new Image(LandingPageUI.class.getResourceAsStream("/images/icons/user.png"));
        ImageView guestuserimageView = new ImageView(userImage);
        guestuserimageView.setFitHeight(30);
        guestuserimageView.setFitWidth(30);
        guestModeBox.getChildren().add(guestuserimageView);
        VBox.setMargin(guestModeBox, new Insets(0, 0, 20, 0)); // Margin at the bottom
        Text guestText = new Text("Guest/Private Mode");
        guestText.setFill(Color.BLACK);
        guestText.setStyle("-fx-font-size: 14;");
        guestModeBox.getChildren().add(guestText);
        guestModeBox.setOnMouseEntered(e -> {guestModeBox.setStyle("-fx-cursor: hand; -fx-background-color: #c5c8cc;  -fx-border-width: 0px; -fx-background-radius: 50; -fx-border-radius: 15;");});
        guestModeBox.setOnMouseExited(e -> {guestModeBox.setStyle("-fx-background-color: #c3c5c6; -fx-border-color: #000000; -fx-border-width: 0px; -fx-background-radius: 50; -fx-border-radius: 15;");});
        guestModeBox.setOnMouseClicked(e -> BrowserUtils.openProfileHomePage(primaryStage, -1));

        leftContainer.getChildren().addAll(guestModeBox);


        rightContainer.setAlignment(javafx.geometry.Pos.CENTER);
        rightContainer.setPadding(new Insets(10));
        leftContainer.setAlignment(javafx.geometry.Pos.BOTTOM_CENTER);
        leftContainer.setPadding(new Insets(10));

        // Create containers for logo, title, description, and profiles
        VBox logoTitleDescriptionContainer = new VBox(10);
        logoTitleDescriptionContainer.setAlignment(javafx.geometry.Pos.CENTER); // Center the contents
        profilesContainer = new GridPane();
        profilesContainer.setHgap(10);
        profilesContainer.setVgap(10);
        profilesContainer.setAlignment(javafx.geometry.Pos.CENTER);

        // Add circular logo image above the title text
        Image logoImage = new Image(LandingPageUI.class.getResourceAsStream("/images/icons/logo.png"));
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

      

        // Add containers to the rightContainer
        rightContainer.getChildren().addAll(logoTitleDescriptionContainer);
        loadProfiles();

        HBox.setHgrow(leftContainer, Priority.NEVER);
        HBox.setHgrow(rightContainer, Priority.ALWAYS);

        mainContainer.getChildren().addAll(leftContainer, rightContainer);
        root.setCenter(mainContainer);


        return root;
    }

    public static void loadProfiles(){
        // Clear existing profiles
        profilesContainer.getChildren().clear();

        // Fetch profiles from the database and add them to the profilesContainer
        List<Profiles> profiles = ProfileFetcher.fetchAllProfiles();
        int column = 0;
        int row = 0;
        for (Profiles profile : profiles) {
            Image avatarImage = AvatarFetcher.fetchAvatarById(profile.getProfilePicId());
            HBox profileBox = createClickableBox(profile.getUsername(), avatarImage);
            profileBox.setOnMouseClicked(e -> openAuthenticationPopup(primaryStage,profile.getUsername(), profile.getEmail(), profile.getProfileId()));
            profilesContainer.add(profileBox, column, row);
            column++;
            if (column == 5) { // Change this value to set the number of columns
                column = 0;
                row++;
            }
        }

        // Add a clickable box for adding a new profile
        Image adduser = new Image(LandingPageUI.class.getResourceAsStream("/images/icons/add-user.png"));
        HBox addProfileBox = createClickableBox("Add Profile", adduser);
        addProfileBox.setOnMouseClicked(e -> openSignupPopup(primaryStage));
        profilesContainer.add(addProfileBox, column, row);

        // Ensure profilesContainer is only added once
        if (!rightContainer.getChildren().contains(profilesContainer)) {
            rightContainer.getChildren().add(profilesContainer);
        }

    }




    public static HBox createClickableBox(String text, Image avatarImage) {
        HBox box = new HBox();
        box.setPrefSize(150, 150); // Increased size for profile boxes
        box.setStyle("-fx-background-color: #d9d9d9; -fx-border-color: #000000; -fx-border-width: 0px; -fx-background-radius: 15; -fx-border-radius: 15;");
        box.setAlignment(javafx.geometry.Pos.CENTER);
    
        VBox vbox = new VBox(10);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
    
        if (!text.equals("Add Profile")) {
            Button editButton = new Button("...");
            editButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #000000; -fx-font-size: 14; -fx-cursor: hand;");
            ContextMenu contextMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("Edit");
            MenuItem removeItem = new MenuItem("Remove");
            removeItem.setOnAction(e -> {
                ProfileManager.deleteProfile(text);
                // logic to update the UI after deletion
                loadProfiles();
            });
            contextMenu.getItems().addAll(editItem, removeItem);
            editButton.setOnMouseClicked(e -> {contextMenu.show(editButton, e.getScreenX(), e.getScreenY());});
            HBox topRightBox = new HBox(editButton);
            topRightBox.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
            topRightBox.setPadding(new Insets(-15, -35, 0, 0)); // Add top-right margin
            vbox.getChildren().add(topRightBox);
        }
    
        if (avatarImage != null) {
            ImageView imageView = new ImageView(avatarImage);
            if (text.equals("Add Profile")) {
                imageView.setFitHeight(40);
                imageView.setFitWidth(40);
            } else {
                imageView.setFitHeight(70); // Increased size for profile images
                imageView.setFitWidth(70);
            }
            vbox.getChildren().add(imageView);
        }
    
        Text boxText = new Text(text);
        boxText.setFill(Color.BLACK);
        boxText.setStyle("-fx-font-size: 14;");
        vbox.getChildren().add(boxText);
    
        box.getChildren().add(vbox);
    
        // Add hover effect
        box.setOnMouseEntered(e -> {
            box.setStyle("-fx-cursor: hand; -fx-background-color: #e6e8e9;-fx-border-width: 0px; -fx-background-radius: 15; -fx-border-radius: 15;");
        });
        box.setOnMouseExited(e -> {
            box.setStyle("-fx-background-color: #d9d9d9; -fx-border-color: #000000; -fx-border-width: 0px; -fx-background-radius: 15; -fx-border-radius: 15;");
        });
        return box;
    }

    

    public static void openSignupPopup(Stage primaryStage) {
        SignupAuthenticationUI.openSignupPopup(primaryStage);
    }
    public static void openAuthenticationPopup(Stage primaryStage, String username, String email, int profileId) {
        if (!ProfileManager.isUserActive(username)) {
            UserVerificationUI.openVerificationPopup(primaryStage, username, email,profileId);
            return;
        }
        else{
        LoginAuthenticationUI.openAuthenticationPopup(primaryStage, username, email, profileId);
        }
    }
}