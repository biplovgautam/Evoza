package com.evoza.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class CustomTitleBarWithTabs extends HBox {
    private double xOffset = 0;
    private double yOffset = 0;

    private TabPane tabPane;
    private Button newTabButton;

    public CustomTitleBarWithTabs(Stage stage, TabPane tabPane) {
        this.tabPane = tabPane;

        // Load the logo
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/images/icons/logo.png")));
        logo.setFitHeight(20);
        logo.setFitWidth(20);

        Label title = new Label("Evoza");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        newTabButton = new Button("+");
        newTabButton.setTooltip(new Tooltip("New Tab"));
        newTabButton.setOnAction(e -> addNewTab());

        Button minimizeButton = new Button("_");
        minimizeButton.setTooltip(new Tooltip("Minimize"));
        minimizeButton.setOnAction(e -> stage.setIconified(true));

        Button closeButton = new Button("X");
        closeButton.setTooltip(new Tooltip("Close"));
        closeButton.setOnAction(e -> stage.close());

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        setAlignment(Pos.CENTER_LEFT);
        setSpacing(10);
        setStyle("-fx-background-color: #333;");
        getChildren().addAll(logo, title, tabPane, newTabButton, spacer, minimizeButton, closeButton);

        // Allow dragging the window
        setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    private void addNewTab() {
        Tab tab = new Tab("New Tab");
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("https://www.google.com.np");

        tab.setContent(webView);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }
}