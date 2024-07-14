package com.compass.item;

import com.compass.center.CenterService;
import com.compass.common.exception.ContentConflictException;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
import com.compass.item.dtos.ItemDto;
import com.compass.item.enums.CategoryItem;
import jakarta.persistence.NoResultException;

import java.util.List;

public class ItemService {
    private final ItemDao itemDao;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public ItemEntity findItemById(Long id) {
        try {
            return itemDao.findById(id);
        }
        catch (DaoException exception) {
            throw exception;
        }
    }

    public List<ItemEntity> findByCategory(CategoryItem category) {
        try {
            return itemDao.findByCategory(category);
        } catch (DaoException exception) {
            throw exception;
        }
    }

    public List<ItemEntity> findAll() {
        try {
            return itemDao.findAll();
        } catch (DaoException exception) {
            throw exception;
        }
    }

    public ItemEntity save(ItemEntity item) {
        Boolean existsCapacity = item.getCenter().existsCapacityForCategoryItem(item.getQuantity(), item.getCategory());
        if(!existsCapacity) throw new ContentConflictException("Capacidade para " + item.getCenter() + " do centro insuficiente");

        ItemEntity newItem = new ItemEntity();
        newItem.setDescription(newItem.getDescription());
        newItem.setCategory(newItem.getCategory());
        newItem.setQuantity(newItem.getQuantity());
        newItem.setCenter(newItem.getCenter());

        try {
            return itemDao.save(newItem);
        }
        catch (DaoException exception) {
            throw exception;
        }
    }

    public ItemEntity update(ItemDto item) {
        try {
            ItemEntity itemEntity = itemDao.findById(item.id());
            if(itemEntity == null) throw new NotFoundException("Item não encontrado");

            itemEntity.setDescription(item.description());
            itemEntity.setCategory(item.category());
            itemEntity.setQuantity(item.quantity());
            itemEntity.setSize(item.size());
            itemEntity.setGender(item.gender());
            itemEntity.setUnit(item.unit());
            itemEntity.setExpirationDate(item.expirationDate());
            itemEntity.setHygieneType(item.hygieneType());

            return itemDao.update(itemEntity);
        }
        catch (NotFoundException exception) {
            throw exception;
        }
        catch (DaoException exception) {
            throw exception;
        }
    }

    public ItemDto delete(Long id) {
        try {
            ItemEntity item = itemDao.findById(id);
            if(item == null) throw new NoResultException("Item não encontrado");
            itemDao.deleteById(id);
            return ItemDto.fromEntity(item);
        }
        catch (NoResultException exception) {
            throw new NotFoundException(exception.getMessage());
        }
        catch (DaoException exception) {
            throw exception;
        }
    }
}
