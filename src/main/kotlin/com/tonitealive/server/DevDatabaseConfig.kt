package com.tonitealive.server

import com.tonitealive.server.domain.UserProfile
import org.neo4j.ogm.session.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.neo4j.config.Neo4jConfiguration
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import org.springframework.data.neo4j.template.Neo4jOperations
import org.springframework.transaction.annotation.EnableTransactionManagement

@Profile("dev")
@Configuration
@EnableNeo4jRepositories
@EnableTransactionManagement
open class DevDatabaseConfig : Neo4jConfiguration() {

    @Value("\${NEO4J_URL}") lateinit var neo4jUrl: String

    @Autowired
    lateinit var operations: Neo4jOperations

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

    @Bean
    open fun initDevDatabase(): CommandLineRunner {
        return CommandLineRunner { args ->
            clearDatabase()
        }
    }

    fun clearDatabase() {
        val types = arrayOf(UserProfile::class.java)
        for (t in types)
            operations.deleteAll(t)
    }
}