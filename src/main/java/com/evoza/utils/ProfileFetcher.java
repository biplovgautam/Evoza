package com.evoza.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfileFetcher {
    public static List<Profiles> fetchAllProfiles() {
        List<Profiles> profiles = new ArrayList<>();
        String query = "SELECT * FROM profiles";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                int profileId = resultSet.getInt("profile_id");
                String username = resultSet.getString("username");
                String fullname = resultSet.getString("fullname");
                String email = resultSet.getString("email");
                String password = resultSet.getString("pass");
                int profilePicId = resultSet.getInt("profile_pic");
                Profiles profile = new Profiles(profileId, username, fullname, email, password, profilePicId);
                profiles.add(profile);
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch profiles.");
            e.printStackTrace();
        }
        return profiles;
    }
}