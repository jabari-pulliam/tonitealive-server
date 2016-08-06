package com.tonitealive.server.services;

import com.tonitealive.server.domain.models.UserProfileModel;

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
    void createProfile(UserProfileModel profileModel);

    /**
     * Gets a user profile by its username
     *
     * @param username The username
     * @return A future that will return a {@link UserProfileModel}
     * @throws com.tonitealive.server.domain.exceptions.ResourceNotFoundException
     */
    Future<UserProfileModel> getProfileByUsername(String username);

}
