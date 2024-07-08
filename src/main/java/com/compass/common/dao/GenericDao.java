package com.compass.common.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public abstract class GenericDao<Entity, ID> implements DaoProtocol<Entity, ID>{
    protected final EntityManager entityManager;
    private final Class<Entity> classEntity;

    public GenericDao(EntityManager entityManager, Class<Entity> classEntity) {
        this.entityManager = entityManager;
        this.classEntity = classEntity;
    }

    @Override
    public Entity save(Entity entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return entity;
        }
        catch (Exception exception) {
            entityManager.getTransaction().rollback();
            throw exception;
        }
    }

    @Override
    public Entity findById(ID id) {
        return entityManager.find(classEntity, id);
    }

    @Override
    public List<Entity> findAll() {
        String query = "SELECT entity FROM " + classEntity.getSimpleName() + " entity";
        return entityManager
                .createQuery(query, classEntity)
                .getResultList();
    }

    @Override
    public Entity update(Entity entity) {
        try {
            entityManager.getTransaction().begin();
            Entity entityFound = entityManager.find(classEntity, entity);
            if (entityFound == null) throw new NoResultException("Entity not found");
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
            return entity;
        }
        catch (Exception exception) {
            entityManager.getTransaction().rollback();
            throw exception;
        }
    }

    @Override
    public void deleteById(ID id) {
        try {
            entityManager.getTransaction().begin();
            Entity entityFound = entityManager.find(classEntity, id);
            if (entityFound == null) throw new NoResultException("Entity not found");
            entityManager.remove(entityFound);
            entityManager.getTransaction().commit();
        }
        catch (Exception exception) {
            entityManager.getTransaction().rollback();
            throw exception;
        }
    }
}
