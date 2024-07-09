package com.compass.item.entities;

import com.compass.item.enums.CategoryItem;
import com.compass.item.services.ItemService;

public interface ItemServiceFactoryProtocol {
    ItemService<?> createItemService(CategoryItem categoryItem);
}
