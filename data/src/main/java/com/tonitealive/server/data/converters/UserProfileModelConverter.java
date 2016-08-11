package com.tonitealive.server.data.converters;

import com.tonitealive.server.data.entities.UserProfileNode;
import com.tonitealive.server.domain.models.UserProfile;
import org.springframework.core.convert.converter.Converter;

public class UserProfileModelConverter implements Converter<UserProfile, UserProfileNode> {
    @Override
    public UserProfileNode convert(UserProfile source) {
        UserProfileNode entity = new UserProfileNode(source.username(), source.email());
        return entity;
    }
}
