package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.ai.OpenAICompatible;
import io.github.enerccio.llllm.model.domain.collections.AIType;
import io.github.enerccio.llllm.model.service.OpenAICompatibleService;
import io.github.enerccio.llllm.model.tx.CommonTx;

public class OpenAICompatibleServiceImpl extends ExtendedContentServiceImpl<OpenAICompatible> implements OpenAICompatibleService {

    @Override
    protected Class<OpenAICompatible> getEntityClass() {
        return OpenAICompatible.class;
    }

    @Override
    @CommonTx
    public OpenAICompatible create() throws Exception {
        OpenAICompatible ai = new OpenAICompatible();
        ai.setAiType(AIType.OPEN_AI_COMPATIBLE);
        return save(ai);
    }
}
