package com.tonitealive.server.data;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

import static com.tonitealive.server.Profiles.PRODUCTION;

@Profile(PRODUCTION)
@Configuration
public class ProductionDatabaseConfig extends Neo4jConfiguration {

    @Value("${GRAPHENEDB_IVORY_URL}") private String neo4jUrl;

    @Bean
    @Override
    public SessionFactory getSessionFactory()  {
        return new SessionFactory(configuration(), "com.tonitealive.server");
    }

    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
        config.driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setURI(neo4jUrl);
        return config;
    }

}