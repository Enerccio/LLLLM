package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.ai.OpenAICompatible;
import io.github.enerccio.llllm.model.service.OpenAICompatibleService;

public class OpenAICompatibleServiceImpl extends ExtendedContentServiceImpl<OpenAICompatible> implements OpenAICompatibleService {

    @Override
    protected Class<OpenAICompatible> getEntityClass() {
        return OpenAICompatible.class;
    }

    @Override
    public OpenAICompatible create() {
        return new OpenAICompatible();
    }
}
