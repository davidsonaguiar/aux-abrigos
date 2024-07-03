package com.compass.item;

import com.compass.common.dao.GenericDao;
import jakarta.persistence.EntityManager;

public class ItemDao extends GenericDao<ItemEntity, Long> {
    public ItemDao(EntityManager entityManager, Class<ItemEntity> classEntity) {
        super(entityManager, classEntity);
    }
}
