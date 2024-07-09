package com.compass.item.entities;

import com.compass.common.exception.BadRequestException;
import com.compass.item.enums.CategoryItem;
import com.compass.item.services.ItemClothingService;
import com.compass.item.services.ItemFoodService;
import com.compass.item.services.ItemHygieneService;
import com.compass.item.services.ItemService;

public class ItemServiceFactory implements ItemServiceFactoryProtocol {
    private final ItemFoodService itemFoodService;
    private final ItemClothingService itemClothingService;
    private final ItemHygieneService itemHygieneService;

    public ItemServiceFactory(ItemFoodService itemFoodService, ItemClothingService itemClothingService, ItemHygieneService itemHygieneService) {
        this.itemFoodService = itemFoodService;
        this.itemClothingService = itemClothingService;
        this.itemHygieneService = itemHygieneService;
    }

    @Override
    public ItemService<?> createItemService(CategoryItem categoryItem) {
        switch (categoryItem.getCategory()) {
            case "ALIMENTO":
                return itemFoodService;
            case "ROUPA":
                return itemClothingService;
            case "HIGIENE":
                return itemHygieneService;
            default:
                throw new BadRequestException("Categoria de item inválida");
        }
    }
}
