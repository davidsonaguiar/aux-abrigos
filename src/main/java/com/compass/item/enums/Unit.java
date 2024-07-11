package com.compass.item.enums;

public enum Unit {
    UNIDADE("UN"),
    QUILOGRAMA("KG"),
    GRAMA("G"),
    MILIGRAMA("MG"),
    LITRO("LT"),
    MILILITRO("ML");

    private String unit;

    Unit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public static Unit fromString(String text) {
        for (Unit unit : Unit.values()) {
            if (unit.unit.equalsIgnoreCase(text)) {
                return unit;
            }
        }
        return null;
    }
}
