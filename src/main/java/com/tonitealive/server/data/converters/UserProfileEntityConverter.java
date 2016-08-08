package com.tonitealive.server.data.converters;

import com.tonitealive.server.data.entities.UserProfileEntity;
import com.tonitealive.server.domain.models.UserProfile;
import org.springframework.core.convert.converter.Converter;

public class UserProfileEntityConverter implements Converter<UserProfileEntity, UserProfile> {
    @Override
    public UserProfile convert(UserProfileEntity source) {
        UserProfile model = UserProfile.create(source.getUsername(),
                source.getEmail(), source.getProfilePhotoUrl());
        return model;
    }
}
