package com.tonitealive.server.domain.models;

import org.joda.time.DateTime;

public class ApiErrorModel {

    private final String message;
    private final int errorCode;
    private final long timestamp;

    public static ApiErrorModel create(String message, int errorCode) {
        return new ApiErrorModel(message, errorCode, DateTime.now().getMillis());
    }

    private ApiErrorModel(String message, int errorCode, long timestamp) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = timestamp;
    }
}
