package io.github.enerccio.llllm.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(indexes = {
        @Index(name = "resource_is_deleted_ix", columnList = "is_deleted"),
        @Index(name = "resource_user_id_ix", columnList = "userId")
})
public class Resource extends ExtendedContentEntity {

    private String mimeType;

    private String path;

    @Lob
    private String originalName;

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }
}
