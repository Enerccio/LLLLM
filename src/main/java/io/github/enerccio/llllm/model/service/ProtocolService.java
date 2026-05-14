package io.github.enerccio.llllm.model.service;

import io.github.enerccio.llllm.model.domain.Protocol;
import io.github.enerccio.llllm.model.domain.collections.ProtocolType;

public interface ProtocolService extends ExtendedContentService<Protocol> {

    Protocol create(ProtocolType type) throws Exception;

}
