package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.ChatMessage;
import io.github.enerccio.llllm.model.service.ChatMessageService;

public class ChatMessageServiceImpl extends ExtendedContentServiceImpl<ChatMessage> implements ChatMessageService {

    @Override
    protected Class<ChatMessage> getEntityClass() {
        return ChatMessage.class;
    }
    
}
