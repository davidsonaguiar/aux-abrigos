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
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public Entity findById(ID id) {
        try {
            return entityManager.find(classEntity, id);
        }
        catch (NoResultException e) {
            return null;
        }
        catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Entity> findAll() {
        String query = "SELECT entity FROM " + classEntity.getSimpleName() + " entity";
        try {
            return entityManager
                    .createQuery(query, classEntity)
                    .getResultList();
        }
        catch (NoResultException e) {
            return List.of();
        }
        catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Entity update(Entity entity) throws NoResultException {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return entity;
    }

    @Override
    public void deleteById(ID id) throws NoResultException {
        entityManager.getTransaction().begin();
        Entity entity = entityManager.find(classEntity, id);
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }
}
