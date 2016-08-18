package com.tonitealive.server.domain.interactors;

import com.tonitealive.server.domain.models.UserProfile;
import com.tonitealive.server.domain.repositories.UserProfilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class GetUserByUsername {

    private final UserProfilesRepository userProfilesRepository;

    @Autowired
    public GetUserByUsername(UserProfilesRepository userProfilesRepository) {
        this.userProfilesRepository = userProfilesRepository;
    }

    public UserProfile execute(String username) {
        checkNotNull(username);

        return userProfilesRepository.getByUsername(username);
    }

}
