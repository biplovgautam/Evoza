package com.evoza.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProfileManager {
    public static boolean createProfile(String username, String fullname, String email, String password, int profilePicId) {
        String query = "INSERT INTO profiles (username, fullname, email, pass, profile_pic) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, username);
            statement.setString(2, fullname);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.setInt(5, profilePicId);
            statement.executeUpdate();
            System.out.println("Profile created successfully.");
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to create profile.");
            e.printStackTrace();
            return false;
        }
    }
}