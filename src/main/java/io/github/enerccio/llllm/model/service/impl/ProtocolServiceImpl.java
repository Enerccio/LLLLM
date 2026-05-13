package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.Protocol;
import io.github.enerccio.llllm.model.service.ProtocolService;

public abstract class ProtocolServiceImpl<T extends Protocol> extends ExtendedContentServiceImpl<T> implements ProtocolService<T> {

}
