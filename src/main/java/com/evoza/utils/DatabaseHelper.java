package com.evoza.utils;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/evoza";
    private static final String USER = "root";
    private static final String PASSWORD = "sabin$2201";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
            return null;
        }
    }

    public static void initializeAvatars() {
        if (!avatarsExist()) {
            uploadAvatars();
        }
    }

    private static boolean avatarsExist() {
        String query = "SELECT COUNT(*) FROM avatars";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("Failed to check if avatars exist.");
            e.printStackTrace();
        }
        return false;
    }

    private static void uploadAvatars() {
    String[] avatarFiles = {
        "dinosaur.png", "dog.png", "bear.png", "hacker.png",
        "panda.png", "cat.png"
    };
    String query = "INSERT INTO avatars (avatar_name, avatar_image) VALUES (?, ?)";
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        String avatarDirectoryPath = getAvatarDirectoryPath();
        if (avatarDirectoryPath == null) {
            System.err.println("Avatar directory path is null.");
            return;
        }
        System.out.println("Avatar directory path: " + avatarDirectoryPath);
        for (String avatarFile : avatarFiles) {
            String filePath = Paths.get(avatarDirectoryPath, avatarFile).toString();
            String avatarName = avatarFile.substring(0, avatarFile.lastIndexOf('.')); // Extract name without extension
            System.out.println("Uploading avatar file: " + filePath + " with name: " + avatarName);
            byte[] imageBytes = Files.readAllBytes(Paths.get(filePath));
            statement.setString(1, avatarName);
            statement.setBytes(2, imageBytes);
            int rowsAffected = statement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        }
        System.out.println("Avatars uploaded successfully.");
    } catch (SQLException e) {
        System.err.println("Failed to upload avatars.");
        e.printStackTrace();
    } catch (IOException e) {
        System.err.println("Failed to read avatar file.");
        e.printStackTrace();
    }
}

    private static String getAvatarDirectoryPath() {
        try {
            URL resource = DatabaseHelper.class.getClassLoader().getResource("images/avatars");
            if (resource == null) {
                return null; // Resource not found
            }
            return Paths.get(resource.toURI()).toString();
        } catch (Exception e) {
            System.err.println("Error resolving avatar directory path.");
            e.printStackTrace();
            return null;
        }
    }
}