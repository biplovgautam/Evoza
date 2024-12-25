package com.evoza.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class searchEngine {

    // Fetch the search engine of a profile
    public static String getSearchEngine(int profileId) {
        String query = "SELECT engine FROM searchengine WHERE profile_id = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, profileId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("engine");
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch the search engine.");
            e.printStackTrace();
        }
        return null;
    }

    // Update the search engine of a profile
    public static boolean updateSearchEngine(int profileId, String newEngine) {
        String query = "UPDATE searchengine SET engine = ? WHERE profile_id = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newEngine);
            statement.setInt(2, profileId);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Failed to update the search engine.");
            e.printStackTrace();
        }
        return false;
    }
}