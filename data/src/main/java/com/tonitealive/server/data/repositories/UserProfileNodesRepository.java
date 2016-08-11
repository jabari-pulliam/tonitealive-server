package com.tonitealive.server.data.repositories;

import com.tonitealive.server.data.entities.UserProfileNode;
import org.springframework.data.neo4j.repository.GraphRepository;


public interface UserProfileNodesRepository extends GraphRepository<UserProfileNode> {
    UserProfileNode findByUsername(String username);
    UserProfileNode findByEmail(String email);
}