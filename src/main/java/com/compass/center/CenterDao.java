package com.compass.center;

import com.compass.common.dao.GenericDao;
import jakarta.persistence.EntityManager;

public class CenterDao extends GenericDao<CenterEntity, Long> {
    public CenterDao(EntityManager entityManager, Class<CenterEntity> classEntity) {
        super(entityManager, classEntity);
    }
}
