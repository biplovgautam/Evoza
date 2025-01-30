package com.evoza.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.evoza.ui.CustomPopupAlert;

public class ProfileManager {
    public static boolean createProfile(String username, String fullname, String email, String password, int profilePicId, boolean is_active) {
        // Check if username already exists
        if (isUsernameTaken(username)) {
            CustomPopupAlert.showNotification("Username already exists!");
            return false;
        }

        // Perform other validations
        if (username == null || username.isEmpty() || fullname == null || fullname.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            CustomPopupAlert.showNotification("All fields required!");
            return false;
        }

        String query = "INSERT INTO profiles (username, fullname, email, pass, profile_pic, is_active) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, fullname);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.setInt(5, profilePicId);
            statement.setBoolean(6, is_active);
            statement.executeUpdate();
            CustomPopupAlert.showNotification("Profile created successfully.");
            return true;
        } catch (SQLException e) {
            CustomPopupAlert.showNotification("Failed to create profile.");
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isUsernameTaken(String username) {
        String query = "SELECT COUNT(*) FROM profiles WHERE username = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isUserActive(String username) {
        String query = "SELECT is_active FROM profiles WHERE username = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("is_active");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




    public static void deleteProfile(int profileId) { // this for deleting the profile through profile id 
        String sql = "DELETE FROM profiles WHERE id = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, profileId);
            pstmt.executeUpdate();
            System.out.println("Profile with ID " + profileId + "\n deleted successfully.");

        } catch (SQLException e) {
            System.err.println("Error deleting profile: " + e.getMessage());
        }
    }
    public static void deleteProfile(String username) { // this is for deleting the profile through profile username
        String sql = "DELETE FROM profiles WHERE username = ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.executeUpdate();
            CustomPopupAlert.showNotification("Profile with Username " + username + "\n deleted successfully.");
            System.out.println("Profile with Username " + username + " deleted successfully.");

        } catch (SQLException e) {
            System.err.println("Error deleting profile: " + e.getMessage());
        }
    }

    public static String generateAndStoreOTP(int profileId) {
        Random random = new Random();
        String otp = String.format("%04d", random.nextInt(10000)); // Generate a random 4-digit number as a string

        String selectQuery = "SELECT COUNT(*) FROM OTPs WHERE profile_id = ?";
        String insertQuery = "INSERT INTO OTPs (profile_id, otp_nbr) VALUES (?, ?)";
        String updateQuery = "UPDATE OTPs SET otp_nbr = ? WHERE profile_id = ?";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(selectQuery);
             PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {

            selectStmt.setInt(1, profileId);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    // Profile ID exists, update the OTP
                    updateStmt.setString(1, otp);
                    updateStmt.setInt(2, profileId);
                    updateStmt.executeUpdate();
                } else {
                    // Profile ID does not exist, insert a new record
                    insertStmt.setInt(1, profileId);
                    insertStmt.setString(2, otp);
                    insertStmt.executeUpdate();
                }
                return otp; // Return the generated OTP
            }
        } catch (SQLException e) {
            System.err.println("Failed to generate and store OTP: " + e.getMessage());
            return null;
        }
    }

    public static void setUserActive(int profileID) {
        String query = "UPDATE profiles SET is_active = true WHERE profile_id = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, profileID);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to set user active: " + e.getMessage());
        }
    }

    public static boolean updatePassword(int profileId, String newPassword) {
        String query = "UPDATE profiles SET pass = ? WHERE profile_id = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, newPassword);
            statement.setInt(2, profileId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Failed to update password: " + e.getMessage());
            return false;
        }
    }

    public static boolean verifyOTP(int profileId, String otp) {
        String query = "SELECT otp_nbr FROM OTPs WHERE profile_id = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, profileId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedOtp = resultSet.getString("otp_nbr");
                    if( otp.equals(storedOtp)){
                        setUserActive(profileId);
                        return true;

                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to verify OTP: " + e.getMessage());
        }
        return false; // Return false if OTP verification fails
    }

    public static Integer getProfileIdByUsername(String username) {
        String query = "SELECT profile_id FROM profiles WHERE username = ?";
        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("profile_id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Failed to get profile ID: " + e.getMessage());
        }
        return null; // Return null if profile ID not found or an error occurs
    }
    // Add these methods to ProfileManager.java

public static Profiles getProfileById(int profileId) {
    String query = "SELECT * FROM profiles WHERE profile_id = ?";
    try (Connection conn = DatabaseHelper.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, profileId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Profiles(
                rs.getInt("profile_id"),
                rs.getString("username"),
                rs.getString("fullname"),
                rs.getString("email"),
                rs.getString("pass"),
                rs.getInt("profile_pic")
            );
        }
    } catch (SQLException e) {
        System.err.println("Error fetching profile: " + e.getMessage());
    }
    return null;
}

public static boolean updateProfile(int profileId, String fullname, String email, String newPassword) {
    String query = "UPDATE profiles SET fullname = ?, email = ?" + 
                  (newPassword != null && !newPassword.isEmpty() ? ", pass = ?" : "") +
                  " WHERE profile_id = ?";
    try (Connection conn = DatabaseHelper.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, fullname);
        stmt.setString(2, email);
        if (newPassword != null && !newPassword.isEmpty()) {
            stmt.setString(3, newPassword);
            stmt.setInt(4, profileId);
        } else {
            stmt.setInt(3, profileId);
        }
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error updating profile: " + e.getMessage());
        return false;
    }
}

}