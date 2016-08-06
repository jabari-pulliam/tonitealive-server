package com.tonitealive.server.data.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "UserProfile")
public class UserProfileEntity {

    @GraphId private Long id;
    private String username;
    private String email;
    private String profilePhotoUrl;

    public UserProfileEntity(String username, String email, String profilePhotoUrl) {
        this.username = username;
        this.email = email;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public UserProfileEntity(Long id, String username, String email, String profilePhotoUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }



}