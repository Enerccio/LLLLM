package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.protocol.ChatCompletion;
import io.github.enerccio.llllm.model.service.ChatCompletionService;

public class ChatCompletionServiceImpl extends ProtocolBaseServiceImpl<ChatCompletion> implements ChatCompletionService {

    @Override
    protected Class<ChatCompletion> getEntityClass() {
        return ChatCompletion.class;
    }

}
