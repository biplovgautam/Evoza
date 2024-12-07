package com.evoza.utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationManager {
    public static boolean authenticate(String username, String password) {
        String query = "SELECT * FROM profiles WHERE username = ? AND pass = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                boolean isAuthenticated = resultSet.next();
                if (isAuthenticated) {
                    System.out.println("Authentication successful.");
                } else {
                    System.err.println("Authentication failed.");
                }
                return isAuthenticated;
            }
        } catch (SQLException e) {
            System.err.println("Failed to authenticate.");
            e.printStackTrace();
            return false;
        }
    }
}