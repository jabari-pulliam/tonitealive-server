package com.tonitealive.server.auth;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.event.RegisteredAccountRequestEvent;
import com.stormpath.sdk.servlet.event.RequestEventListenerAdapter;
import com.tonitealive.server.domain.models.UserProfile;
import com.tonitealive.server.services.UserProfilesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("stormpathRequestEventListener")
public class StormpathEventListenerAdapter extends RequestEventListenerAdapter {

    private final UserProfilesService userProfilesService;
    private final Logger log = LoggerFactory.getLogger(StormpathEventListenerAdapter.class);

    @Autowired
    public StormpathEventListenerAdapter(UserProfilesService userProfilesService) {
        this.userProfilesService = userProfilesService;
    }

    @Override
    public void on(RegisteredAccountRequestEvent e) {
        super.on(e);
        log.info("Received new account event");

        // Create a new user profile from the account
        Account account = e.getAccount();
        UserProfile profile = UserProfile.create(account.getUsername(), account.getEmail());
        userProfilesService.createProfile(profile);
    }
}
