package com.compass.order_center;

import com.compass.common.dao.GenericDao;
import jakarta.persistence.EntityManager;

public class OrderCenterDao extends GenericDao<OrderCenterEntity, Long> {
    public OrderCenterDao(EntityManager entityManager, Class classEntity) {
        super(entityManager, classEntity);
    }
}
