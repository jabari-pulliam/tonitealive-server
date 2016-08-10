package com.tonitealive.server.data.converters;

import com.tonitealive.server.annotations.DebugLog;
import com.tonitealive.server.data.entities.UserProfileEntity;
import com.tonitealive.server.domain.models.UserProfile;
import org.springframework.core.convert.converter.Converter;

public class UserProfileEntityConverter implements Converter<UserProfileEntity, UserProfile> {

    @DebugLog
    @Override
    public UserProfile convert(UserProfileEntity source) {
        return UserProfile.create(source.getUsername(), source.getEmail());
    }
}
