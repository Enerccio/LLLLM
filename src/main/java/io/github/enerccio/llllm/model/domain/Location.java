package io.github.enerccio.llllm.model.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table( indexes = {
        @Index( name = "location_is_deleted_idx", columnList = "is_deleted"),
        @Index(name = "location_user_id_ix", columnList = "userId")
})
public class Location extends ExtendedContentEntity {

    @Lob
    private String name;

    @Lob
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Lorebook> lorebooks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Resource background;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Lorebook> getLorebooks() {
        return lorebooks;
    }

    public void setLorebooks(List<Lorebook> lorebooks) {
        this.lorebooks = lorebooks;
    }

    public Resource getBackground() {
        return background;
    }

    public void setBackground(Resource background) {
        this.background = background;
    }
}
