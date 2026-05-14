package io.github.enerccio.llllm.model.domain;

import io.github.enerccio.llllm.model.domain.ai.OpenAICompatible;
import io.github.enerccio.llllm.model.domain.collections.AIType;
import io.github.enerccio.llllm.model.domain.collections.AudioFormat;
import io.github.enerccio.llllm.model.domain.collections.ReasoningEffort;
import io.github.enerccio.llllm.synthesis.domain.VoiceMatrix;
import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(indexes = {
        @Index(name = "ai_is_deleted_idx", columnList = "is_deleted"),
        @Index(name = "ai_type_idx", columnList = "ai_type"),
        @Index(name = "ai_user_id_ix", columnList = "userId")
})
@Inheritance(strategy = InheritanceType.JOINED)
public class AI extends ExtendedContentEntity {

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "ai_type")
    private AIType aiType;

    @Lob
    private String name;

    @Lob
    private String capabilities;

    @Lob
    private String jailbreak;

    private Boolean needsJailbreak;

    // reasoning

    private Boolean enabledReasoning;

    @Enumerated(EnumType.STRING)
    private ReasoningEffort reasoningEffort;

    private Integer maxCompletionTokens;

    // voice ingest

    private int maxVoiceClipDuration = 1;

    @Enumerated(EnumType.STRING)
    private AudioFormat audioFormat;

    private Integer samplingRate;

    // voice output

    @Transient
    private VoiceMatrix voiceMatrixInstance;

    @Lob
    private String voiceMatrix;

    @Enumerated(EnumType.STRING)
    private AudioFormat audioOutputFormat;

    private Integer samplingOutputRate;

    @Lob
    private String audioEngine;

    @Lob
    private String fallbackVoice;

    @Lob
    private Double voiceSpeed;

    // misc?

    private Boolean dropSystemMessages;

    public AIType getAiType() {
        return aiType;
    }

    public void setAiType(AIType aiType) {
        this.aiType = aiType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxVoiceClipDuration() {
        return maxVoiceClipDuration;
    }

    public void setMaxVoiceClipDuration(int maxDuration) {
        this.maxVoiceClipDuration = maxDuration;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public String getJailbreak() {
        return jailbreak;
    }

    public void setJailbreak(String jailbreak) {
        this.jailbreak = jailbreak;
    }

    public Boolean getNeedsJailbreak() {
        return needsJailbreak == null ? Boolean.FALSE : needsJailbreak;
    }

    public void setNeedsJailbreak(Boolean needsJailbreak) {
        this.needsJailbreak = needsJailbreak;
    }

    public Boolean getEnabledReasoning() {
        return enabledReasoning == null ? Boolean.FALSE : enabledReasoning;
    }

    public void setEnabledReasoning(Boolean enabledReasoning) {
        this.enabledReasoning = enabledReasoning;
    }

    public ReasoningEffort getReasoningEffort() {
        return reasoningEffort;
    }

    public void setReasoningEffort(ReasoningEffort reasoningEffort) {
        this.reasoningEffort = reasoningEffort;
    }

    public OpenAICompatible getOpenAICompatible() {
        if (this instanceof OpenAICompatible)
            return (OpenAICompatible) this;
        throw new IllegalStateException(String.format(
                "Execution failure: AI entity ID [%s] (Type: %s) was expected to be OpenAICompatible, but it is not.",
                getId(), this.getClass().getSimpleName()
        ));
    }

    public VoiceMatrix getVoiceMatrix() {
        if (this.voiceMatrixInstance == null) {
            this.voiceMatrixInstance = StringUtils.isBlank(voiceMatrix)
                    ? VoiceMatrix.createEmptyMatrix()
                    : VoiceMatrix.deserialize(voiceMatrix);
        }
        return this.voiceMatrixInstance;
    }

    public void setVoiceMatrix(VoiceMatrix voiceMatrix) {
        this.voiceMatrixInstance = null;
        this.voiceMatrix = voiceMatrix == null ? null : VoiceMatrix.serialize(voiceMatrix);
    }

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(AudioFormat audioFormat) {
        this.audioFormat = audioFormat;
    }

    public Integer getSamplingRate() {
        return samplingRate;
    }

    public void setSamplingRate(Integer samplingRate) {
        this.samplingRate = samplingRate;
    }

    public void setVoiceMatrix(String voiceMatrix) {
        this.voiceMatrix = voiceMatrix;
    }

    public AudioFormat getAudioOutputFormat() {
        return audioOutputFormat;
    }

    public void setAudioOutputFormat(AudioFormat audioOutputFormat) {
        this.audioOutputFormat = audioOutputFormat;
    }

    public Integer getSamplingOutputRate() {
        return samplingOutputRate;
    }

    public void setSamplingOutputRate(Integer samplingOutputRate) {
        this.samplingOutputRate = samplingOutputRate;
    }

    public String getAudioEngine() {
        return audioEngine;
    }

    public void setAudioEngine(String audioEngine) {
        this.audioEngine = audioEngine;
    }

    public String getFallbackVoice() {
        return fallbackVoice;
    }

    public void setFallbackVoice(String fallbackVoice) {
        this.fallbackVoice = fallbackVoice;
    }

    public Double getVoiceSpeed() {
        return voiceSpeed;
    }

    public void setVoiceSpeed(Double voiceSpeed) {
        this.voiceSpeed = voiceSpeed;
    }

    public Boolean getDropSystemMessages() {
        return dropSystemMessages == null ? Boolean.FALSE : dropSystemMessages;
    }

    public void setDropSystemMessages(Boolean dropSystemMessages) {
        this.dropSystemMessages = dropSystemMessages;
    }

    public Integer getMaxCompletionTokens() {
        return maxCompletionTokens;
    }

    public void setMaxCompletionTokens(Integer maxCompletionTokens) {
        this.maxCompletionTokens = maxCompletionTokens;
    }
}
