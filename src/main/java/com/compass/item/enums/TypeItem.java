package com.compass.item.enums;

public enum TypeItem {
    ROUPA("Roupa"),
    ALIMENTO("Alimento"),
    HIGIENE("Higiene");

    private String type;

    TypeItem(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static TypeItem fromString(String text) {
        for (TypeItem type : TypeItem.values()) {
            if (type.type.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }
}
