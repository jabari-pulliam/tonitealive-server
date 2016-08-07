package com.tonitealive.server.converters;

import com.tonitealive.server.data.entities.UserProfileEntity;
import com.tonitealive.server.domain.models.UserProfile;
import org.springframework.core.convert.converter.Converter;

public final class UserProfileConverters {

    public static class ModelConverter implements Converter<UserProfile, UserProfileEntity> {
        @Override
        public UserProfileEntity convert(UserProfile source) {
            UserProfileEntity entity = new UserProfileEntity(source.username(),
                    source.email(), source.profilePhotoUrl());
            return entity;
        }
    }

    public static class EntityConverter implements Converter<UserProfileEntity, UserProfile> {
        @Override
        public UserProfile convert(UserProfileEntity source) {
            UserProfile model = UserProfile.create(source.getUsername(),
                    source.getEmail(), source.getProfilePhotoUrl());
            return model;
        }
    }

}
