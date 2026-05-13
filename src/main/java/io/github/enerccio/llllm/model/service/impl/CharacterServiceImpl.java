package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.CharacterInfo;
import io.github.enerccio.llllm.model.domain.Resource;
import io.github.enerccio.llllm.model.service.CharacterService;
import io.github.enerccio.llllm.model.service.ResourceService;

public class CharacterServiceImpl extends ExtendedContentServiceImpl<CharacterInfo> implements CharacterService {

    @Override
    protected Class<CharacterInfo> getEntityClass() {
        return CharacterInfo.class;
    }
    
}
