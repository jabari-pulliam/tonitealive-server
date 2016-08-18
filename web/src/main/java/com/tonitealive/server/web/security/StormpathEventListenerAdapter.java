package com.tonitealive.server.web.security;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.event.RegisteredAccountRequestEvent;
import com.stormpath.sdk.servlet.event.RequestEventListenerAdapter;
import com.tonitealive.server.domain.interactors.CreateNewUserAccountUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.schedulers.Schedulers;

@Component("stormpathRequestEventListener")
public class StormpathEventListenerAdapter extends RequestEventListenerAdapter {

    private final Logger log = LoggerFactory.getLogger(StormpathEventListenerAdapter.class);

    private final CreateNewUserAccountUseCase createNewUserAccountUseCase;

    @Autowired
    public StormpathEventListenerAdapter(CreateNewUserAccountUseCase createNewUserAccountUseCase) {
        this.createNewUserAccountUseCase = createNewUserAccountUseCase;
    }

    @Override
    public void on(RegisteredAccountRequestEvent e) {
        super.on(e);
        log.info("Received new account event");

        // Create a new user profile from the account
        Account account = e.getAccount();
        createNewUserAccountUseCase.execute(account.getUsername(), account.getEmail())
                .subscribeOn(Schedulers.io())
                .subscribe(value -> {}, error -> {
                    log.error("Failed to create new user account", error);
                });
    }

}
