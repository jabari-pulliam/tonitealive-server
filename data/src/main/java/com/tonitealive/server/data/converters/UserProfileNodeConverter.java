package com.tonitealive.server.data.converters;

import com.tonitealive.server.annotations.DebugLog;
import com.tonitealive.server.data.entities.UserProfileNode;
import com.tonitealive.server.domain.models.UserProfile;
import org.springframework.core.convert.converter.Converter;

public class UserProfileNodeConverter implements Converter<UserProfileNode, UserProfile> {

    @DebugLog
    @Override
    public UserProfile convert(UserProfileNode source) {
        return UserProfile.create(source.getUsername(), source.getEmail());
    }
}
