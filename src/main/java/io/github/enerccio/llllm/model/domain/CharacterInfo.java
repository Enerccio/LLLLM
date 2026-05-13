package io.github.enerccio.llllm.model.domain;

import io.github.enerccio.llllm.model.domain.collections.Gender;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(indexes = {
        @Index(name = "character_is_deleted_idx", columnList = "is_deleted"),
        @Index(name = "character_user_id_ix", columnList = "userId")
})
public class CharacterInfo extends ExtendedContentEntity {

    @Lob
    private String name;

    @Lob
    private String description;

    @Lob
    private String voice;

    private Gender gender;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Lorebook> lorebooks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Resource icon;

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

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
