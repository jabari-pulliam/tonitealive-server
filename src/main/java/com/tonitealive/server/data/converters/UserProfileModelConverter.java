package com.tonitealive.server.data.converters;

import com.tonitealive.server.data.entities.UserProfileEntity;
import com.tonitealive.server.domain.models.UserProfile;
import org.springframework.core.convert.converter.Converter;

public class UserProfileModelConverter implements Converter<UserProfile, UserProfileEntity> {
    @Override
    public UserProfileEntity convert(UserProfile source) {
        UserProfileEntity entity = new UserProfileEntity(source.username(),
                source.email(), source.profilePhotoUrl());
        return entity;
    }
}
