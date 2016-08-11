package com.tonitealive.server.domain.interactors;

import com.tonitealive.server.domain.models.UserProfile;
import com.tonitealive.server.domain.repositories.UserProfilesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static com.google.common.base.Preconditions.checkNotNull;

public class GetUserByUsernameUseCase {

    private final UserProfilesRepository userProfilesRepository;

    @Autowired
    public GetUserByUsernameUseCase(UserProfilesRepository userProfilesRepository) {
        this.userProfilesRepository = userProfilesRepository;
    }

    public UserProfile execute(String username) {
        checkNotNull(username);

        return userProfilesRepository.getByUsername(username);
    }

}
