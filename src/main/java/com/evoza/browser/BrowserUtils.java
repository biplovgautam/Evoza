package com.evoza.browser;

import javafx.stage.Stage;
import com.evoza.ui.BrowserInterface;

public class BrowserUtils {

    public static void openProfileHomePage(Stage primaryStage, int profileId) {
        BrowserInterface profileHomePage = new BrowserInterface();
        profileHomePage.start(primaryStage, profileId);
    }
}