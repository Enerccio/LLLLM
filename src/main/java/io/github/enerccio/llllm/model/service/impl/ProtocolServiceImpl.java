package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.Protocol;
import io.github.enerccio.llllm.model.domain.collections.ProtocolType;
import io.github.enerccio.llllm.model.domain.protocol.ChatCompletion;
import io.github.enerccio.llllm.model.service.ProtocolService;
import io.github.enerccio.llllm.model.tx.CommonTx;

public class ProtocolServiceImpl extends ExtendedContentServiceImpl<Protocol> implements ProtocolService {

    @Override
    protected Class<Protocol> getEntityClass() {
        return Protocol.class;
    }

    @Override
    @CommonTx
    public Protocol create(ProtocolType type) throws Exception {
        Protocol protocol = switch (type) {
            case CHAT_COMPLETION -> {
                ChatCompletion cc = new ChatCompletion();
                cc.setProtocolType(ProtocolType.CHAT_COMPLETION);
                yield cc;
            }
            default -> throw new IllegalStateException("Unknown type: " + type);
        };

        protocol.setFrequencyPenaltyEnabled(true);
        protocol.setPresencePenaltyEnabled(true);
        protocol.setTemperatureEnabled(true);
        protocol.setTopPEnabled(true);

        // default values
        protocol.setTemperature(0.75);
        protocol.setTopP(0.9);
        protocol.setFrequencyPenalty(0.4);
        protocol.setPresencePenalty(0.5);
        protocol.setMaxTokens(8096);
        protocol.setReplyTokens(2048);

        return save(protocol);
    }
}
