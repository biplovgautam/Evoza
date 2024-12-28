package com.evoza.utils;


import javafx.stage.Stage;
import com.evoza.ui.BrowserInterface;

public class BrowserUtils {

    public static void openProfileHomePage(Stage primaryStage, int profileId) {
        // Close the current stage
        primaryStage.close();

        // Create a new stage for BrowserInterface
        Stage browserStage = new Stage();

        BrowserInterface profileHomePage = new BrowserInterface();
        profileHomePage.start(browserStage, profileId);
    }
}