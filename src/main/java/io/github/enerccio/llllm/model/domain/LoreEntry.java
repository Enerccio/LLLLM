package io.github.enerccio.llllm.model.domain;

import io.github.enerccio.llllm.model.domain.collections.ActivationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(indexes = {
    @Index(name = "loreentry_is_deleted_idx", columnList = "is_deleted"),
    @Index(name = "loreentry_user_id_ix", columnList = "userId")
})
public class LoreEntry extends ExtendedContentEntity {

    @Lob
    private String text;

    @Lob
    private String name;

    @Lob
    private String shortDescription;

    private ActivationType activationType;

    private String keywords;

    private boolean caseSensitive;

    private long priority;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public ActivationType getActivationType() {
        return activationType;
    }

    public void setActivationType(ActivationType activationType) {
        this.activationType = activationType;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }
}
