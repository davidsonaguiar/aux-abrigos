package com.compass.item;

import com.compass.common.dao.GenericDao;
import com.compass.item.entities.ItemEntity;
import com.compass.item.enums.CategoryItem;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ItemDao extends GenericDao<ItemEntity, Long> {
    public ItemDao(EntityManager entityManager, Class<ItemEntity> classEntity) {
        super(entityManager, classEntity);
    }

    public List<ItemEntity> findByCategory(CategoryItem category) {
        String categoryName = category.getCategory();
        return entityManager.createNativeQuery("SELECT * FROM TB_ITEMS WHERE category = :category", ItemEntity.class)
                .setParameter("category", categoryName)
                .getResultList();
    }
}
