package com.tonitealive.server.controllers;

import com.tonitealive.server.domain.exceptions.InternalServerErrorException;
import com.tonitealive.server.domain.models.UserProfileModel;
import com.tonitealive.server.services.UserProfilesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public UserProfileModel getProfileByUsername(@PathVariable("username") String username) {
        UserProfileModel profile;
        try {
            profile = userProfilesService.getProfileByUsername(username).get();
            return profile;
        } catch (InterruptedException | CancellationException | ExecutionException e) {
            log.error("Failed to get user profile", e);
            throw InternalServerErrorException.create();
        }
    }

}