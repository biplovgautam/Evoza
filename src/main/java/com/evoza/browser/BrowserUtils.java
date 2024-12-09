package com.evoza.browser;

import javafx.stage.Stage;
import com.evoza.ui.ProfileHomePage;

public class BrowserUtils {

    public static void openProfileHomePage(Stage primaryStage, int profileId) {
        ProfileHomePage profileHomePage = new ProfileHomePage();
        profileHomePage.start(primaryStage, profileId);
    }
}