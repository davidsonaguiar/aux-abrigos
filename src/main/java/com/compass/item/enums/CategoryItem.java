package com.compass.item.enums;

public enum CategoryItem {
    ROUPA("ROUPA"),
    ALIMENTO("ALIMENTO"),
    HIGIENE("HIGIENE");

    private String category;

    CategoryItem(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public static CategoryItem fromString(String text) {
        for (CategoryItem type : CategoryItem.values()) {
            if (type.category.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }
}
