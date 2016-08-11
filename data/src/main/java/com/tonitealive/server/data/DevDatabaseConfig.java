package com.tonitealive.server.data;

import com.tonitealive.server.Profiles;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

@Profile({Profiles.DEV, Profiles.TEST})
@Configuration
public class DevDatabaseConfig extends Neo4jConfiguration {

    @Bean
    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory(configuration(), "com.tonitealive.server");
    }

    @Bean
    public org.neo4j.ogm.config.Configuration configuration()  {
        org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
        config.driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver");
        return config;
    }

}