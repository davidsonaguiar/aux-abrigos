package com.compass.item.enums;

public enum UnitItem {
    UN("UN"),
    KG("KG"),
    G("G"),
    MG("MG"),
    LT("LT"),
    ML("ML");

    private String unit;

    UnitItem(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public static UnitItem fromString(String text) {
        for (UnitItem unit : UnitItem.values()) {
            if (unit.unit.equalsIgnoreCase(text)) {
                return unit;
            }
        }
        return null;
    }
}
