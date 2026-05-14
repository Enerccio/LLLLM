package io.github.enerccio.llllm.model.factories;

import io.github.enerccio.llllm.model.domain.AI;
import io.github.enerccio.llllm.model.service.InferenceProvider;
import io.github.enerccio.llllm.model.service.inference.OpenAIInferenceService;
import org.springframework.beans.factory.annotation.Autowired;

public class InferenceFactory {

    @Autowired
    private OpenAIInferenceService openAIInferenceService;

    public InferenceProvider getInferenceProvider(AI ai) {
        return switch (ai.getAiType()) {
            case OPEN_AI_COMPATIBLE -> openAIInferenceService;
            default -> throw new RuntimeException("Unsupported AI type: " + ai.getAiType());
        };
    }

    public OpenAIInferenceService getOpenAIInferenceService() {
        return openAIInferenceService;
    }

    public void setOpenAIInferenceService(OpenAIInferenceService openAIInferenceService) {
        this.openAIInferenceService = openAIInferenceService;
    }
}
