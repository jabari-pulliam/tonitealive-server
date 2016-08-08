package com.tonitealive.server.controllers;

import com.tonitealive.server.domain.exceptions.InternalServerErrorException;
import com.tonitealive.server.domain.models.UserProfile;
import com.tonitealive.server.services.UserProfilesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("users/")
public class UsersController {

    private final UserProfilesService userProfilesService;
    private final Logger log = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    public UsersController(UserProfilesService userProfilesService) {
        this.userProfilesService = userProfilesService;
    }

    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public UserProfile getProfileByUsername(@PathVariable("username") String username) {
        UserProfile profile;
        return null;
    }

    @RequestMapping(value = "{username}/profilePhoto", method = RequestMethod.POST)
    public ResponseEntity<Void> saveProfilePhoto(@PathVariable String username,
                                                 @RequestParam("photo") MultipartFile imageFile) throws IOException {
        // TODO: Implement
        return null;
    }

}