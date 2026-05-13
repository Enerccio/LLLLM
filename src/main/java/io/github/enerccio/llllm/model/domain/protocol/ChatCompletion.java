package io.github.enerccio.llllm.model.domain.protocol;

import io.github.enerccio.llllm.model.domain.ExtendedContentEntity;
import io.github.enerccio.llllm.model.domain.Protocol;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(indexes = {
        @Index(name = "chatcompletion_is_deleted_idx", columnList = "is_deleted"),
})
public class ChatCompletion extends Protocol {


}
