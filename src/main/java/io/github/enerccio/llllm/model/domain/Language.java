package io.github.enerccio.llllm.model.domain;


import io.github.enerccio.llllm.model.domain.collections.SupportedLanguage;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table (indexes = {
    @Index(name = "language_is_deleted_idx", columnList = "is_deleted"),
    @Index(name = "language_user_id_ix", columnList = "userId")
})
public class Language extends ExtendedContentEntity {

    private SupportedLanguage language;

    @Lob
    private String difficultyModifier;

    public SupportedLanguage getLanguage() {
        return language;
    }

    public void setLanguage(SupportedLanguage language) {
        this.language = language;
    }

    public String getDifficultyModifier() {
        return difficultyModifier;
    }

    public void setDifficultyModifier(String difficultyModifier) {
        this.difficultyModifier = difficultyModifier;
    }
}
