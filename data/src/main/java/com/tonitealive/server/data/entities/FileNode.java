package com.tonitealive.server.data.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@NodeEntity
public class FileNode {

    @GraphId
    private Long id;

    private String fileId;

    public FileNode(String fileId) {
        checkNotNull(fileId);
        checkArgument(!fileId.isEmpty());

        this.fileId = fileId;
    }

    public Long getId() {
        return id;
    }

    public String getFileId() {
        return fileId;
    }


}
