package io.github.enerccio.llllm.model.domain;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(indexes = {
        @Index(name = "protocol_is_deleted_idx", columnList = "is_deleted"),
        @Index(name = "protocol_user_id_ix", columnList = "userId")
})
public abstract class Protocol extends ExtendedContentEntity {

    @Lob
    private String name;

    private int maxTokens;
    private int replyTokens;

    public String getTypeName() {
        return "undefined";
    }

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

}
