package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.AI;
import io.github.enerccio.llllm.model.domain.ai.OpenAICompatible;
import io.github.enerccio.llllm.model.domain.collections.AIType;
import io.github.enerccio.llllm.model.service.AIService;
import io.github.enerccio.llllm.model.service.InferenceProvider;
import io.github.enerccio.llllm.model.service.OpenAICompatibleService;
import io.github.enerccio.llllm.model.service.inference.OpenAIInferenceService;
import io.github.enerccio.llllm.model.tx.CommonTx;
import org.springframework.beans.factory.annotation.Autowired;

public class AIServiceImpl extends ExtendedContentServiceImpl<AI> implements AIService {

    @Autowired
    private OpenAICompatibleService openAICompatibleService;

    private final OpenAIInferenceService openAIInferenceService = new OpenAIInferenceService();

    @Override
    protected Class<AI> getEntityClass() {
        return AI.class;
    }

    @Override
    @CommonTx
    public AI create(AIType aiType) throws Exception {
        AI ai = new AI();
        ai.setAiType(aiType);

        ai = save(ai);

        OpenAICompatible openAICompatible = openAICompatibleService.create();
        ai.setOpenAICompatible(openAICompatibleService.save(openAICompatible));

        return save(ai);
    }

    @Override
    public InferenceProvider getInferenceProvider(AI ai) {
        return switch (ai.getAiType()) {
            case OPEN_AI_COMPATIBLE -> openAIInferenceService;
            default -> throw new RuntimeException("Unsupported AI type: " + ai.getAiType());
        };
    }

    @Override
    @CommonTx
    public AI save(AI entity) throws Exception {
        if (entity.getOpenAICompatible() != null)
            entity.setOpenAICompatible(openAICompatibleService.save(entity.getOpenAICompatible()));
        entity = super.save(entity);
        return entity;
    }
}
