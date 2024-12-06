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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

        Button minimizeButton = createButton("-", "Minimize");
        minimizeButton.setOnAction(e -> stage.setIconified(true));

        Button maximizeButton = createButton("[]", "Maximize");
        maximizeButton.setOnAction(e -> stage.setMaximized(!stage.isMaximized()));

        Button closeButton = createButton("X", "Close");
        closeButton.setOnAction(e -> stage.close());

        HBox rightBox = new HBox(minimizeButton, maximizeButton, closeButton);
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        rightBox.setSpacing(10); // Add spacing between buttons

        this.getChildren().addAll(leftBox, rightBox);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-background-color: #131a24; -fx-padding: 5px;");

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

    private Button createButton(String text, String tooltipText) {
        Button button = new Button(text);
        Tooltip tooltip = new Tooltip(tooltipText);
        setTooltipDelay(tooltip, 30); // Set tooltip delay to 30ms
        button.setTooltip(tooltip);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        button.setMinSize(30, 30);
        return button;
    }

    private void setTooltipDelay(Tooltip tooltip, int delay) {
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Method method = fieldTimer.getType().getDeclaredMethod("setDelay", int.class);
            method.setAccessible(true);
            method.invoke(fieldTimer, delay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}