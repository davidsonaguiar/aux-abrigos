package com.compass.item.services;

import com.compass.center.CenterService;
import com.compass.item.ItemDao;
import com.compass.item.dtos.ItemDto;
import com.compass.item.entities.ItemEntity;
import com.compass.item.enums.CategoryItem;

import java.util.List;

public abstract class ItemService<T extends ItemEntity> {
    protected final ItemDao itemDao;
    protected final CenterService centerService;

    public ItemService(ItemDao itemDao, CenterService centerService) {
        this.itemDao = itemDao;
        this.centerService = centerService;
    }

    public ItemEntity findItemById(Long id) {
        return itemDao.findById(id);
    }

    public List<ItemEntity> findByCategory(CategoryItem category) {
        return itemDao.findByCategory(category);
    }

    public void delete(Long id) {
        itemDao.deleteById(id);
    }

    public abstract T save(ItemDto item);
}
