package com.tonitealive.server.data

import com.tonitealive.server.domain.UserProfile
import org.springframework.data.neo4j.repository.GraphRepository


interface UserProfilesRepository : GraphRepository<UserProfile> {
    fun findByUsername(username: String): UserProfile?
}