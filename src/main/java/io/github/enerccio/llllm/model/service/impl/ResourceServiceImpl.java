package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.Resource;
import io.github.enerccio.llllm.model.service.ResourceService;

public class ResourceServiceImpl extends ExtendedContentServiceImpl<Resource> implements ResourceService {

    @Override
    protected Class<Resource> getEntityClass() {
        return Resource.class;
    }
    
}
