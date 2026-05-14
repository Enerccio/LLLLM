package io.github.enerccio.llllm.model.factories;

import io.github.enerccio.llllm.model.domain.Protocol;
import io.github.enerccio.llllm.model.service.MessageProcessorService;
import io.github.enerccio.llllm.model.service.messages.ChatCompletionMessageProcessorService;

public class MessageProcessorFactory {

    private final ChatCompletionMessageProcessorService chatCompletionMessageProcessorService = new ChatCompletionMessageProcessorService();

    public MessageProcessorService getMessageProcessorService(Protocol protocol) {
        return switch (protocol.getProtocolType()) {
            case CHAT_COMPLETION -> chatCompletionMessageProcessorService;
            default -> throw new RuntimeException("Unsupported protocol type: " + protocol.getProtocolType());
        };
    }

    private static final class InstanceHolder {
        private static final MessageProcessorFactory instance = new MessageProcessorFactory();
    }

    public static MessageProcessorFactory getInstance() {
        return InstanceHolder.instance;
    }

}
