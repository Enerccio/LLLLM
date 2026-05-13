package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.CharacterInfo;
import io.github.enerccio.llllm.model.domain.Chat;
import io.github.enerccio.llllm.model.service.CharacterService;
import io.github.enerccio.llllm.model.service.ChatService;

public class ChatServiceImpl extends ExtendedContentServiceImpl<Chat> implements ChatService {

    @Override
    protected Class<Chat> getEntityClass() {
        return Chat.class;
    }
    
}
