package com.evoza.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookmarkUtils {

    public static void saveBookmark(int profileId, String title, String weburl) {
        try (Connection conn = DatabaseHelper.getConnection()) {
            String query = "INSERT INTO bookmarks (profile_id, title, weburl) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, profileId);
            stmt.setString(2, title);
            stmt.setString(3, weburl);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean bookmarkExists(int profileId, String weburl) {
        try (Connection conn = DatabaseHelper.getConnection()) {
            String query = "SELECT COUNT(*) FROM bookmarks WHERE profile_id = ? AND weburl = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, profileId);
            stmt.setString(2, weburl);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void removeBookmark(int bookmarkId) {
        try (Connection conn = DatabaseHelper.getConnection()) {
            String query = "DELETE FROM bookmarks WHERE bookmark_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookmarkId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Bookmark> getBookmarks(int profileId) {
        List<Bookmark> bookmarks = new ArrayList<>();
        try (Connection conn = DatabaseHelper.getConnection()) {
            String query = "SELECT bookmark_id, title, weburl FROM bookmarks WHERE profile_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, profileId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int bookmarkId = rs.getInt("bookmark_id");
                String title = rs.getString("title");
                String weburl = rs.getString("weburl");
                bookmarks.add(new Bookmark(bookmarkId, profileId, title, weburl));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookmarks;
    }

    public static class Bookmark {
        private int bookmarkId;
        private int profileId;
        private String title;
        private String weburl;

        public Bookmark(int bookmarkId, int profileId, String title, String weburl) {
            this.bookmarkId = bookmarkId;
            this.profileId = profileId;
            this.title = title;
            this.weburl = weburl;
        }

        public int getBookmarkId() {
            return bookmarkId;
        }

        public int getProfileId() {
            return profileId;
        }

        public String getTitle() {
            return title;
        }

        public String getWeburl() {
            return weburl;
        }
    }
}