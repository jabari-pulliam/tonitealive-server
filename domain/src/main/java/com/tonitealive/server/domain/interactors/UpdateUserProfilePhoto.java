package com.tonitealive.server.domain.interactors;

import com.tonitealive.server.annotations.DebugLog;
import com.tonitealive.server.domain.repositories.FilesRepository;
import com.tonitealive.server.domain.repositories.UserProfilesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class UpdateUserProfilePhoto {

    private final FilesRepository filesRepository;
    private final UserProfilesRepository userProfilesRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public UpdateUserProfilePhoto(FilesRepository filesRepository, UserProfilesRepository userProfilesRepository) {
        this.filesRepository = filesRepository;
        this.userProfilesRepository = userProfilesRepository;
    }

    @DebugLog
    public void execute(String username, File profilePhotoFile) {
        checkNotNull(username);
        checkNotNull(profilePhotoFile);

        userProfilesRepository.updateUserProfilePhoto(username, profilePhotoFile);
    }

}
