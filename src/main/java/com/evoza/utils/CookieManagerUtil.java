package com.evoza.utils;

import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class CookieManagerUtil {
    private static final CookieManager cookieManager = new CookieManager();

    public static CookieManager getCookieManager() {
        return cookieManager;
    }

    public static void loadCookies(int profileId) {
        try (Connection conn = DatabaseHelper.getConnection()) {
            String query = "SELECT domain, name, value, path, expiry FROM cookies WHERE profile_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, profileId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String domain = rs.getString("domain");
                String name = rs.getString("name");
                String value = rs.getString("value");
                String path = rs.getString("path");
                String expiry = rs.getString("expiry");

                HttpCookie cookie = new HttpCookie(name, value);
                cookie.setDomain(domain);
                cookie.setPath(path);
                if (expiry != null) {
                    cookie.setMaxAge(Long.parseLong(expiry));
                }

                cookieManager.getCookieStore().add(new URI(domain), cookie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveCookies(int profileId) {
        try (Connection conn = DatabaseHelper.getConnection()) {
            String deleteQuery = "DELETE FROM cookies WHERE profile_id = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, profileId);
            deleteStmt.executeUpdate();

            String insertQuery = "INSERT INTO cookies (profile_id, domain, name, value, path, expiry) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);

            List<HttpCookie> cookies = cookieManager.getCookieStore().getCookies();
            for (HttpCookie cookie : cookies) {
                insertStmt.setInt(1, profileId);
                insertStmt.setString(2, cookie.getDomain());
                insertStmt.setString(3, cookie.getName());
                insertStmt.setString(4, cookie.getValue());
                insertStmt.setString(5, cookie.getPath());
                insertStmt.setString(6, cookie.getMaxAge() > 0 ? String.valueOf(cookie.getMaxAge()) : null);
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}