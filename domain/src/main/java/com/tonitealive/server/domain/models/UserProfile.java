package com.tonitealive.server.domain.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

@AutoValue
public abstract class UserProfile {

    @JsonProperty("username")
    public abstract String username();

    @JsonProperty("email")
    public abstract String email();

    @Nullable
    @JsonProperty("profilePhotoId")
    public abstract String profilePhotoId();

    @JsonCreator
    public static UserProfile create(@JsonProperty("username") String username,
                                     @JsonProperty("email") String email,
                                     @JsonProperty("profilePhotoId") @Nullable String profilePhotoId) {
        return new AutoValue_UserProfile(username, email, profilePhotoId);
    }

    public static UserProfile create(String username, String email) {
        return create(username, email, null);
    }

}
