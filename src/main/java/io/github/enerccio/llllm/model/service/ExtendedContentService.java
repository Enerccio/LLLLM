package io.github.enerccio.llllm.model.service;

import io.github.enerccio.llllm.model.domain.ExtendedContentEntity;

import java.util.List;

public interface ExtendedContentService<T extends ExtendedContentEntity> {

    T save(T entity) throws Exception;
    T softDelete(T entity) throws Exception;
    void delete(T entity);
    T findById(Long id);
    T findByUuid(String uuid);
    String getType();

    List<Long> findAll() throws Exception;
    List<Long> findAll(boolean forUser) throws Exception;
}
