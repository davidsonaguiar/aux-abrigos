package com.compass.order;

import com.compass.common.dao.GenericDao;
import com.compass.common.exception.DaoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;

public class OrderDao extends GenericDao<OrderEntity, Long> {
    public OrderDao(EntityManager entityManager, Class<OrderEntity> classEntity) {
        super(entityManager, classEntity);
    }

    public List<OrderEntity> findByShelter(Long shelterId) {
        try {
            return entityManager.createQuery("SELECT o FROM OrderEntity o WHERE o.shelter.id = :shelterId", OrderEntity.class)
                    .setParameter("shelterId", shelterId)
                    .getResultList();
        }
        catch (Exception exception) {
            throw new DaoException("Erro ao buscar pedidos por abrigo.", exception);
        }
    }

    public List<OrderEntity> findByCenter(long id) throws DaoException {
        try {
            Query query = entityManager.createQuery(
                    "SELECT o FROM OrderEntity o JOIN o.orderCenter oc WHERE oc.center.id = :centerId",
                    OrderEntity.class);
            query.setParameter("centerId", id);
            return query.getResultList();
        } catch (Exception exception) {
            throw new DaoException("Erro ao buscar pedidos por centro de doação.", exception);
        }
    }
}
