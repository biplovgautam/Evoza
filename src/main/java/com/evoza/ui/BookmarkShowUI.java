package com.evoza.ui;

import com.evoza.utils.BookmarkUtils;
import com.evoza.utils.ProfileManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

public class BookmarkShowUI {
    static VBox bookmarksvbox = new VBox(10);
    static int profileId; 

    public static void openbookmarkshow(Stage parentStage, int profileId) {
        BookmarkShowUI.profileId = profileId;
        Stage bookStage = new Stage();
        bookStage.initModality(Modality.APPLICATION_MODAL);
        bookStage.initStyle(StageStyle.TRANSPARENT);

        VBox vbox = new VBox(10);
        vbox.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        vbox.setPadding(new Insets(0, 20, 10, 20));
        vbox.setStyle("-fx-background-color: #9aafc0; -fx-background-radius: 20;");

        // Custom title bar
        HBox titleBar = new HBox();
        titleBar.setStyle("-fx-background-color: #9aafc0; -fx-padding: 10;");
        titleBar.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 20;");
        closeButton.setOnAction(e -> bookStage.close());
        closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #00000; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 20;"));
        closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 20;"));

        titleBar.getChildren().addAll(closeButton);
        HBox.setMargin(closeButton, new Insets(0, -22, 0, 0));
        VBox.setMargin(titleBar, new Insets(-8, 0, 0, 0));

        VBox titlebox = new VBox(10);
        titlebox.setAlignment(javafx.geometry.Pos.CENTER);
        Text titleText = new Text("Saved Bookmarks");
        titleText.textAlignmentProperty().set(javafx.scene.text.TextAlignment.CENTER);
        titleText.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #ffffff;");
        titlebox.getChildren().addAll(titleText);
        vbox.getChildren().addAll(titleBar,titlebox);

        // VBox bookmarksvbox = new VBox(10);
        bookmarksvbox.setAlignment(Pos.CENTER);
        bookmarksvbox.setStyle("-fx-background-color: #9aafc0;");



        loadbookmarks();



        ScrollPane scrollPane = new ScrollPane(bookmarksvbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #9aafc0;");
        vbox.getChildren().addAll(scrollPane);


        Scene scene = new Scene(vbox, 400, 500);
        scene.setFill(Color.TRANSPARENT);
        bookStage.setScene(scene);
        bookStage.show();
    }


    private static void loadbookmarks(){
        bookmarksvbox.getChildren().clear();
        List<BookmarkUtils.Bookmark> bookmarks = BookmarkUtils.getBookmarks(profileId);
        for (BookmarkUtils.Bookmark bookmark : bookmarks) {
           
            HBox bookmarkButton = createBookmarkHBox(bookmark);
            bookmarksvbox.getChildren().add(bookmarkButton);
        }

    }

    private static HBox createBookmarkHBox(BookmarkUtils.Bookmark bookmark) {
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(10));
        hbox.setStyle("-fx-background-color: #e6e8e9; -fx-background-radius: 10;");

        VBox infoVBox = new VBox(5);
        // Make infoVBox clickable
        infoVBox.setStyle("-fx-cursor: hand;");
        infoVBox.setOnMouseClicked(e -> {
            BrowserInterface.openUrlInNewTab(bookmark.getWeburl());
            // bookStage.close();
        });
        Text titleText = new Text(bookmark.getTitle());
        Text urlText = new Text(bookmark.getWeburl());
        infoVBox.getChildren().addAll(titleText, urlText);

        VBox buttonVBox = new VBox();
        buttonVBox.setAlignment(Pos.CENTER_RIGHT);
        Button optionsButton = new Button("X");
        optionsButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #000000; -fx-cursor: hand;");

        

       
        optionsButton.setOnAction(e -> {
            CustomPopupAlert.showConfirmation("Are you sure you want to remove this bookmark?", confirmed -> {
                    if (confirmed) {
                        BookmarkUtils.removeBookmark(bookmark.getBookmarkId());
                        // logic to reload the boook marks here by removing childs of bookmarks vbox and add them again
                        BookmarkShowUI.loadbookmarks();
                    }
                });
            
            
        });

        buttonVBox.getChildren().add(optionsButton);

        hbox.getChildren().addAll(infoVBox, buttonVBox);
        HBox.setHgrow(infoVBox, Priority.ALWAYS);

        return hbox;
    }
}
