package com.compass.center;

import com.compass.common.dao.GenericDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class CenterDao extends GenericDao<CenterEntity, Long> {
    public CenterDao(EntityManager entityManager, Class<CenterEntity> classEntity) {
        super(entityManager, classEntity);
    }

    public CenterEntity findByName(String name) {
        return entityManager.createQuery("SELECT c FROM CenterEntity c WHERE c.name = :name", CenterEntity.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
