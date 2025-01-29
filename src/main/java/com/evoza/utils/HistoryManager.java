package com.evoza.utils;

public class HistoryManager {
    private static final String INSERT_HISTORY = "INSERT INTO history (profile_id, url, title, visit_time) VALUES (?, ?, ?, ?)";
    
    public static void saveVisit(int profileId, String url, String title) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_HISTORY)) {
            
            pstmt.setInt(1, profileId);
            pstmt.setString(2, url);
            pstmt.setString(3, title);
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}