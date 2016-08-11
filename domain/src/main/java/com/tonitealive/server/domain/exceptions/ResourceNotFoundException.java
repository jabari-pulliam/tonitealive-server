package com.tonitealive.server.domain.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private final String resourceType;
    private final String resourceName;

    private ResourceNotFoundException(String resourceType, String resourceName, String message) {
        super(message);
        this.resourceType = resourceType;
        this.resourceName = resourceName;
    }

    public static ResourceNotFoundException create(String resourceType, String resourceName) {
        String message = "Resource not found: {resourceType: "
                +  resourceType + ", resourceName: " + resourceName + "}";
        return new ResourceNotFoundException(resourceType, resourceName, message);
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getResourceName() {
        return resourceName;
    }
}
