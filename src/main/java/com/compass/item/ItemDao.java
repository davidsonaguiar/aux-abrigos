package com.compass.item;

import com.compass.common.dao.GenericDao;
import com.compass.common.exception.DaoException;
import com.compass.item.enums.CategoryItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class ItemDao extends GenericDao<ItemEntity, Long> {
    public ItemDao(EntityManager entityManager, Class<ItemEntity> classEntity) {
        super(entityManager, classEntity);
    }

    public List<ItemEntity> findByCategory(CategoryItem category) {
        try {
            return entityManager.createQuery("SELECT i FROM ItemEntity i WHERE i.category = :category", ItemEntity.class)
                    .setParameter("category", category)
                    .getResultList();
        }
        catch (NoResultException exception) {
            return null;
        }
        catch (Exception exception) {
            throw new DaoException("Erro ao buscar itens por categoria", exception);
        }
    }
}
