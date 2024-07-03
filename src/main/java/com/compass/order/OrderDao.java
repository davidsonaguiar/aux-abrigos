package com.compass.order;

import com.compass.common.dao.GenericDao;
import jakarta.persistence.EntityManager;

public class OrderDao extends GenericDao<OrderEntity, Long> {
    public OrderDao(EntityManager entityManager, Class<OrderEntity> classEntity) {
        super(entityManager, classEntity);
    }
}
