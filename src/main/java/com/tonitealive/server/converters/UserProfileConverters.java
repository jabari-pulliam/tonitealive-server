package com.tonitealive.server.converters;

import com.tonitealive.server.data.entities.UserProfileEntity;
import com.tonitealive.server.domain.models.UserProfileModel;
import org.springframework.core.convert.converter.Converter;

public final class UserProfileConverters {

    public static class ModelConverter implements Converter<UserProfileModel, UserProfileEntity> {
        @Override
        public UserProfileEntity convert(UserProfileModel source) {
            UserProfileEntity entity = new UserProfileEntity(source.getUsername(),
                    source.getEmail(), source.getProfilePhotoUrl());
            return entity;
        }
    }

    public static class EntityConverter implements Converter<UserProfileEntity, UserProfileModel> {
        @Override
        public UserProfileModel convert(UserProfileEntity source) {
            UserProfileModel model = UserProfileModel.create(source.getUsername(),
                    source.getEmail(), source.getProfilePhotoUrl());
            return model;
        }
    }

}
