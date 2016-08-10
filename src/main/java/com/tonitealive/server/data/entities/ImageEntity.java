package com.tonitealive.server.data.entities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "Photo")
public class ImageEntity {

    @GraphId
    private Long id;

    private String publicId;

    public ImageEntity(String publicId) {
        this.publicId = publicId;
    }

    public Long getId() {
        return id;
    }

    public String getPublicId() {
        return publicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageEntity that = (ImageEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return publicId.equals(that.publicId);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + publicId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ImageEntity{" +
                "id=" + id +
                ", publicId='" + publicId + '\'' +
                '}';
    }
}
