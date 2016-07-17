package com.tonitealive.server

import org.neo4j.ogm.session.SessionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.neo4j.config.Neo4jConfiguration
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Profile("production")
@Configuration
@EnableNeo4jRepositories
@EnableTransactionManagement
open class ProductionDatabaseConfig : Neo4jConfiguration() {

    @Value("\${GRAPHENEDB_IVORY_URL}") lateinit var neo4jUrl: String

    @Bean
    override fun getSessionFactory(): SessionFactory {
        return SessionFactory(configuration(), "com.tonitealive.server")
    }

    @Bean
    open fun configuration(): org.neo4j.ogm.config.Configuration {
        val config = org.neo4j.ogm.config.Configuration()
        config
                .driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setURI(neo4jUrl)
        return config
    }

}