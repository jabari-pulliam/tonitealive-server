package com.tonitealive.server.data.repositories;

import com.tonitealive.server.data.entities.FileNode;
import org.springframework.data.neo4j.repository.GraphRepository;

interface FileNodesRepository extends GraphRepository<FileNode> {

    FileNode findByFileId(String fileId);

}
