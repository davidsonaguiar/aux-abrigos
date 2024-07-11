package com.compass.item.enums;

public enum HygieneTypeItem {
    SABONETE("Sabonete"),
    ESCOVA_DE_DENTE("Escova de dente"),
    PASTA_DE_DENTE("Pasta de dente"),
    ABSORVENTE("Absorvente");

    private String type;

    HygieneTypeItem(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static HygieneTypeItem fromString(String text) {
        for (HygieneTypeItem type : HygieneTypeItem.values()) {
            if (type.type.equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }
}
