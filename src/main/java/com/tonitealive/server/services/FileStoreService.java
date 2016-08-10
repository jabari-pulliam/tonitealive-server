package com.tonitealive.server.services;

import javax.annotation.Nullable;
import java.io.File;

/**
 * Handles storing files
 */
public interface FileStoreService {

    /**
     * Stores an unaltered image file
     *
     * @param imageFile The image file
     * @return The public ID of the stored file
     */
    String storeImage(File imageFile);

    /**
     * Deletes the file referenced by the public ID
     * @param publicId The public ID of the file
     * @param invalidateCache Whether or not to invalidate cahced versions of this file
     */
    void deleteFile(String publicId, boolean invalidateCache);

    /**
     * Gets the URL for the file referenced by the public ID
     *
     * @param publicId The public ID
     * @return The URL for the file
     */
    String getUrlForFile(String publicId);

    /**
     * Gets the URL for the image referenced by the public ID and makes it fit within
     * the given bounds if they are present
     *
     * @param publicId The public ID
     * @param width The width
     * @param height The height
     * @return The URL
     */
    String getUrlForImage(String publicId, @Nullable Integer width, @Nullable Integer height);

}
