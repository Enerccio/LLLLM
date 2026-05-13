package io.github.enerccio.llllm.model.domain;

import io.github.enerccio.llllm.model.domain.ai.OpenAICompatible;
import io.github.enerccio.llllm.model.domain.collections.AIType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class AI extends ExtendedContentEntity {

    private AIType aiType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "openai_compatible_id", nullable = true)
    private OpenAICompatible openAICompatible;

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
}
