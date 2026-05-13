package io.github.enerccio.llllm.model.domain;

import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Protocol extends ExtendedContentEntity {

    @Lob
    private String additionalParameters;

    public String getAdditionalParameters() {
        return additionalParameters;
    }

    public void setAdditionalParameters(String additionalParameters) {
        this.additionalParameters = additionalParameters;
    }
}
