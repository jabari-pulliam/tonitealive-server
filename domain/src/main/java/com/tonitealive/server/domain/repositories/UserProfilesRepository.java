package com.tonitealive.server.domain.repositories;

import com.tonitealive.server.domain.models.UserProfile;

public interface UserProfilesRepository {

    void save(UserProfile profile);

    void delete(String username);

    UserProfile getByUsername(String username);

}
