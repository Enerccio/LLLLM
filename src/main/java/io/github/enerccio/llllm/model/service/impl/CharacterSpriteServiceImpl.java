package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.CharacterSprite;
import io.github.enerccio.llllm.model.domain.Chat;
import io.github.enerccio.llllm.model.service.CharacterSpriteService;
import io.github.enerccio.llllm.model.service.ChatService;

public class CharacterSpriteServiceImpl extends ExtendedContentServiceImpl<CharacterSprite> implements CharacterSpriteService {

    @Override
    protected Class<CharacterSprite> getEntityClass() {
        return CharacterSprite.class;
    }
    
}
