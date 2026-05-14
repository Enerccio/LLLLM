package io.github.enerccio.llllm.model.domain.ai;

import io.github.enerccio.llllm.model.domain.AI;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

@Entity
public class OpenAICompatible extends AI {

    @Lob
    private String uri;

    @Lob
    private String model;

    @Lob
    private String apiKey;

    @Lob
    private String additionalParameters;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAdditionalParameters() {
        return additionalParameters;
    }

    public void setAdditionalParameters(String additionalParameters) {
        this.additionalParameters = additionalParameters;
    }
}
