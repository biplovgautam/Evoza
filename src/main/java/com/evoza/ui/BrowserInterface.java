package com.evoza.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.web.WebHistory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import com.evoza.EvozaApp;
import com.evoza.ui.CustomHomepageTemp;
import com.evoza.utils.BookmarkUtils;
import com.evoza.utils.ProfileManager;
import com.evoza.utils.SessionUtils;

import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrowserInterface {
    private List<Button> tabButtons = new ArrayList<>();
    private List<Rectangle> verticalLines = new ArrayList<>();
    private StackPane contentArea = new StackPane();
    private TextField searchBar;
    private int activeTabIndex = -1;
    private int tabCount = 0;
    private static final int MAX_TABS = 6;
    private int profileId;

    

    public void start(Stage profileStage, int profileId) {
        this.profileId = profileId;



        profileStage.setTitle("Evoza Web Browser");

        BorderPane borderPane = new BorderPane();

        // Add custom title bar with tabs
        profileStage.initStyle(StageStyle.UNDECORATED); // Remove default title bar
        HBox customTitleBar = CustomTitleBarWithTabs(profileStage);

        // Create the toolbar
        HBox toolbar = createToolbar(profileStage);

        // Combine custom title bar, toolbar, and content area in a VBox
        VBox mainContainer = new VBox();
        VBox.setVgrow(contentArea, Priority.ALWAYS); // Ensure the content area expands to fill the available space
        mainContainer.getChildren().addAll(customTitleBar, toolbar, contentArea);
        borderPane.setCenter(mainContainer);
        borderPane.setStyle("-fx-background-color: transparent; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-width: 0;");


        Scene scene = new Scene(borderPane, 1024, 768);
        profileStage.setMaximized(true); // Open in maximized mode
        profileStage.setScene(scene);
        // Set the application icon
        // it is still not working
        
        InputStream iconStream = getClass().getResourceAsStream("/images/icons/logo.png");
        if (iconStream == null) {
            System.err.println("Icon resource not found!");
        } else {
            System.out.println("Icon resource found!");
            profileStage.getIcons().add(new Image(iconStream));
        }
        profileStage.show();


        // Automatically open one tab when the profile home page is opened
        HBox tabButtonsArea = (HBox) customTitleBar.lookup("#tabButtonsArea");
        if (tabButtonsArea != null) {
            System.out.println("one tab opned automatically");
            addNewTab(tabButtonsArea);
        } else {
            System.err.println("Error: #tabButtonsArea not found in customTitleBar");
        }
        
    }

    private HBox createToolbar(Stage BrowserStage) {
        HBox toolbar = new HBox(10);
        toolbar.setPadding(new Insets(10));
        toolbar.setStyle("-fx-background-color: #d9d9d9;");

        Image profileIcon = new Image(getClass().getResourceAsStream("/images/icons/user.png"), 22, 22, true, true);
        Button profileButton = createIconButton(profileIcon, "profile", 20, 20);
        profileButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;");
        profileButton.setOnMouseEntered(e -> profileButton.setStyle("-fx-background-color:rgba(85, 85, 85, 0.33); -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        profileButton.setOnMouseExited(e -> profileButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        
        
        ContextMenu profileMenu = new ContextMenu();
        MenuItem profileMenuItem = new MenuItem("Profile");
        MenuItem logoutMenuItem = new MenuItem("Logout");

        profileMenuItem.setOnAction(e -> {
            // Open profile settings or page
            System.out.println("Profile clicked");
        });

        logoutMenuItem.setOnAction(e -> {
            // Clear session and redirect to login page
            CustomPopupAlert.showConfirmation("Are you sure you want to logout this profile?", confirmed -> {
                    if (confirmed) {
                        SessionUtils.clearSession(profileId);
                        BrowserStage.close();
                        EvozaApp.openLandingPage();
                    }
                });
            
        });

        profileMenu.getItems().addAll(profileMenuItem, logoutMenuItem);
        profileButton.setOnAction(e -> profileMenu.show(profileButton, javafx.geometry.Side.BOTTOM, 0, 0));

        Image backIcon = new Image(getClass().getResourceAsStream("/images/icons/back.png"), 25, 25, true, true);
        Button backButton = createIconButton(backIcon, "Back", 20, 20);
        backButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color:rgba(85, 85, 85, 0.33); -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        
        Image forwardIcon = new Image(getClass().getResourceAsStream("/images/icons/next.png"), 25, 25, true, true);
        Button forwardButton = createIconButton(forwardIcon, "Forward", 20, 20);
        forwardButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;");
        forwardButton.setOnMouseEntered(e -> forwardButton.setStyle("-fx-background-color:rgba(85, 85, 85, 0.33); -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        forwardButton.setOnMouseExited(e -> forwardButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        
        Image refreshIcon = new Image(getClass().getResourceAsStream("/images/icons/refresh.png"), 25, 25, true, true);
        Button refreshButton = createIconButton(refreshIcon, "Refresh", 20, 20);
        refreshButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;");
        refreshButton.setOnMouseEntered(e -> refreshButton.setStyle("-fx-background-color:rgba(85, 85, 85, 0.33); -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        refreshButton.setOnMouseExited(e -> refreshButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        
        Image homeIcon = new Image(getClass().getResourceAsStream("/images/icons/home.png"),25,25,true,true);
        Button homeButton = createIconButton(homeIcon, "Home", 20, 20);
        homeButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;");
        homeButton.setOnMouseEntered(e -> homeButton.setStyle("-fx-background-color:rgba(85, 85, 85, 0.33); -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        homeButton.setOnMouseExited(e -> homeButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));

        Image searchengineIcon = new Image(getClass().getResourceAsStream("/images/icons/Bing.png"), 25, 25, true, true);
        Button searchengineButton = createIconButton(searchengineIcon, "Go", 20, 20);
        searchengineButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;");
        searchengineButton.setOnMouseEntered(e -> searchengineButton.setStyle("-fx-background-color:rgba(85, 85, 85, 0.33); -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        searchengineButton.setOnMouseExited(e -> searchengineButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        


        searchBar = new TextField();
        HBox.setHgrow(searchBar, Priority.ALWAYS);
        searchBar.setPromptText("Search Google or type a URL"); // Set placeholder text
        searchBar.setStyle(
            "-fx-background-color:rgb(205, 205, 205); " +
            "-fx-text-fill: #000; " +
            "-fx-font-size: 14px; " +
            "-fx-padding: 5px 10px 5px 15px; " +
            "-fx-border-radius: 50px; " +
            "-fx-background-radius: 50px; " +
            "-fx-border-color:rgb(0, 0, 0); " +
            "-fx-border-width: 1px; " +
            "-fx-prompt-text-fill: rgba(0, 0, 0, 0.62);" // Set placeholder text opacity
        );

        Image goIcon = new Image(getClass().getResourceAsStream("/images/icons/search.png"), 25, 25, true, true);
        Button goButton = createIconButton(goIcon, "Go", 20, 20);
        goButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;");
        goButton.setOnMouseEntered(e -> goButton.setStyle("-fx-background-color:rgba(85, 85, 85, 0.33); -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        goButton.setOnMouseExited(e -> goButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        
        Image bookmarksIcon = new Image(getClass().getResourceAsStream("/images/icons/bookmark-white.png"), 25, 25, true, true);
        Button bookmarksButton = createIconButton(bookmarksIcon, "bookmark", 20, 20);
        bookmarksButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;");
        bookmarksButton.setOnMouseEntered(e -> bookmarksButton.setStyle("-fx-background-color:rgba(85, 85, 85, 0.33); -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        bookmarksButton.setOnMouseExited(e -> bookmarksButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        bookmarksButton.setOnAction(e -> {
            String currentURL = searchBar.getText();
            if (!currentURL.isEmpty()) {
                // BookmarkUtils.saveBookmark(profileId, "Bookmark Title", currentURL);
                // CustomPopupAlert.showNotification("Bookmark saved!");
                BookmarkSaveUI.openbookmarksave(BrowserStage, profileId, currentURL);
            }
        });
        
        Rectangle verticalLine = new Rectangle();
        verticalLine.setWidth(2); // Set the width of the line
        verticalLine.setHeight(26); // Set the height of the line to match the tab button height
        verticalLine.setArcWidth(5); // Set the arc width for rounded corners
        verticalLine.setArcHeight(8); // Set the arc height for rounded corners
        verticalLine.setFill(Color.rgb(0, 0, 10, 0.8));
        HBox.setMargin(verticalLine, new Insets(0, 5, 0, 15)); // Adjust margins as needed (top, right, bottom, left)




        Image downloadsIcon = new Image(getClass().getResourceAsStream("/images/icons/download.png"), 25, 25, true, true);
        Button downloadsButton = createIconButton(downloadsIcon, "Downloads", 20, 20);
        downloadsButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;");
        downloadsButton.setOnMouseEntered(e -> downloadsButton.setStyle("-fx-background-color:rgba(85, 85, 85, 0.33); -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        downloadsButton.setOnMouseExited(e -> downloadsButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));

        Image optionsIcon = new Image(getClass().getResourceAsStream("/images/icons/settings.png"), 25, 25, true, true);
        Button optionsButton = createIconButton(optionsIcon, "Options", 18, 20);
        optionsButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;");
        optionsButton.setOnMouseEntered(e -> optionsButton.setStyle("-fx-background-color:rgba(85, 85, 85, 0.33); -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        optionsButton.setOnMouseExited(e -> optionsButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        
        // Create context menu for options button
        ContextMenu optionsMenu = new ContextMenu();
        MenuItem historyMenuItem = new MenuItem("History");
        MenuItem bookmarksMenuItem = new MenuItem("Bookmarks");

        historyMenuItem.setOnAction(e -> {
            // Open history page or dialog
            System.out.println("History clicked");
        });

        bookmarksMenuItem.setOnAction(e -> {
            // Show bookmarks
            BookmarkShowUI.openbookmarkshow(BrowserStage, profileId);
        });

        optionsMenu.getItems().addAll(historyMenuItem, bookmarksMenuItem);
        optionsButton.setOnAction(e -> optionsMenu.show(optionsButton, javafx.geometry.Side.BOTTOM, 0, 0));


        backButton.setOnAction(e -> navigateBack());
        forwardButton.setOnAction(e -> navigateForward());
        refreshButton.setOnAction(e -> refreshPage());
        homeButton.setOnAction(e -> loadHomePage());
        searchBar.setOnAction(e -> {
            String url = searchBar.getText();
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            loadURL(url);
        });
        goButton.setOnAction(e -> loadURL(searchBar.getText()));

        toolbar.getChildren().addAll(profileButton,backButton, forwardButton, refreshButton, homeButton, searchengineButton,searchBar, goButton,bookmarksButton,verticalLine,downloadsButton,optionsButton);

        return toolbar;
    }

    private void addNewTab(HBox tabButtonsArea) {
        if (tabCount >= MAX_TABS) {
            CustomPopupAlert.showNotification("Maximum number of tabs reached.");
            return;
        }
    
        tabCount++;
        Button tabButton = new Button();
        tabButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #000000; -fx-font-size: 14px; -fx-padding: 5px 10px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-cursor: hand;");
        tabButton.setPrefWidth(140); // Increase the width
    
        // Create a close button
        Button closeButton = new Button("x");
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #000000; -fx-font-size: 12px; -fx-cursor: hand;");
        closeButton.setOnAction(e -> closeTab(tabButton, tabButtonsArea));
    
        // Create an HBox to contain the favicon, tab label, and the close button
        HBox tabContent = new HBox();
        tabContent.setAlignment(Pos.CENTER_LEFT);
        tabContent.setSpacing(2); // Space between favicon, label, and close button
    
        // Create an ImageView for the favicon
        ImageView faviconView = new ImageView();
        faviconView.setFitHeight(16);
        faviconView.setFitWidth(16);
    
        // Add the tab label and the close button to the HBox
        Label tabLabel = new Label("New Tab");
        tabLabel.setStyle("-fx-text-fill: #000000; -fx-font-size: 14px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        tabContent.getChildren().addAll(faviconView, tabLabel,spacer, closeButton);
    
        // Set the HBox as the graphic for the tab button
        tabButton.setGraphic(tabContent);
    
        // Create a new WebView
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
            
        // Set user-agent to mimic a modern browser
        webEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
            
        // Enable JavaScript
        webEngine.setJavaScriptEnabled(true);
            
        

        // List<String> bookmarks = Arrays.asList("https://www.google.com", "https://www.bing.com");
        // List<String> favoritePages = Arrays.asList("https://www.youtube.com", "https://www.instagram.com");
        // List<String> recentPages = Arrays.asList("https://www.recent.com", "https://www.anotherrecent.com");
        // String homePageContent = CustomHomepageTemp.generateHomePageContent(bookmarks, favoritePages, recentPages);
        
        // // Load the generated HTML content into the WebView
        // webEngine.loadContent(homePageContent);
            
        // Load a website
        // yahoo, google, bing, 
        webEngine.load("https://www.bing.com");
    
        // Add a listener to update the tab label and favicon when the page title changes
        webEngine.titleProperty().addListener((obs, oldTitle, newTitle) -> {
            tabLabel.setText(newTitle);
        });
    
        webEngine.locationProperty().addListener((obs, oldLocation, newLocation) -> {
            if (tabButton.equals(tabButtons.get(activeTabIndex))) {
                searchBar.setText(newLocation);
            }
            updateFavicon(faviconView, newLocation);
        });
    
        // Add event handlers for the tab button
        tabButton.setOnAction(e -> switchToTab(tabButton));

        // Add the tab button to the list and the tab buttons area
        tabButtons.add(tabButton);
        tabButtonsArea.getChildren().add(tabButtonsArea.getChildren().size() - 1, tabButton); // Add before the "+" button

        addVerticalLine(tabButtonsArea);

        // Add the WebView to the content area and switch to the new tab
        contentArea.getChildren().add(webView);
        switchToTab(tabButton);
        
    }
    private void addVerticalLine(HBox tabButtonsArea) {
        Rectangle verticalLine = new Rectangle();
        verticalLine.setWidth(2); // Set the width of the line
        verticalLine.setHeight(20); // Set the height of the line to match the tab button height
        verticalLine.setArcWidth(5); // Set the arc width for rounded corners
        verticalLine.setArcHeight(5); // Set the arc height for rounded corners
        verticalLine.setFill(Color.rgb(217, 217, 217, 0.8)); // Set the color and opacity
        
        // Add margin to center it vertically
        HBox.setMargin(verticalLine, new Insets(5, 0, 0, 0)); // Adjust margins as needed
        verticalLines.add(verticalLine); // Add the vertical line to the list
        tabButtonsArea.getChildren().add(tabButtonsArea.getChildren().size() - 1, verticalLine);

    }
    
    
    private void updateFavicon(ImageView faviconView, String url) {
        try {
            URI baseUri = new URI(url);
            String faviconUrl = baseUri.getScheme() + "://" + baseUri.getHost() + "/favicon.ico";
            Image favicon = new Image(faviconUrl, true); // Load image directly
            faviconView.setImage(favicon);
        } catch (Exception e) {
            e.printStackTrace();
            Image webIcon = new Image(getClass().getResourceAsStream("/images/icons/web.png"), 25, 25, true, true);
            faviconView.setImage(webIcon); // Fallback to a default image
        }
    }
    
    private void switchToTab(Button tabButton) {
        int index = tabButtons.indexOf(tabButton);
        if (index >= 0 && index < contentArea.getChildren().size()) {
            contentArea.getChildren().forEach(node -> node.setVisible(false));
            contentArea.getChildren().get(index).setVisible(true);
    
            // Update the toolbar with the current URL of the active tab
            WebView webView = (WebView) contentArea.getChildren().get(index);
            WebEngine webEngine = webView.getEngine();
            searchBar.setText(webEngine.getLocation());
    
            // Set the background color of the active tab
            tabButtons.forEach(button -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: #000000; -fx-font-size: 14px; -fx-padding: 5px 10px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-cursor: hand;"));
            tabButton.setStyle("-fx-background-color: #d9d9d9; -fx-text-fill: #000000; -fx-font-size: 14px; -fx-padding: 5px 10px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-cursor: hand;");
    
            // Set the active tab index
            activeTabIndex = index;
    
            // Focus the tab button
            tabButton.requestFocus();
            System.out.println("Tab button at index " + index + " is focused.");
        }
    }
    

    // this is just removing the tab button and the content area from displaying but this is not actually closing the webview for that tab
    private void closeTab(Button tabButton, HBox tabButtonsArea) {
        int index = tabButtons.indexOf(tabButton);
        if (index >= 0) {
            tabButtons.remove(index);
            contentArea.getChildren().remove(index);
            tabButtonsArea.getChildren().remove(tabButton);
            tabCount--;
    
            // Remove the corresponding vertical line
            if (index < verticalLines.size()) {
                Rectangle verticalLine = verticalLines.remove(index);
                tabButtonsArea.getChildren().remove(verticalLine);
            }
            if (tabCount == 0) {
                addNewTab(tabButtonsArea);
            } else {
                // Switch to the previous tab if available
                if (index > 0) {
                    switchToTab(tabButtons.get(index - 1));
                } else if (!tabButtons.isEmpty()) {
                    switchToTab(tabButtons.get(0));
                }
            }
        }
    }

    private WebView getCurrentWebView() {
        if (activeTabIndex >= 0 && activeTabIndex < contentArea.getChildren().size()) {
            System.out.println("Returning WebView at activeTabIndex: " + activeTabIndex); // Debugging: Print the activeTabIndex
            return (WebView) contentArea.getChildren().get(activeTabIndex);
        }
        System.out.println("No active tab found."); // Debugging: Print if no active tab is found
        return null;
    }

    private void navigateBack() {
        WebView webView = getCurrentWebView();
        
        // Debugging: Log the WebView state.
        if (webView != null) {
            WebEngine webEngine = webView.getEngine();
            
            // Debugging: Check if the WebEngine is properly initialized.
            if (webEngine == null) {
                System.out.println("WebEngine is null");
                return;
            }
    
            // Debugging: Log the history state.
            int currentIndex = webEngine.getHistory().getCurrentIndex();
            int historySize = webEngine.getHistory().getEntries().size();
            System.out.println("Current history index: " + currentIndex);
            System.out.println("History size: " + historySize);
            
            // If history exists and can go back, do it.
            if (currentIndex > 0) {
                System.out.println("Navigating back...");
                webEngine.getHistory().go(-1);
            } else {
                // add the template of home page if there is no going back
                CustomPopupAlert.showNotification("No history to go back.");
                System.out.println("No history to go back.");
            }
        } else {
            System.out.println("WebView is null");
        }
    }
    
    

    private void navigateForward() {
        WebView webView = getCurrentWebView();
        if (webView != null) {
            WebEngine webEngine = webView.getEngine();
            // Check if there is a next entry in the history
            if (webEngine.getHistory().getCurrentIndex() < webEngine.getHistory().getEntries().size() - 1) {
                System.out.println("Navigating forward");
                webEngine.getHistory().go(1); // Move forward in history by 1 entry
            } else {
                CustomPopupAlert.showNotification("No history to go forward.");
                System.out.println("No history to go forward");
            }
        }
    }
    

    private void refreshPage() {
        WebView webView = getCurrentWebView();
        if (webView != null) {
            webView.getEngine().reload();
        }
    }

    private void loadHomePage() {
        WebView webView = getCurrentWebView();
        if (webView != null) {
            WebHistory history = webView.getEngine().getHistory();
            if (!history.getEntries().isEmpty()) {
                history.go(-history.getCurrentIndex());
            } else {
                webView.getEngine().load("https://www.google.com");
            }
        }
    }

    private void loadURL(String url) {
        WebView webView = getCurrentWebView();
        if (webView != null) {
            webView.getEngine().load(url);
        }
    }

    

    private HBox CustomTitleBarWithTabs(Stage stage) {
        HBox titleBar = new HBox();
        titleBar.setPadding(new Insets(01));
        titleBar.setStyle("-fx-background-color: #9aafc0; -fx-text-fill: #fff;");

        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/images/icons/logo.png")));
        logo.setFitHeight(28);
        logo.setFitWidth(28);
        HBox.setMargin(logo, new Insets(2, 0, 0, 2));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Create the tab buttons area
        HBox tabButtonsArea = new HBox(10);
        tabButtonsArea.setId("tabButtonsArea"); // Set an ID for lookup
        tabButtonsArea.setPadding(new Insets(0,5,(-5),5)); // Reduce padding
        HBox.setMargin(tabButtonsArea, new Insets(3, 0, 0, 0)); // Margin: top, right, bottom, left
        tabButtonsArea.setPrefHeight(15); // Set a fixed height


        
        // Add the "Add New Tab" button
        Image addtabIcon = new Image(getClass().getResourceAsStream("/images/icons/plus.png"), 25, 25, true, true);
        // Button addTabButton = new Button("+");
        Button addTabButton = createIconButton(addtabIcon, "Back", 10, 10);
        addTabButton.setOnAction(e -> addNewTab(tabButtonsArea));
        tabButtonsArea.getChildren().add(addTabButton);
        addTabButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;");
        addTabButton.setOnMouseEntered(e -> addTabButton.setStyle("-fx-background-color:rgba(85, 85, 85, 0.33); -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));
        addTabButton.setOnMouseExited(e -> addTabButton.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-border-radius: 50px; -fx-background-radius: 50px;"));

        // Load button icons
        Image minimizeIcon = new Image(getClass().getResourceAsStream("/images/icons/minimizeb.png"), 25, 25, true, true);
        Image maximizeIcon = new Image(getClass().getResourceAsStream("/images/icons/maximizeb.png"), 25, 25, true, true);
        Image restoreIcon = new Image(getClass().getResourceAsStream("/images/icons/squares.png"), 25, 25, true, true);
        Image closeIcon = new Image(getClass().getResourceAsStream("/images/icons/closeb.png"), 25, 25, true, true);

        // Create buttons with icons
        Button minimizeButton = createIconButton(minimizeIcon, "Minimize", 20, 20);
        minimizeButton.setOnAction(e -> stage.setIconified(true));

        Button maximizeButton = createIconButton(maximizeIcon, "Maximize", 12, 12); // Adjusted size
        maximizeButton.setOnAction(e -> stage.setMaximized(!stage.isMaximized()));

        Button closeButton = createIconButton(closeIcon, "Close", 12, 12);
        closeButton.setOnAction(e -> stage.close());

        // Add listener to change maximize button icon
        ImageView maximizeImageView = new ImageView(maximizeIcon);
        ImageView restoreView = new ImageView(restoreIcon);
        stage.maximizedProperty().addListener((obs, wasMaximized, isNowMaximized) -> {
            if (isNowMaximized) {
                maximizeButton.setGraphic(restoreView);
                restoreView.setFitHeight(12); // Set height for restore down icon
                restoreView.setFitWidth(12);  // Set width for restore down icon
                maximizeButton.setTooltip(new Tooltip("Restore Down"));
            } else {
                maximizeButton.setGraphic(maximizeImageView);
                maximizeImageView.setFitHeight(12); // Set height for maximize icon
                maximizeImageView.setFitWidth(12);  // Set width for maximize icon
                maximizeButton.setTooltip(new Tooltip("Maximize"));
            }
        });

        titleBar.getChildren().addAll(logo, tabButtonsArea, spacer, minimizeButton, maximizeButton, closeButton);
        return titleBar;
    }

    private Button createIconButton(Image icon, String tooltipText, double width, double height) {
        ImageView imageView = new ImageView(icon);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        
        Button button = new Button();
        button.setGraphic(imageView);
        button.setTooltip(new Tooltip(tooltipText));
        button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        button.setMinSize(30, 30);
        
        return button;
    }


}