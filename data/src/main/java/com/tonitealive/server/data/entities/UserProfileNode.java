package com.tonitealive.server.data.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "UserProfile")
public class UserProfileNode {

    @GraphId private Long id;
    private String username;
    private String email;
    private FileNode profilePhoto;

    public UserProfileNode(String username, String email) {
        this.username = username;
        this.email = email;
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

    public FileNode getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(FileNode profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

}