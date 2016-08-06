package com.tonitealive.server.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("roles")
public final class Roles {

    public final String USER;
    public final String ADMIN;
    public final String PROMOTER;

    @Autowired
    public Roles(Environment environment) {
        USER = environment.getProperty("stormpath.authorized.group.user");
        ADMIN = environment.getProperty("stormpath.authorized.group.admin");
        PROMOTER = environment.getProperty("stormpath.authorized.group.promoter");
    }

}

