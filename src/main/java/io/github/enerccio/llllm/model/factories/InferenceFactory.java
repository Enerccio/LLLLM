package io.github.enerccio.llllm.model.factories;

import io.github.enerccio.llllm.model.domain.AI;
import io.github.enerccio.llllm.model.service.InferenceProvider;
import io.github.enerccio.llllm.model.service.inference.OpenAIInferenceService;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class InferenceFactory {

    private final OpenAIInferenceService openAIInferenceService = new OpenAIInferenceService();

    public InferenceProvider getInferenceProvider(AI ai) {
        return switch (ai.getAiType()) {
            case OPEN_AI_COMPATIBLE -> openAIInferenceService;
            default -> throw new RuntimeException("Unsupported AI type: " + ai.getAiType());
        };
    }

    private static final class InstanceHolder {
        private static final InferenceFactory instance = new InferenceFactory();
    }

    public static InferenceFactory getInstance() {
        return InstanceHolder.instance;
    }

}
