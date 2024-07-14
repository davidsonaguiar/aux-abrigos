package com.compass.shelter;

import com.compass.common.dao.GenericDao;
import jakarta.persistence.EntityManager;

public class ShelterDao extends GenericDao<ShelterEntity, Long> {
    public ShelterDao(EntityManager entityManager, Class<ShelterEntity> classEntity) {
        super(entityManager, classEntity);
    }

    public ShelterEntity findByName(String name) {
        return entityManager.createQuery("SELECT s FROM ShelterEntity s WHERE s.name = :name", ShelterEntity.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
