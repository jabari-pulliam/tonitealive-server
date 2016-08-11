package com.tonitealive.server.domain.interactors;

import com.tonitealive.server.domain.models.FileType;
import com.tonitealive.server.domain.models.UserProfile;
import com.tonitealive.server.domain.repositories.FilesRepository;
import com.tonitealive.server.domain.repositories.UserProfilesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

import static com.google.common.base.Preconditions.checkNotNull;

public class UpdateUserProfilePhoto {

    private final FilesRepository filesRepository;
    private final UserProfilesRepository userProfilesRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public UpdateUserProfilePhoto(FilesRepository filesRepository, UserProfilesRepository userProfilesRepository) {
        this.filesRepository = filesRepository;
        this.userProfilesRepository = userProfilesRepository;
    }

    public void execute(String username, File profilePhotoFile) {
        checkNotNull(username);
        checkNotNull(profilePhotoFile);

        UserProfile profile = userProfilesRepository.getByUsername(username);
        if (profile == null) {
            log.debug("No user found with username: {}", username);
        } else {
            String fileId = filesRepository.storeFile(profilePhotoFile, FileType.IMAGE);

        }


    }

}
