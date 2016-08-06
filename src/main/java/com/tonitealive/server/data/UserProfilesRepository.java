package com.tonitealive.server.data;

import com.tonitealive.server.data.entities.UserProfileEntity;
import org.springframework.data.neo4j.repository.GraphRepository;


public interface UserProfilesRepository extends GraphRepository<UserProfileEntity> {
    UserProfileEntity findByUsername(String username);
    UserProfileEntity findByEmail(String email);
}