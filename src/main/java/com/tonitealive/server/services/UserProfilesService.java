package com.tonitealive.server.services;

import com.tonitealive.server.domain.models.UserProfile;
import rx.Observable;

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
    Observable<Void> createProfile(UserProfile profileModel);

    /**
     * Gets a user profile by its username
     *
     * @param username The username
     * @return A future that will return a {@link UserProfile}
     * @throws com.tonitealive.server.domain.exceptions.ResourceNotFoundException
     */
    Observable<UserProfile> getProfileByUsername(String username);

    /**
     * Adds/updates the profile photo for a user with the given username.
     *
     * @param username The username
     * @param profilePhoto The profile photo file
     * @return The profile photo URL
     * @throws com.tonitealive.server.domain.exceptions.ResourceNotFoundException Thrown if there is no user with the
     * username
     */
    String updateProfilePhoto(String username, File profilePhoto);

}
