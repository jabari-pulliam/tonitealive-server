package com.tonitealive.server.services;

import com.tonitealive.server.data.repositories.FilesRepository;
import com.tonitealive.server.data.repositories.UserProfilesRepository;
import com.tonitealive.server.data.entities.UserProfileEntity;
import com.tonitealive.server.domain.exceptions.ResourceNotFoundException;
import com.tonitealive.server.domain.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rx.Observable;

import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.json.XMLTokener.entity;

@Service
public class DefaultUserProfilesService implements UserProfilesService {

    private final UserProfilesRepository repository;
    private final ConversionService conversionService;
    private final FilesRepository filesRepository;

    @Autowired
    public DefaultUserProfilesService(UserProfilesRepository repository, FilesRepository filesRepository,
                                      ConversionService conversionService) {
        this.repository = repository;
        this.conversionService = conversionService;
        this.filesRepository = filesRepository;
    }

    @Async
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

    @Async
    @Override
    public Observable<UserProfile> getProfileByUsername(String username) {
        checkNotNull(username);
        checkArgument(!username.isEmpty());
        return Observable.create(subscriber -> {
            UserProfileEntity entity = repository.findByUsername(username);
            if (!subscriber.isUnsubscribed()) {
                if (entity == null) {
                    subscriber.onError(ResourceNotFoundException.create("UserProfile", username));
                } else {
                    UserProfile model = conversionService.convert(entity, UserProfile.class);
                    subscriber.onNext(model);
                    subscriber.onCompleted();
                }
            }
        });
    }

    @Async
    @Override
    public Observable<UserProfile> updateProfilePhoto(String username, MultipartFile profilePhoto) {
        checkNotNull(username);
        checkArgument(!username.isEmpty());
        checkNotNull(profilePhoto);

        // Transform the image file


        // Store the image file


        //

        return null;
    }
}
