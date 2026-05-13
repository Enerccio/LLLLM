package io.github.enerccio.llllm.model.domain;

import jakarta.persistence.*;

@Entity
@Table(indexes = {
    @Index( name = "character_sprite_is_deleted_idx", columnList = "is_deleted"),
    @Index(name = "character_sprite_user_id_ix", columnList = "userId")
})
public class CharacterSprite extends ExtendedContentEntity {

    @ManyToOne
    private Resource sprite;

    @Lob
    private String description;

    @Lob
    private String emote;

    private boolean isDefault;

    public Resource getSprite() {
        return sprite;
    }

    public void setSprite(Resource sprite) {
        this.sprite = sprite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmote() {
        return emote;
    }

    public void setEmote(String emote) {
        this.emote = emote;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
