package com.tonitealive.server.services;

import com.tonitealive.server.data.entities.UserProfileEntity;
import com.tonitealive.server.data.repositories.UserProfilesRepository;
import com.tonitealive.server.domain.exceptions.ResourceNotFoundException;
import com.tonitealive.server.domain.models.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.io.File;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class DefaultUserProfilesService implements UserProfilesService {

    private static final String TMP_FILE_PREFIX = "TA_";
    private static final Logger log = LoggerFactory.getLogger(DefaultUserProfilesService.class);

    private final UserProfilesRepository repository;
    private final ConversionService conversionService;

    @Autowired
    public DefaultUserProfilesService(UserProfilesRepository repository,
                                      ConversionService conversionService) {
        this.repository = repository;
        this.conversionService = conversionService;
    }

    @Override
    public Observable<Void> createProfile(UserProfile profileModel) {
        checkNotNull(profileModel);
        return Observable.create(subscriber -> {
            UserProfileEntity entity = conversionService.convert(profileModel, UserProfileEntity.class);
            repository.save(entity);

            if (!subscriber.isUnsubscribed()) {
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<UserProfile> getProfileByUsername(String username) {
        checkNotNull(username);
        checkArgument(!username.isEmpty());
        return Observable.create(subscriber -> {
            UserProfileEntity entity = repository.findByUsername(username);
            if (!subscriber.isUnsubscribed()) {
                if (entity == null) {
                    log.error("Failed to get user profile");
                    subscriber.onError(ResourceNotFoundException.create("UserProfile", username));
                } else {
                    UserProfile model = conversionService.convert(entity, UserProfile.class);
                    subscriber.onNext(model);
                    subscriber.onCompleted();
                }
            }
        });
    }

    @Override
    public String updateProfilePhoto(String username, File profilePhoto) {
        checkNotNull(username);
        checkArgument(!username.isEmpty());
        checkNotNull(profilePhoto);

        // TODO: Fix
        return null;
    }
}
