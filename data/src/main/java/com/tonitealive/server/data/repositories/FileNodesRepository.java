package com.tonitealive.server.data.repositories;

import com.tonitealive.server.data.entities.FileNode;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface FileNodesRepository extends GraphRepository<FileNode> {

    FileNode findByFileId(String fileId);

}
