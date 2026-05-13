package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.Protocol;
import io.github.enerccio.llllm.model.service.ProtocolBaseService;

public abstract class ProtocolBaseServiceImpl<T extends Protocol> extends ExtendedContentServiceImpl<T> implements ProtocolBaseService<T> {

}
