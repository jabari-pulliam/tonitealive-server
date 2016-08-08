package com.tonitealive.server.data.repositories;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Future;

@Repository
public class CloudinaryFilesRepository implements FilesRepository {

    private final Cloudinary cloudinary;
    private final String KEY_URL = "url";

    @Autowired
    public CloudinaryFilesRepository(Environment environment) {
        String cloudinaryUrl = environment.getProperty("CLOUDINARY_URL");
        this.cloudinary = new Cloudinary(cloudinaryUrl);
    }

    @Override
    public Future<String> storeImageFile(MultipartFile file) {
        File tmpFile = null;
        try {
            // Upload to cloudinary
            tmpFile = File.createTempFile("", "");
            file.transferTo(tmpFile);
            Map result = cloudinary.uploader().upload(tmpFile, ObjectUtils.emptyMap());
            String url = (String) result.get(KEY_URL);

            // Return the URL
            return new AsyncResult<>(url);
        } catch (IOException e) {

        } finally {
            // Cleanup the temp file
            if (tmpFile != null)
                FileUtils.deleteQuietly(tmpFile);
        }
        return null;
    }
}
