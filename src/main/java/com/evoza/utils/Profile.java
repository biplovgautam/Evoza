package com.evoza.utils;

public class Profile {
    private int profileId;
    private String username;
    private String fullname;
    private String email;
    private String password;
    private int profilePicId;

    public Profile(int profileId, String username, String fullname, String email, String password, int profilePicId) {
        this.profileId = profileId;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.profilePicId = profilePicId;
    }

    // Getters and setters
    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getProfilePicId() {
        return profilePicId;
    }

    public void setProfilePicId(int profilePicId) {
        this.profilePicId = profilePicId;
    }
}