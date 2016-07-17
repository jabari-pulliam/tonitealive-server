package com.tonitealive.server

import com.stormpath.spring.config.StormpathWebSecurityConfigurer.stormpath
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
open class SpringSecurityWebAppConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http?.apply(stormpath())?.and()?.csrf()?.disable()
    }

}