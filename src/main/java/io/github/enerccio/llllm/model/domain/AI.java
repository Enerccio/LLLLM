package io.github.enerccio.llllm.model.domain;

import io.github.enerccio.llllm.model.domain.ai.OpenAICompatible;
import io.github.enerccio.llllm.model.domain.collections.AIType;
import jakarta.persistence.*;

@Entity
@Table(indexes = {
        @Index(name = "ai_is_deleted_idx", columnList = "is_deleted"),
        @Index(name = "ai_type_idx", columnList = "ai_type"),
        @Index(name = "ai_user_id_ix", columnList = "userId")
})
public class AI extends ExtendedContentEntity {

    private AIType aiType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "openai_compatible_id", nullable = true)
    private OpenAICompatible openAICompatible;

    @Lob
    private String name;

    public OpenAICompatible getOpenAICompatible() {
        return openAICompatible;
    }

    public void setOpenAICompatible(OpenAICompatible openAICompatible) {
        this.openAICompatible = openAICompatible;
    }

    public AIType getAiType() {
        return aiType;
    }

    public void setAiType(AIType aiType) {
        this.aiType = aiType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
