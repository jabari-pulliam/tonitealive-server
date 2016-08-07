package com.tonitealive.server.services;

import com.tonitealive.server.domain.models.UserProfile;

import java.util.concurrent.Future;

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
     * @return A future that will return a {@link UserProfile}
     * @throws com.tonitealive.server.domain.exceptions.ResourceNotFoundException
     */
    Future<UserProfile> getProfileByUsername(String username);

}
