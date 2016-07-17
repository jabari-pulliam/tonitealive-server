package com.tonitealive.server.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component("roles")
class Roles @Autowired constructor(val environment: Environment) {

    val USER: String
    val ADMIN: String
    val PROMOTER: String

    init {
        USER = environment.getProperty("stormpath.authorized.group.user")
        ADMIN = environment.getProperty("stormpath.authorized.group.admin")
        PROMOTER = environment.getProperty("stormpath.authorized.group.promoter")
    }

}

