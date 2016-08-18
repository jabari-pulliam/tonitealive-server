package com.tonitealive.server.domain.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class UserProfile {

    @JsonProperty("username")
    public abstract String username();

    @JsonProperty("email")
    public abstract String email();

    @JsonCreator
    public static UserProfile create(@JsonProperty("username") String username,
                                     @JsonProperty("email") String email) {
        return new AutoValue_UserProfile(username, email);
    }

}
