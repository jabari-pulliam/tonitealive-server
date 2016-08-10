package com.tonitealive.server.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.Url;
import com.cloudinary.utils.ObjectUtils;
import com.tonitealive.server.annotations.DebugLog;
import com.tonitealive.server.domain.exceptions.RemoteServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class CloudinaryFileStoreService implements FileStoreService {

    private static final String SERVICE_NAME = "cloudinary";
    private static final String DEFAULT_IMG_FORMAT = "png";
    private static final String KEY_PUBLIC_ID = "public_id";
    private static final String KEY_INVALIDATE = "invalidate";
    private static final String KEY_FOLDER = "folder";
    private static final String KEY_TRANSFORM = "transformation";
    private static final String IMAGES_FOLDER = "images";

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Cloudinary cloudinary;
    private final String rootFolder;

    @Autowired
    public CloudinaryFileStoreService(Environment env) {
        // Get the service URL
        String cloudinaryUrl = env.getProperty("CLOUDINARY_URL");
        cloudinary = new Cloudinary(cloudinaryUrl);

        // Get the root folder
        rootFolder = env.getProperty("tonitealive.filestoreservice.rootFolder");
    }

    @DebugLog
    @Override
    @SuppressWarnings("unchecked")
    public String storeImage(File imageFile) {
        checkNotNull(imageFile);

        try {
            Map options = ObjectUtils.asMap(KEY_FOLDER, rootFolder + "/" + IMAGES_FOLDER);
            Map result = cloudinary.uploader().upload(imageFile, options);
            return (String) result.get(KEY_PUBLIC_ID);
        } catch (IOException ex) {
            log.error("Failed to upload to cloudinary");
            throw RemoteServiceException.create(SERVICE_NAME, ex);
        }
    }

    @DebugLog
    @Override
    public void deleteFile(String publicId, boolean invalidateCache) {
        checkNotNull(publicId);

        try {
            Map options = ObjectUtils.asMap(KEY_INVALIDATE, invalidateCache);
            cloudinary.uploader().destroy(publicId, options);
        } catch (IOException ex) {
            log.error("Failed to delete file from cloudinary", ex);
            throw RemoteServiceException.create(SERVICE_NAME, ex);
        }
    }

    @DebugLog
    @Override
    public String getUrlForFile(String publicId) {
        checkNotNull(publicId);

        try {
            return cloudinary.url().format(DEFAULT_IMG_FORMAT).generate(publicId);
        } catch (Throwable ex) {
            log.error("Failed to get URL for file with public_id: {}", publicId, ex);
            throw RemoteServiceException.create(SERVICE_NAME, ex);
        }
    }

    @Override
    @DebugLog
    @SuppressWarnings("unchecked")
    public String getUrlForImage(String publicId, @Nullable Integer width, @Nullable Integer height) {
        checkNotNull(publicId);
        checkArgument((width == null && height == null) || (width != null && height != null),
                "Width and height must either both be specified or neither be specified");

        try {
            Url url = cloudinary.url();
            if (width != null) {
                Transformation transformation = new Transformation().width(width).height(height).crop("fit");
                url = url.transformation(transformation);
            }
            return url.generate(publicId);
        } catch (Throwable ex) {
            log.error("Could not get URL for image with public id: {}", publicId, ex);
            throw RemoteServiceException.create(SERVICE_NAME, ex);
        }
    }
}
