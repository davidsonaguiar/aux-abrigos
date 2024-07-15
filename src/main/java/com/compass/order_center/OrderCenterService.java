package com.compass.order_center;

import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
import com.compass.order.enums.StatusOrder;
import jakarta.persistence.NoResultException;


public class OrderCenterService {
    private final OrderCenterDao orderCenterDao;

    public OrderCenterService(OrderCenterDao orderCenterDao) {
        this.orderCenterDao = orderCenterDao;
    }

    public OrderCenterEntity save(OrderCenterEntity orderCenter) throws NotFoundException, DaoException {
        try {
            orderCenter.setStatus(StatusOrder.PEDENTE);
            return orderCenterDao.save(orderCenter);
        } catch (NoResultException exception) {
            throw new NotFoundException(exception.getMessage());
        }
    }

    public OrderCenterEntity update(OrderCenterEntity orderCenter) {
        try {
            return orderCenterDao.update(orderCenter);
        } catch (NoResultException exception) {
            throw new NotFoundException(exception.getMessage());
        }
    }
}
