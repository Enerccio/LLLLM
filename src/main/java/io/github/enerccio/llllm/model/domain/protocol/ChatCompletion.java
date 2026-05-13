package io.github.enerccio.llllm.model.domain.protocol;

import io.github.enerccio.llllm.model.domain.Protocol;
import jakarta.persistence.Entity;

@Entity
public class ChatCompletion extends Protocol {

    @Override
    public String getTypeName() {
        return "chat-completion";
    }

}
