package io.github.enerccio.llllm.model.domain.ai;

import io.github.enerccio.llllm.model.domain.ExtendedContentEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(indexes = {
        @Index(name = "openaicompatible_is_deleted_idx", columnList = "is_deleted"),
})
public class OpenAICompatible extends ExtendedContentEntity {


}
