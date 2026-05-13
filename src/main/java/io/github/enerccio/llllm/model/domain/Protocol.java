package io.github.enerccio.llllm.model.domain;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(indexes = {
        @Index(name = "protocol_is_deleted_idx", columnList = "is_deleted"),
        @Index(name = "protocol_user_id_ix", columnList = "userId")
})
public class Protocol extends ExtendedContentEntity {

    @Lob
    private String additionalParameters;

    private int maxTokens;
    private int replyTokens;

    public String getAdditionalParameters() {
        return additionalParameters;
    }

    public void setAdditionalParameters(String additionalParameters) {
        this.additionalParameters = additionalParameters;
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
}
