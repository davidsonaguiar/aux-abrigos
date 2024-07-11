package com.compass.order;

import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;

import java.util.List;

public class OrderService {
    private final OrderDao orderDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public OrderEntity findOrderById(Long id) {
        try {
            return orderDao.findById(id);
        } catch (DaoException exception) {
            throw exception;
        }
    }

    public List<OrderEntity> findAll() {
        try {
            return orderDao.findAll();
        } catch (DaoException exception) {
            throw exception;
        }
    }

    public OrderEntity save(OrderEntity order) {
        try {
            return orderDao.save(order);
        }
        catch (NotFoundException exception) {
            throw new NotFoundException(exception.getMessage());
        }
        catch (DaoException exception) {
            throw exception;
        }
    }

    public OrderEntity update(OrderEntity order) {
        try {
            return orderDao.update(order);
        }
        catch (NotFoundException exception) {
            throw new NotFoundException(exception.getMessage());
        }
        catch (DaoException exception) {
            throw exception;
        }
    }
}
