package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.AI;
import io.github.enerccio.llllm.model.domain.collections.AIType;
import io.github.enerccio.llllm.model.service.AIService;
import io.github.enerccio.llllm.model.service.OpenAICompatibleService;
import io.github.enerccio.llllm.model.tx.CommonTx;
import org.springframework.beans.factory.annotation.Autowired;

public class AIServiceImpl extends ExtendedContentServiceImpl<AI> implements AIService {

    @Autowired
    private OpenAICompatibleService openAICompatibleService;

    @Override
    protected Class<AI> getEntityClass() {
        return AI.class;
    }

    @Override
    @CommonTx
    public AI create(AIType aiType) throws Exception {
        return switch (aiType) {
            case OPEN_AI_COMPATIBLE -> openAICompatibleService.create();
            default -> throw new RuntimeException("Unhandled type " + aiType);
        };
    }

}
