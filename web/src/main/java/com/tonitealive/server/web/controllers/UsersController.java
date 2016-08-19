package com.tonitealive.server.web.controllers;

import com.tonitealive.server.annotations.DebugLog;
import com.tonitealive.server.domain.exceptions.InternalServerErrorException;
import com.tonitealive.server.domain.interactors.GetUserByUsername;
import com.tonitealive.server.domain.interactors.UpdateUserProfilePhoto;
import com.tonitealive.server.domain.models.UserProfile;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("users/")
public class UsersController {

    private final GetUserByUsername getUserByUsername;
    private final UpdateUserProfilePhoto updateUserProfilePhoto;

    private static final String TMP_FILE_PREFIX = "TMP_";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public UsersController(GetUserByUsername getUserByUsername, UpdateUserProfilePhoto updateUserProfilePhoto) {
        this.getUserByUsername = getUserByUsername;
        this.updateUserProfilePhoto = updateUserProfilePhoto;
    }

    @DebugLog
    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public UserProfile getProfileByUsername(@PathVariable("username") String username) {
        return getUserByUsername.execute(username);
    }

    @DebugLog
    @PostMapping("{username}/profilePhoto")
    public ResponseEntity<?> saveProfilePhoto(@PathVariable("username") String username,
                                              @RequestPart(name = "file") MultipartFile imageFile) {
        // Transfer the multipart file to a temp file
        File tmpFile;
        try {
            tmpFile = File.createTempFile(TMP_FILE_PREFIX, null);
            imageFile.transferTo(tmpFile);
        } catch (IOException ex) {
            log.error("Failed to create temp file", ex);
            throw InternalServerErrorException.create();
        }

        // Execute the use case
        updateUserProfilePhoto.execute(username, tmpFile);

        // Delete the temp file in the background
        Observable.create(subscriber -> {
            FileUtils.deleteQuietly(tmpFile);
        }).subscribeOn(Schedulers.io());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}