package com.evoza.utils;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AvatarFetcher {
    public static Image fetchAvatarById(int avatarId) {
        String query = "SELECT avatar_image FROM avatars WHERE avatar_id = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, avatarId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    byte[] imageBytes = resultSet.getBytes("avatar_image");
                    return new Image(new ByteArrayInputStream(imageBytes));
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch avatar image.");
            e.printStackTrace();
        }
        return null;
    }
}