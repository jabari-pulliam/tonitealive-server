package com.tonitealive.server.services;

import com.tonitealive.server.data.UserProfilesRepository;
import com.tonitealive.server.data.entities.UserProfileEntity;
import com.tonitealive.server.domain.exceptions.ResourceNotFoundException;
import com.tonitealive.server.domain.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class DefaultUserProfilesService implements UserProfilesService {

    private final UserProfilesRepository repository;
    private final ConversionService conversionService;

    @Autowired
    public DefaultUserProfilesService(UserProfilesRepository repository, ConversionService conversionService) {
        this.repository = repository;
        this.conversionService = conversionService;
    }

    @Async
    @Override
    public void createProfile(UserProfile profileModel) {
        checkNotNull(profileModel);

        UserProfileEntity entity = conversionService.convert(profileModel, UserProfileEntity.class);
        repository.save(entity);
    }

    @Async
    @Override
    public Future<UserProfile> getProfileByUsername(String username) {
        checkNotNull(username);
        checkArgument(!username.isEmpty());

        UserProfileEntity entity = repository.findByUsername(username);
        if (entity == null)
            throw ResourceNotFoundException.create("UserProfile", username);

        UserProfile model = conversionService.convert(entity, UserProfile.class);
        return new AsyncResult<>(model);
    }
}
