package com.evoza.ui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import java.util.ArrayList;
import java.util.List;

public class TestResultUI {
    private static TableView<TestResult> tableView;
    private static List<TestResult> testResults = new ArrayList<>();

    public static void showTestResults() {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        
        VBox root = new VBox(10);
        root.setStyle("-fx-background-color: #395f84; -fx-background-radius: 20;");
        root.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("Profile Test Results");
        titleLabel.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-font-weight: bold;");

        // Table
        tableView = new TableView<>();
        tableView.setStyle("-fx-background-color: #ffffff;");

        TableColumn<TestResult, String> testNameCol = new TableColumn<>("Test Case");
        testNameCol.setCellValueFactory(data -> data.getValue().testNameProperty());

        TableColumn<TestResult, String> expectedCol = new TableColumn<>("Expected");
        expectedCol.setCellValueFactory(data -> data.getValue().expectedProperty());

        TableColumn<TestResult, String> actualCol = new TableColumn<>("Actual");
        actualCol.setCellValueFactory(data -> data.getValue().actualProperty());

        TableColumn<TestResult, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> data.getValue().statusProperty());
        statusCol.setCellFactory(col -> new TableCell<TestResult, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle(item.equals("PASSED") ? 
                            "-fx-text-fill: green; -fx-font-weight: bold;" :
                            "-fx-text-fill: red; -fx-font-weight: bold;");
                }
            }
        });

        tableView.getColumns().addAll(testNameCol, expectedCol, actualCol, statusCol);

        // Run Tests button
        Button runButton = new Button("Run Tests");
        runButton.setStyle("-fx-background-color: #e6e8e9; -fx-text-fill: #000000;");
        runButton.setOnAction(e -> runTests());

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #e6e8e9; -fx-text-fill: #000000;");
        closeButton.setOnAction(e -> stage.close());

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(runButton, closeButton);

        root.getChildren().addAll(titleLabel, tableView, buttonBox);

        Scene scene = new Scene(root, 800, 600);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    private static void runTests() {
        tableView.getItems().clear();
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
            .selectors(DiscoverySelectors.selectClass("com.evoza.profile.ProfileTest"))
            .build();

        Launcher launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(new TestExecutionListener() {
            @Override
            public void executionStarted(TestIdentifier testIdentifier) {
                if (testIdentifier.isTest()) {
                    Platform.runLater(() -> {
                        TestResult result = new TestResult(
                            testIdentifier.getDisplayName(),
                            "Should succeed",
                            "Pending...",
                            "RUNNING"
                        );
                        testResults.add(result);
                        tableView.getItems().add(result);
                    });
                }
            }

            @Override
            public void executionFinished(TestIdentifier testIdentifier, 
                org.junit.platform.engine.TestExecutionResult testExecutionResult) {
                if (testIdentifier.isTest()) {
                    Platform.runLater(() -> {
                        TestResult result = testResults.stream()
                            .filter(r -> r.getTestName().equals(testIdentifier.getDisplayName()))
                            .findFirst()
                            .orElse(null);
                        if (result != null) {
                            result.setActual(testExecutionResult.getStatus().toString());
                            result.setStatus(testExecutionResult.getStatus() == 
                                org.junit.platform.engine.TestExecutionResult.Status.SUCCESSFUL ? 
                                "PASSED" : "FAILED");
                            tableView.refresh();
                        }
                    });
                }
            }
        });

        new Thread(() -> launcher.execute(request)).start();
    }
}