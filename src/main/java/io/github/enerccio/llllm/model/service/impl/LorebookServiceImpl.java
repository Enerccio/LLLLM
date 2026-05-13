package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.Lorebook;
import io.github.enerccio.llllm.model.domain.Resource;
import io.github.enerccio.llllm.model.service.LorebookService;
import io.github.enerccio.llllm.model.service.ResourceService;

public class LorebookServiceImpl extends ExtendedContentServiceImpl<Lorebook> implements LorebookService {

    @Override
    protected Class<Lorebook> getEntityClass() {
        return Lorebook.class;
    }
    
}
