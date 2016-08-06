package com.tonitealive.server.auth;

import com.stormpath.sdk.servlet.account.event.RegisteredAccountRequestEvent;
import com.stormpath.sdk.servlet.event.RequestEventListenerAdapter;

public class StormpathEventListenerAdapter extends RequestEventListenerAdapter {
    @Override
    public void on(RegisteredAccountRequestEvent e) {
        super.on(e);


    }
}
