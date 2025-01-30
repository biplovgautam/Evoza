package com.evoza.ui;

import com.evoza.utils.HistoryManager;
import com.evoza.utils.HistoryEntry;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class HistoryShowUI {
    static VBox historyVBox = new VBox(10);
    static int profileId;

    public static void openHistoryShow(Stage parentStage, int profileId) {
        HistoryShowUI.profileId = profileId;
        Stage historyStage = new Stage();
        historyStage.initModality(Modality.APPLICATION_MODAL);
        historyStage.initStyle(StageStyle.TRANSPARENT);

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(0, 20, 10, 20));
        vbox.setStyle("-fx-background-color: #9aafc0; -fx-background-radius: 20;");

        // Custom title bar
        HBox titleBar = new HBox();
        titleBar.setStyle("-fx-background-color: #9aafc0; -fx-padding: 10;");
        titleBar.setAlignment(Pos.TOP_RIGHT);
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff; -fx-font-size: 14; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 20;");
        closeButton.setOnAction(e -> historyStage.close());

        titleBar.getChildren().add(closeButton);
        HBox.setMargin(closeButton, new Insets(0, -22, 0, 0));

        // Title
        VBox titlebox = new VBox(10);
        titlebox.setAlignment(Pos.CENTER);
        Text titleText = new Text("Browsing History");
        titleText.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #ffffff;");
        titlebox.getChildren().add(titleText);

        historyVBox.setAlignment(Pos.CENTER);
        historyVBox.setStyle("-fx-background-color: #9aafc0;");

        loadHistory( historyStage);

        ScrollPane scrollPane = new ScrollPane(historyVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #9aafc0;");

        vbox.getChildren().addAll(titleBar, titlebox, scrollPane);

        Scene scene = new Scene(vbox, 400, 500);
        scene.setFill(Color.TRANSPARENT);
        historyStage.setScene(scene);
        historyStage.show();
    }

    private static void loadHistory(Stage historyStage) {
        historyVBox.getChildren().clear();
        List<HistoryEntry> history = HistoryManager.getHistory(profileId);
        for (HistoryEntry entry : history) {
            HBox historyItem = createHistoryHBox(entry, historyStage);
            historyVBox.getChildren().add(historyItem);
        }
    }

    private static HBox createHistoryHBox(HistoryEntry entry, Stage historyStage) {
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(10));
        hbox.setStyle("-fx-background-color: #e6e8e9; -fx-background-radius: 10;");
        hbox.setPrefWidth(300); // Set preferred width for the HBox
    
        VBox infoVBox = new VBox(5);
        infoVBox.setPrefWidth(280); // Set width for content
        infoVBox.setStyle("-fx-cursor: hand;");
    
        infoVBox.setOnMouseClicked(e -> {
            BrowserInterface.openUrlInNewTab(entry.getUrl());
            historyStage.close();
        });
        
        Text titleText = new Text(entry.getTitle());
        titleText.setStyle("-fx-font-weight: bold;");
        titleText.setWrappingWidth(280);
    
        Text urlText = new Text(entry.getUrl());
        urlText.setWrappingWidth(280);
        urlText.setStyle("-fx-fill: #395f84;");
    
        Text timeText = new Text(entry.getVisitTime().toString());
        timeText.setStyle("-fx-fill: #666666; -fx-font-size: 11px;");
    
        infoVBox.getChildren().addAll(titleText, urlText, timeText);
    
        Button deleteButton = new Button("X");
        deleteButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #000000; -fx-cursor: hand;");
        deleteButton.setOnAction(e -> {
            CustomPopupAlert.showConfirmation("Delete this history entry?", confirmed -> {
                if (confirmed) {
                    HistoryManager.removeHistoryEntry(entry.getHistoryId());
                    loadHistory(historyStage);
                }
            });
        });
    
        hbox.getChildren().addAll(infoVBox, deleteButton);
        HBox.setHgrow(infoVBox, Priority.ALWAYS);
    
        return hbox;
    }
}