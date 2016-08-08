package com.tonitealive.server.domain.models;

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
    @JsonProperty("profilePhotoUrl")
    public abstract String profilePhotoUrl();

    public static UserProfile create(String username, String email, @Nullable String profilePhotoUrl) {
        return new AutoValue_UserProfile(username, email, profilePhotoUrl);
    }

}
