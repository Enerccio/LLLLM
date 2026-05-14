package io.github.enerccio.llllm.model.domain;

import io.github.enerccio.llllm.model.domain.collections.ProtocolType;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(indexes = {
        @Index(name = "protocol_is_deleted_idx", columnList = "is_deleted"),
        @Index(name = "protocol_user_id_ix", columnList = "userId")
})
public abstract class Protocol extends ExtendedContentEntity {

    @Lob
    private String name;

    @Enumerated(value = EnumType.ORDINAL)
    private ProtocolType protocolType;

    private int maxTokens;
    private int replyTokens;

    private Boolean temperatureEnabled;
    private Double temperature;
    private Boolean topPEnabled;
    private Double topP;
    private Boolean frequencyPenaltyEnabled;
    private Double frequencyPenalty;
    private Boolean presencePenaltyEnabled;
    private Double presencePenalty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    public int getReplyTokens() {
        return replyTokens;
    }

    public void setReplyTokens(int replyTokens) {
        this.replyTokens = replyTokens;
    }

    public ProtocolType getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(ProtocolType protocolType) {
        this.protocolType = protocolType;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getTopP() {
        return topP;
    }

    public void setTopP(Double topP) {
        this.topP = topP;
    }

    public Double getFrequencyPenalty() {
        return frequencyPenalty;
    }

    public void setFrequencyPenalty(Double frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
    }

    public Double getPresencePenalty() {
        return presencePenalty;
    }

    public void setPresencePenalty(Double presencePenalty) {
        this.presencePenalty = presencePenalty;
    }

    public Boolean getTemperatureEnabled() {
        return temperatureEnabled == null ? Boolean.FALSE : temperatureEnabled;
    }

    public void setTemperatureEnabled(Boolean temperatureEnabled) {
        this.temperatureEnabled = temperatureEnabled;
    }

    public Boolean getTopPEnabled() {
        return topPEnabled == null ? Boolean.FALSE : topPEnabled;
    }

    public void setTopPEnabled(Boolean topPEnabled) {
        this.topPEnabled = topPEnabled;
    }

    public Boolean getFrequencyPenaltyEnabled() {
        return frequencyPenaltyEnabled == null ? Boolean.FALSE : frequencyPenaltyEnabled;
    }

    public void setFrequencyPenaltyEnabled(Boolean frequencyPenaltyEnabled) {
        this.frequencyPenaltyEnabled = frequencyPenaltyEnabled;
    }

    public Boolean getPresencePenaltyEnabled() {
        return presencePenaltyEnabled == null ? Boolean.FALSE : presencePenaltyEnabled;
    }

    public void setPresencePenaltyEnabled(Boolean presencePenaltyEnabled) {
        this.presencePenaltyEnabled = presencePenaltyEnabled;
    }
}
