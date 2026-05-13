package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.Configuration;
import io.github.enerccio.llllm.model.domain.ExtendedContentEntity;
import io.github.enerccio.llllm.model.domain.User;
import io.github.enerccio.llllm.model.service.ExtendedContentService;
import io.github.enerccio.llllm.model.tx.CommonTx;
import io.github.enerccio.llllm.model.tx.CommonTxReadOnly;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public abstract class ExtendedContentServiceImpl<T extends ExtendedContentEntity> implements ExtendedContentService<T> {

    @Autowired
    protected User currentUser;

    @Autowired
    protected Configuration configuration;

    private EntityManager entityManager;

    protected abstract Class<T> getEntityClass();

    protected String getEntityType() {
        return getEntityClass().getSimpleName();
    }

    @Override
    @CommonTx
    public T save(T entity) {
        if (entity.getUserId() == null) {
            if (currentUser != null) {
                entity.setUserId(currentUser.getId());
            }
        }

        if (entity.getUuid() == null) {
            entity.setUuid(UUID.randomUUID().toString());
        }

        if (entity.getId() == null) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    @Override
    @CommonTx
    public void delete(T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    @CommonTxReadOnly
    public T findById(Long id) {
        return entityManager.find(getEntityClass(), id);
    }

    @SuppressWarnings("SqlSourceToSinkFlow")
    @Override
    @CommonTxReadOnly
    public T findByUuid(String uuid) {
        List<T> results = entityManager.createQuery(
                "SELECT e FROM " + getEntityType() + " e WHERE e.uuid = :uuid",
                getEntityClass()
        ).setParameter("uuid", uuid).setMaxResults(1).getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return results.getFirst();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
