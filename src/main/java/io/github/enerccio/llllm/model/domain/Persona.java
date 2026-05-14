package io.github.enerccio.llllm.model.domain;

import io.github.enerccio.llllm.model.domain.collections.Gender;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table( indexes = {
        @Index( name = "persona_is_deleted_idx", columnList = "is_deleted"),
        @Index(name = "persona_user_id_ix", columnList = "userId")
})
public class Persona extends ExtendedContentEntity {

    @Lob
    private String name;

    @Lob
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Lorebook> lorebooks;

    @ManyToOne(fetch = FetchType.EAGER)
    private Resource icon;

    @ManyToOne(fetch = FetchType.EAGER)
    private Resource photo;

    @ManyToOne(fetch = FetchType.EAGER)
    private Language language;

    @Enumerated(EnumType.STRING)
    private Gender gender;

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

    public Resource getIcon() {
        return icon;
    }

    public void setIcon(Resource icon) {
        this.icon = icon;
    }

    public Resource getPhoto() {
        return photo;
    }

    public void setPhoto(Resource photo) {
        this.photo = photo;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
