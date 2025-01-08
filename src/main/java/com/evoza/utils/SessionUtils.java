package com.evoza.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class SessionUtils {

    public static boolean isSessionActive(int profileId) {
        try (Connection conn = DatabaseHelper.getConnection()) {
            String query = "SELECT expires_at FROM sessions WHERE profile_id = ? ORDER BY created_at DESC LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, profileId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Timestamp expiresAt = rs.getTimestamp("expires_at");
                if (expiresAt != null && expiresAt.toLocalDateTime().isAfter(LocalDateTime.now())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void saveSession(int profileId) {
        try (Connection conn = DatabaseHelper.getConnection()) {
            String query = "INSERT INTO sessions (profile_id, token, expires_at) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, profileId);
            stmt.setString(2, UUID.randomUUID().toString());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now().plusHours(12)));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}