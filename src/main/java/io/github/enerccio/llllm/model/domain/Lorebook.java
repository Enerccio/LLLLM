package io.github.enerccio.llllm.model.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(indexes = {
        @Index(name = "lorebook_is_deleted_idx", columnList = "is_deleted"),
        @Index(name = "lorebook_user_id_ix", columnList = "userId")
})
public class Lorebook extends ExtendedContentEntity {

    @Lob
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<LoreEntry> entries = new ArrayList<>();

    private boolean isDisabled;

    private long priority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LoreEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LoreEntry> entries) {
        this.entries = entries;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }
}
