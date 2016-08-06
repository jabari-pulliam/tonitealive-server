package com.tonitealive.server.domain.models;

import javax.annotation.Nullable;

public class UserProfileModel {

    private final String username;
    private final String email;
    private final String profilePhotoUrl;

    public static UserProfileModel create(String username, String email, @Nullable String profilePhotoUrl) {
        return new UserProfileModel(username, email, profilePhotoUrl);
    }

    private UserProfileModel(String username, String email, String profilePhotoUrl) {
        this.username = username;
        this.email = email;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }
}
