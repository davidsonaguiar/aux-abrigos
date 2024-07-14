package com.compass.common.dao;

import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
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
            throw new DaoException("Erro ao salvar entidade.", exception);
        }
    }

    @Override
    public Entity findById(ID id) {
        try {
            return entityManager.find(classEntity, id);
        }
        catch (Exception exception) {
            throw new DaoException("Erro ao buscar recurso.", exception);
        }
    }

    @Override
    public List<Entity> findAll() {
        try {
            String query = "SELECT entity FROM " + classEntity.getSimpleName() + " entity";
            return entityManager
                    .createQuery(query, classEntity)
                    .getResultList();
        }
        catch (Exception exception) {
            throw new DaoException("Erro ao buscar Itens.", exception);
        }
    }

    @Override
    public Entity update(Entity entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.flush();
            entityManager.clear();
            entityManager.getTransaction().commit();
            return entity;
        }
        catch (Exception exception) {
            entityManager.getTransaction().rollback();
            throw new DaoException("Erro ao atualizar Item.", exception);
        }
    }

    @Override
    public void deleteById(ID id) {
        try {
            entityManager.getTransaction().begin();
            Entity entityFound = entityManager.find(classEntity, id);
            entityManager.remove(entityFound);
            entityManager.flush();
            entityManager.clear();
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            entityManager.getTransaction().rollback();
            throw new DaoException("Erro ao deletar Item.", exception);
        }
    }
}
