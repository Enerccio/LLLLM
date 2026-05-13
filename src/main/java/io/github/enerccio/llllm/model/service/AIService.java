package io.github.enerccio.llllm.model.service;

import io.github.enerccio.llllm.model.domain.AI;
import io.github.enerccio.llllm.model.domain.collections.AIType;

public interface AIService extends ExtendedContentService<AI> {

    AI create(AIType aiType);

}
