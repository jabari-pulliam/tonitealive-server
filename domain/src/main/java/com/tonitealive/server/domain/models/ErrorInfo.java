package com.tonitealive.server.domain.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;
import org.joda.time.DateTime;

@AutoValue
public abstract class ErrorInfo {

    @JsonProperty("message")
    public abstract String message();

    @JsonProperty("errorCode")
    public abstract int errorCode();

    @JsonProperty("timestamp")
    public abstract long timestamp();

    @JsonProperty("path")
    public abstract String path();

    @JsonCreator
    public static ErrorInfo create(String message, int errorCode, String path) {
        return new AutoValue_ErrorInfo(message, errorCode, DateTime.now().getMillis(), path);
    }

}
