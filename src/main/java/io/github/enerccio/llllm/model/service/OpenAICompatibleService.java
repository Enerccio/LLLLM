package io.github.enerccio.llllm.model.service;

import io.github.enerccio.llllm.model.domain.ai.OpenAICompatible;

public interface OpenAICompatibleService extends ExtendedContentService<OpenAICompatible> {

    OpenAICompatible create();

}
