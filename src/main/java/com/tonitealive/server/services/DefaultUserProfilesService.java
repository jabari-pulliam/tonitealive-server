package com.tonitealive.server.services;

import com.tonitealive.server.annotations.DebugLog;
import com.tonitealive.server.data.entities.ImageEntity;
import com.tonitealive.server.data.entities.UserProfileEntity;
import com.tonitealive.server.data.repositories.ImagesRepository;
import com.tonitealive.server.data.repositories.UserProfilesRepository;
import com.tonitealive.server.domain.exceptions.ResourceNotFoundException;
import com.tonitealive.server.domain.models.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.io.File;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class DefaultUserProfilesService implements UserProfilesService {

    private static final String TMP_FILE_PREFIX = "TA_";
    private static final Logger log = LoggerFactory.getLogger(DefaultUserProfilesService.class);

    private final UserProfilesRepository profilesRepository;
    private final ConversionService conversionService;
    private final FileStoreService fileStoreService;
    private final ImagesRepository imagesRepository;

    @Autowired
    public DefaultUserProfilesService(UserProfilesRepository profilesRepository,
                                      FileStoreService fileStoreService,
                                      ImagesRepository imagesRepository,
                                      ConversionService conversionService) {
        this.profilesRepository = profilesRepository;
        this.conversionService = conversionService;
        this.fileStoreService = fileStoreService;
        this.imagesRepository = imagesRepository;
    }

    @DebugLog
    @Override
    public void createProfile(UserProfile profileModel) {
        checkNotNull(profileModel);

        UserProfileEntity entity = conversionService.convert(profileModel, UserProfileEntity.class);
        profilesRepository.save(entity);
    }

    @DebugLog
    @Override
    public UserProfile getProfileByUsername(String username) {
        checkNotNull(username);
        checkArgument(!username.isEmpty());

        UserProfileEntity entity = profilesRepository.findByUsername(username);
        if (entity == null) {
            log.error("Failed to get user profile");
            throw ResourceNotFoundException.create("UserProfile", username);
        } else {
            return conversionService.convert(entity, UserProfile.class);
        }
    }

    @DebugLog
    @Override
    public void updateProfilePhoto(String username, File profilePhoto) {
        checkNotNull(username);
        checkArgument(!username.isEmpty());
        checkNotNull(profilePhoto);

        // Find the user profile
        UserProfileEntity profile = profilesRepository.findByUsername(username);
        if (profile != null) {
            // Store the file in the file store
            String fileId = fileStoreService.storeImage(profilePhoto);

            // Create an image entity for the stored file
            ImageEntity imageEntity = new ImageEntity(fileId);
            imageEntity = imagesRepository.save(imageEntity);

            // Save the profile photo in the profile
            profile.setProfilePhoto(imageEntity);
            profilesRepository.save(profile);
        } else {
            log.debug("No profile found for: {}", username);
            throw ResourceNotFoundException.create("UserProfile", username);
        }
    }
}
