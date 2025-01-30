package com.evoza.utils;
import java.sql.Timestamp;

public class HistoryEntry {
    private int historyId;
    private int profileId;
    private String url;
    private String title;
    private Timestamp visitTime;

    public HistoryEntry(int historyId, int profileId, String url, String title, Timestamp visitTime) {
        this.historyId = historyId;
        this.profileId = profileId;
        this.url = url;
        this.title = title;
        this.visitTime = visitTime;
    }

    // Getters
    public int getHistoryId() { return historyId; }
    public int getProfileId() { return profileId; }
    public String getUrl() { return url; }
    public String getTitle() { return title; }
    public Timestamp getVisitTime() { return visitTime; }
}