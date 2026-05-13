package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.Protocol;
import io.github.enerccio.llllm.model.service.ProtocolService;

public class ProtocolServiceImpl extends ExtendedContentServiceImpl<Protocol> implements ProtocolService {

    @Override
    protected Class<Protocol> getEntityClass() {
        return Protocol.class;
    }

}
