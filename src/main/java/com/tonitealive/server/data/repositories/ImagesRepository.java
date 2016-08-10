package com.tonitealive.server.data.repositories;

import com.tonitealive.server.data.entities.ImageEntity;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface ImagesRepository extends GraphRepository<ImageEntity> {
    ImageEntity findByPublicId(String publicId);
}
