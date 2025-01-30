package com.evoza.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TestResult {
    private final StringProperty testName;
    private final StringProperty expected;
    private final StringProperty actual;
    private final StringProperty status;

    public TestResult(String testName, String expected, String actual, String status) {
        this.testName = new SimpleStringProperty(testName);
        this.expected = new SimpleStringProperty(expected);
        this.actual = new SimpleStringProperty(actual);
        this.status = new SimpleStringProperty(status);
    }

    public String getTestName() { return testName.get(); }
    public StringProperty testNameProperty() { return testName; }
    
    public String getExpected() { return expected.get(); }
    public StringProperty expectedProperty() { return expected; }
    
    public String getActual() { return actual.get(); }
    public StringProperty actualProperty() { return actual; }
    public void setActual(String value) { actual.set(value); }
    
    public String getStatus() { return status.get(); }
    public StringProperty statusProperty() { return status; }
    public void setStatus(String value) { status.set(value); }
}