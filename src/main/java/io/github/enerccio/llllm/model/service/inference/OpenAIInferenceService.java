package io.github.enerccio.llllm.model.service.inference;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.models.Model;
import com.openai.models.models.ModelListPage;
import io.github.enerccio.llllm.model.domain.AI;
import io.github.enerccio.llllm.model.service.InferenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OpenAIInferenceService implements InferenceProvider {
    private static final Logger log = LoggerFactory.getLogger(OpenAIInferenceService.class);

    @Override
    public List<Model> getModels(AI ai) throws Exception {
        OpenAIClient client = OpenAIOkHttpClient.builder()
                        .baseUrl(ai.getOpenAICompatible().getUri())
                        .apiKey(ai.getOpenAICompatible().getApiKey())
                        .build();

        log.info("Testing connection to OpenAI...");

        ModelListPage modelList = client.models().list();
        List<Model> models = modelList.data();

        if (models.isEmpty()) {
            log.info("Connection successful, but no models were returned.");
        } else {
            log.info("Connection successful!");
        }

        return models;
    }
}
