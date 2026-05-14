package io.github.enerccio.llllm.model.factories;

import io.github.enerccio.llllm.model.domain.Protocol;
import io.github.enerccio.llllm.model.service.MessageProcessorService;
import io.github.enerccio.llllm.model.service.messages.ChatCompletionMessageProcessorService;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageProcessorFactory {

    @Autowired
    private ChatCompletionMessageProcessorService chatCompletionMessageProcessorService;

    public MessageProcessorService getMessageProcessorService(Protocol protocol) {
        return switch (protocol.getProtocolType()) {
            case CHAT_COMPLETION -> chatCompletionMessageProcessorService;
            default -> throw new RuntimeException("Unsupported protocol type: " + protocol.getProtocolType());
        };
    }

}
