package com.evoza.utils;

import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/evoza";
    private static final String USER = "root";
    private static final String PASSWORD = "admin123";

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
            "user.png", "home.png", "work.png", "school.png",
            "dinosaur.png", "dog.png", "bear.png", "hacker.png",
            "panda.png", "cat.png"
        };
        String query = "INSERT INTO avatars (avatar_image) VALUES (LOAD_FILE(?))";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            String avatarDirectoryPath = getAvatarDirectoryPath();
            if (avatarDirectoryPath == null) {
                System.err.println("Avatar directory path is null.");
                return;
            }
            for (String avatarFile : avatarFiles) {
                statement.setString(1, Paths.get(avatarDirectoryPath, avatarFile).toString());
                statement.executeUpdate();
            }
            System.out.println("Avatars uploaded successfully.");
        } catch (SQLException e) {
            System.err.println("Failed to upload avatars.");
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