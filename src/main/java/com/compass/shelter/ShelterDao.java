package com.compass.shelter;

import com.compass.common.dao.GenericDao;
import jakarta.persistence.EntityManager;

public class ShelterDao extends GenericDao<ShelterEntity, Long> {
    public ShelterDao(EntityManager entityManager, Class<ShelterEntity> classEntity) {
        super(entityManager, classEntity);
    }
}
