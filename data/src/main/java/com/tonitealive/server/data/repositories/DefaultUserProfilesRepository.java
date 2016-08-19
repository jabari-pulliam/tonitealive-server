package com.tonitealive.server.data.repositories;

import com.tonitealive.server.annotations.DebugLog;
import com.tonitealive.server.data.entities.FileNode;
import com.tonitealive.server.data.entities.UserProfileNode;
import com.tonitealive.server.domain.exceptions.ResourceNotFoundException;
import com.tonitealive.server.domain.models.FileType;
import com.tonitealive.server.domain.models.UserProfile;
import com.tonitealive.server.domain.repositories.FilesRepository;
import com.tonitealive.server.domain.repositories.UserProfilesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Repository;

import java.io.File;

@Repository
class DefaultUserProfilesRepository implements UserProfilesRepository {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserProfileNodesRepository userProfileNodesRepository;
    private final FileNodesRepository fileNodesRepository;
    private final FilesRepository filesRepository;
    private final ConversionService conversionService;

    @Autowired
    public DefaultUserProfilesRepository(UserProfileNodesRepository userProfileNodesRepository,
                                         FileNodesRepository fileNodesRepository,
                                         FilesRepository filesRepository,
                                         ConversionService conversionService) {
        this.userProfileNodesRepository = userProfileNodesRepository;
        this.fileNodesRepository = fileNodesRepository;
        this.conversionService = conversionService;
        this.filesRepository = filesRepository;
    }

    @DebugLog
    @Override
    public void save(UserProfile profile) {
        UserProfileNode node = conversionService.convert(profile, UserProfileNode.class);
        userProfileNodesRepository.save(node);
    }

    @DebugLog
    @Override
    public void delete(String username) {
        UserProfileNode node = userProfileNodesRepository.findByUsername(username);
        if (node == null) {
            log.debug("Not profile found for username: {}", username);
            throw ResourceNotFoundException.create("UserProfile", username);
        }

        FileNode profilePhotoNode = node.getProfilePhoto();
        if (profilePhotoNode != null) {
            filesRepository.deleteFile(profilePhotoNode.getFileId(), true);
            fileNodesRepository.delete(profilePhotoNode);
            node.setProfilePhoto(null);
        }
        userProfileNodesRepository.delete(node);
    }

    @DebugLog
    @Override
    public UserProfile getByUsername(String username) {
        UserProfileNode node = userProfileNodesRepository.findByUsername(username);
        if (node == null){
            log.debug("No profile found for username: {}", username);
            throw ResourceNotFoundException.create("UserProfile", username);
        } else {
            UserProfile profile = conversionService.convert(node, UserProfile.class);
            FileNode profilePhotoNode = node.getProfilePhoto();
            if (profilePhotoNode != null)
                profile = UserProfile.create(profile.username(),
                        profile.email(),
                        profilePhotoNode.getFileId());
            return profile;
        }
    }

    @Override
    public void updateUserProfilePhoto(String username, File imageFile) {
        UserProfileNode profileNode = userProfileNodesRepository.findByUsername(username);
        if (profileNode == null) {
            log.debug("No profile found for username: {}", username);
            throw ResourceNotFoundException.create("UserProfile", username);
        } else {
            String fileId = filesRepository.storeFile(imageFile, FileType.IMAGE);
            // If the fileId is null then an exception would have been thrown, which we will not handle here
            if (fileId != null) {
                FileNode fileNode = new FileNode(fileId);
                fileNode = fileNodesRepository.save(fileNode);

                profileNode.setProfilePhoto(fileNode);
                userProfileNodesRepository.save(profileNode);
            }
        }
    }
}
