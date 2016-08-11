package com.tonitealive.server.domain.repositories;

import com.tonitealive.server.domain.models.FileType;

import javax.annotation.Nullable;
import java.io.File;

public interface FilesRepository {

    /**
     * Stores a file
     *
     * @param file The file
     * @param fileType The file content type
     * @return The file ID
     */
    String storeFile(File file, FileType fileType);

    /**
     * Deletes the file referenced by the public ID
     * @param fileId The public ID of the file
     * @param invalidateCache Whether or not to invalidate cahced versions of this file
     */
    void deleteFile(String fileId, boolean invalidateCache);

    /**
     * Gets the URL for the file referenced by the public ID
     *
     * @param fileId The public ID
     * @return The URL for the file
     */
    String getUrlForFile(String fileId);

    /**
     * Gets the URL for the image referenced by the public ID and makes it fit within
     * the given bounds if they are present
     *
     * @param fileId The public ID
     * @param width The width
     * @param height The height
     * @return The URL
     */
    String getUrlForImage(String fileId, @Nullable Integer width, @Nullable Integer height);


}
