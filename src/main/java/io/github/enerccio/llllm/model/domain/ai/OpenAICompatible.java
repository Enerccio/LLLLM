package io.github.enerccio.llllm.model.domain.ai;

import io.github.enerccio.llllm.model.domain.ExtendedContentEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(indexes = {
        @Index(name = "openaicompatible_is_deleted_idx", columnList = "is_deleted"),
        @Index(name = "openaicompatible_user_id_ix", columnList = "userId")
})
public class OpenAICompatible extends ExtendedContentEntity {

    @Lob
    private String uri;

    @Lob
    private String model;

    @Lob
    private String apiKey;

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
}
