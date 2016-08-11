package com.tonitealive.server.domain.exceptions;

public class RemoteServiceException extends RuntimeException {

    private final String serviceName;

    public static RemoteServiceException create(String serviceName, Throwable cause) {
        return new RemoteServiceException(serviceName, cause);
    }

    private RemoteServiceException(String serviceName, Throwable cause) {
        super(cause);
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
