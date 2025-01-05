package com.evoza.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class CustomTitleBar extends HBox {
    private double xOffset = 0;
    private double yOffset = 0;

    public CustomTitleBar(Stage stage) {
        // Load the logo
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/images/icons/logo.png")));
        logo.setFitHeight(20);
        logo.setFitWidth(20);

        Label title = new Label("Evoza");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        HBox leftBox = new HBox(logo, title);
        leftBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(leftBox, Priority.ALWAYS);

        // Load button icons
        Image minimizeIcon = new Image(getClass().getResourceAsStream("/images/icons/minimize.png"));
        Image maximizeIcon = new Image(getClass().getResourceAsStream("/images/icons/maximize.png"));
        Image restoreIcon = new Image(getClass().getResourceAsStream("/images/icons/reset-down.png"));
        Image closeIcon = new Image(getClass().getResourceAsStream("/images/icons/close1.png"));

        // Create buttons with icons
        Button minimizeButton = createIconButton(minimizeIcon, "Minimize", 20, 20);
        minimizeButton.setOnAction(e -> stage.setIconified(true));

        ImageView maximizeImageView = new ImageView(maximizeIcon);
        Button maximizeButton = createIconButton(maximizeImageView, "Maximize", 14, 14); // Adjusted size
        maximizeButton.setOnAction(e -> stage.setMaximized(!stage.isMaximized()));

        Button closeButton = createIconButton(closeIcon, "Close", 20, 20);
        closeButton.setOnAction(e -> stage.close());

        // Add listener to change maximize button icon
        stage.maximizedProperty().addListener((obs, wasMaximized, isNowMaximized) -> {
            if (isNowMaximized) {
                maximizeImageView.setImage(restoreIcon);
                maximizeImageView.setFitHeight(18); // Set height for restore down icon
                maximizeImageView.setFitWidth(18);  // Set width for restore down icon
                maximizeButton.setTooltip(new Tooltip("Restore Down"));
            } else {
                maximizeImageView.setImage(maximizeIcon);
                maximizeImageView.setFitHeight(14); // Set height for maximize icon
                maximizeImageView.setFitWidth(14);  // Set width for maximize icon
                maximizeButton.setTooltip(new Tooltip("Maximize"));
            }
        });

        HBox rightBox = new HBox(minimizeButton, maximizeButton, closeButton);
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        rightBox.setSpacing(10); // Add spacing between buttons

        this.getChildren().addAll(leftBox, rightBox);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-background-color: #9aafc0; -fx-padding: 2px;");

        // Enable dragging of the window
        this.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        this.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    private Button createIconButton(Image icon, String tooltipText, double width, double height) {
        ImageView imageView = new ImageView(icon);
        return createIconButton(imageView, tooltipText, width, height);
    }

    private Button createIconButton(ImageView imageView, String tooltipText, double width, double height) {
        Button button = new Button();
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        button.setGraphic(imageView);
        button.setTooltip(new Tooltip(tooltipText));
        button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        button.setMinSize(30, 30);
        return button;
    }
}