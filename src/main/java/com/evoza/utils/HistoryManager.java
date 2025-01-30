package com.evoza.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.evoza.utils.HistoryEntry;
import com.evoza.utils.DatabaseHelper;
public class HistoryManager {
    private static final String UPSERT_HISTORY = 
        "INSERT INTO histories (profile_id, weburl, title, visited_at) " +
        "VALUES (?, ?, ?, ?) " +
        "ON DUPLICATE KEY UPDATE " +
        "visited_at = VALUES(visited_at), " +
        "title = VALUES(title)";
    
    public static void saveVisit(int profileId, String url, String title) {
        if (profileId <= 0 || url == null || url.isEmpty()) {
            return;
        }

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPSERT_HISTORY)) {
            
            pstmt.setInt(1, profileId);
            pstmt.setString(2, url);
            pstmt.setString(3, title != null ? title : url);
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error saving history: " + e.getMessage());
        }
    }
    public static List<HistoryEntry> getHistory(int profileId) {
    List<HistoryEntry> history = new ArrayList<>();
    String query = "SELECT * FROM histories WHERE profile_id = ? ORDER BY visited_at DESC";
    
    try (Connection conn = DatabaseHelper.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, profileId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            history.add(new HistoryEntry(
                rs.getInt("history_id"),
                rs.getInt("profile_id"),
                rs.getString("weburl"),
                rs.getString("title"),
                rs.getTimestamp("visited_at")
            ));
        }
    } catch (SQLException e) {
        System.err.println("Error fetching history: " + e.getMessage());
    }
    return history;
}

public static void removeHistoryEntry(int historyId) {
    String query = "DELETE FROM histories WHERE history_id = ?";
    try (Connection conn = DatabaseHelper.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, historyId);
        stmt.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Error deleting history: " + e.getMessage());
    }
}
}
