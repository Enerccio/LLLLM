package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.Configuration;
import io.github.enerccio.llllm.model.domain.ExtendedContentEntity;
import io.github.enerccio.llllm.model.domain.User;
import io.github.enerccio.llllm.model.service.ExtendedContentService;
import io.github.enerccio.llllm.model.service.UserService;
import io.github.enerccio.llllm.model.tx.CommonTx;
import io.github.enerccio.llllm.model.tx.CommonTxReadOnly;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ScopeNotActiveException;

import java.util.List;
import java.util.UUID;

public abstract class ExtendedContentServiceImpl<T extends ExtendedContentEntity> implements ExtendedContentService<T> {

    @Autowired
    protected User currentUser;

    @Autowired
    protected UserService userService;

    @Autowired
    protected Configuration configuration;

    private EntityManager entityManager;

    protected abstract Class<T> getEntityClass();

    protected String getEntityType() {
        return getEntityClass().getSimpleName();
    }

    @Override
    public String getType() {
        return getEntityType();
    }

    @Override
    @CommonTx
    public T save(T entity) throws Exception {
        if (entity.getOwner() == null) {
            try {
                if (userService == null && this instanceof UserService)
                    userService = (UserService) this;

                if (currentUser.getId() != null) {
                    entity.setOwner(userService.findById(currentUser.getId()));
                }
            } catch (ScopeNotActiveException e) {
                // ignore, this only happens during the boot when new root user is created
            }
        }

        if (entity.getUuid() == null) {
            entity.setUuid(UUID.randomUUID().toString());
        }

        if (entity.getId() == null) {
            entityManager.persist(entity);
            entityManager.flush();
            return entity;
        } else {
            entity = entityManager.merge(entity);
            entityManager.flush();
            return entity;
        }
    }

    @Override
    @CommonTx
    public void delete(T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @CommonTx
    @Override
    public T softDelete(T entity) throws Exception {
        entity.setDeleted(true);
        return save(entity);
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

    @Override
    @CommonTxReadOnly
    public List<Long> findAll() throws Exception {
        return findAll(true);
    }

    @SuppressWarnings("SqlSourceToSinkFlow")
    @Override
    @CommonTxReadOnly
    public List<Long> findAll(boolean forUser) throws Exception {
        TypedQuery<Long> query = entityManager.createQuery("SELECT e.id FROM " + getEntityType() + " e WHERE e.owner.id = ?1 AND e.deleted = false", Long.class)
                .setParameter(1, currentUser.getId());

        return query.getResultList();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
