package com.tonitealive.server.services;

import com.tonitealive.server.domain.models.UserProfile;

import java.io.File;

/**
 * Manages user profiles.
 */
public interface UserProfilesService {

    /**
     * Creates a new user profile using the given model.
     *
     * @param profileModel The profile
     */
    void createProfile(UserProfile profileModel);

    /**
     * Gets a user profile by its username
     *
     * @param username The username
     * @return A {@link UserProfile}
     * @throws com.tonitealive.server.domain.exceptions.ResourceNotFoundException
     */
    UserProfile getProfileByUsername(String username);

    /**
     * Adds/updates the profile photo for a user with the given username.
     *
     * @param username The username
     * @param profilePhoto The profile photo file
     * @throws com.tonitealive.server.domain.exceptions.ResourceNotFoundException Thrown if there is no user with the
     * username
     */
    void updateProfilePhoto(String username, File profilePhoto);

}
