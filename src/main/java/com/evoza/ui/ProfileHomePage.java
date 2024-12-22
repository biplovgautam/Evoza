package com.evoza.ui;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ProfileHomePage {
    public void start(Stage profileStage, int profileId) {
        profileStage.setTitle("Evoza Web Browser - Profile ID: " + profileId);

        BorderPane borderPane = new BorderPane();

        // Add custom title bar
        CustomTitleBar customTitleBar = new CustomTitleBar(profileStage);

        // Create the TabPane
        TabPane tabPane = new TabPane();
        tabPane.getStyleClass().add("tab-pane");

        // Create the toolbar
        HBox toolbar = createToolbar(tabPane);

        // Create the content area
        VBox contentArea = new VBox();
        contentArea.setPadding(new Insets(10));
        contentArea.setStyle("-fx-background-color: #f0f0f0;");

        // Combine custom title bar, tab pane, toolbar, and content area in a VBox
        VBox mainContainer = new VBox();
        mainContainer.getChildren().addAll(customTitleBar, tabPane, toolbar, contentArea);
        borderPane.setCenter(mainContainer);

        // Add the "Add New Tab" button first
        addNewTabButton(tabPane,contentArea);
        addNewTab(tabPane, contentArea);

        Scene scene = new Scene(borderPane, 1024, 768);
        scene.getStylesheets().add(getClass().getResource("/css/newtab.css").toExternalForm());
        profileStage.setScene(scene);
        profileStage.show();
    }
    

    private HBox createToolbar(TabPane tabPane) {
        HBox toolbar = new HBox(10);
        toolbar.setPadding(new Insets(10));
        toolbar.setStyle("-fx-background-color: #ddd;");

        Button backButton = new Button("<");
        Button forwardButton = new Button(">");
        Button refreshButton = new Button("⟳");
        Button homeButton = new Button("🏠");
        TextField searchBar = new TextField();
        searchBar.setPrefWidth(400);
        Button goButton = new Button("Go");
        Button newTabButton = new Button("+");

        backButton.setOnAction(e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab != null) {
                WebView webView = (WebView) selectedTab.getContent();
                WebEngine webEngine = webView.getEngine();
                if (webEngine.getHistory().getCurrentIndex() > 0) {
                    webEngine.getHistory().go(-1);
                }
            }
        });

        forwardButton.setOnAction(e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab != null) {
                WebView webView = (WebView) selectedTab.getContent();
                WebEngine webEngine = webView.getEngine();
                if (webEngine.getHistory().getCurrentIndex() < webEngine.getHistory().getEntries().size() - 1) {
                    webEngine.getHistory().go(1);
                }
            }
        });

        refreshButton.setOnAction(e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab != null) {
                WebView webView = (WebView) selectedTab.getContent();
                webView.getEngine().reload();
            }
        });

        homeButton.setOnAction(e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab != null) {
                WebView webView = (WebView) selectedTab.getContent();
                webView.getEngine().load("https://www.google.com.np");
            }
        });

        goButton.setOnAction(e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab != null) {
                WebView webView = (WebView) selectedTab.getContent();
                webView.getEngine().load(searchBar.getText());
            }
        });

        

        toolbar.getChildren().addAll(backButton, forwardButton, refreshButton, homeButton, searchBar, goButton, newTabButton);

        return toolbar;
    }

    private void addNewTab(TabPane tabPane, VBox contentArea) {
        Tab tab = new Tab("New Tab");

        // Create a custom layout with buttons for favorite pages
        VBox customLayout = new VBox(10);
        customLayout.setPadding(new Insets(10));
        customLayout.setStyle("-fx-background-color: #f0f0f0;");
        
        Button favorite1 = new Button("Google");
        favorite1.setOnAction(e -> loadURLInTab(tab, "https://www.google.com", contentArea));

        Button favorite2 = new Button("YouTube");
        favorite2.setOnAction(e -> loadURLInTab(tab, "https://www.youtube.com", contentArea));

        Button favorite3 = new Button("GitHub");
        favorite3.setOnAction(e -> loadURLInTab(tab, "https://www.github.com", contentArea));

        customLayout.getChildren().addAll(favorite1, favorite2, favorite3);

        tab.setContent(customLayout);
        tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab); // Add the new tab before the "Add New Tab" button
        tabPane.getSelectionModel().select(tab);
    }

    private void loadURLInTab(Tab tab, String url, VBox contentArea) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(url);
    
        //
    
        // Update the content area with the new WebView
        contentArea.getChildren().setAll(webView);
    }
    
    private void addNewTabButton(TabPane tabPane, VBox contentArea) {
        Tab addTab = new Tab();
        addTab.setClosable(false);

        Button addButton = new Button("+");
        addButton.setOnAction(e -> addNewTab(tabPane, contentArea));

        addTab.setGraphic(addButton);
        tabPane.getTabs().add(addTab);
    }
    private String loadTemplateAsDataURL() {
        InputStream inputStream = getClass().getResourceAsStream("/templates/newtabtemplate.html");
        if (inputStream == null) {
            return "data:text/html,<html><body><h1>Template not found</h1></body></html>";
        }
        try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            String content = scanner.useDelimiter("\\A").next();
            return "data:text/html," + content.replace("\n", "").replace("\r", "").replace("\"", "'");
        }
    }
}