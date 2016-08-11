package com.tonitealive.server.data.repositories;

import com.tonitealive.server.annotations.DebugLog;
import com.tonitealive.server.data.entities.UserProfileNode;
import com.tonitealive.server.domain.exceptions.ResourceNotFoundException;
import com.tonitealive.server.domain.models.UserProfile;
import com.tonitealive.server.domain.repositories.UserProfilesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultUserProfilesRepository implements UserProfilesRepository {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserProfileNodesRepository userProfileNodesRepository;
    private final ConversionService conversionService;

    @Autowired
    public DefaultUserProfilesRepository(UserProfileNodesRepository userProfileNodesRepository,
                                         ConversionService conversionService) {
        this.userProfileNodesRepository = userProfileNodesRepository;
        this.conversionService = conversionService;
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
        userProfileNodesRepository.delete(node);
    }

    @DebugLog
    @Override
    public UserProfile getByUsername(String username) {
        UserProfileNode node = userProfileNodesRepository.findByUsername(username);
        if (node != null)
            return conversionService.convert(node, UserProfile.class);
        else {
            log.debug("No profile found for username: {}", username);
            throw ResourceNotFoundException.create("UserProfile", username);
        }
    }
}
