package io.github.enerccio.llllm.model.service;

import io.github.enerccio.llllm.model.domain.ExtendedContentEntity;

public interface ExtendedContentService<T extends ExtendedContentEntity> {

    T save(T entity);
    void delete(T entity);
    T findById(Long id);
    T findByUuid(String uuid);

}
