package com.tonitealive.server.web.controllers;

import com.tonitealive.server.annotations.DebugLog;
import com.tonitealive.server.domain.exceptions.InternalServerErrorException;
import com.tonitealive.server.domain.models.UserProfile;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("users/")
public class UsersController {

    private static final String TMP_FILE_PREFIX = "TMP_";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @DebugLog
    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public UserProfile getProfileByUsername(@PathVariable("username") String username) {
        return null;
    }

    @DebugLog
    @RequestMapping(value = "{username}/profilePhoto", method = RequestMethod.POST)
    public ResponseEntity<Void> saveProfilePhoto(@PathVariable String username,
                                                 @RequestParam("photo") MultipartFile imageFile) {
        File tmpFile;
        try {
            tmpFile = File.createTempFile(TMP_FILE_PREFIX, null);
            imageFile.transferTo(tmpFile);
        } catch (IOException ex) {
            log.error("Failed to create temp file", ex);
            throw InternalServerErrorException.create();
        }


        // Delete the temp file
        FileUtils.deleteQuietly(tmpFile);

        return ResponseEntity.ok().build();
    }

}