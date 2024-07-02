package com.compass.item.enums;

public enum SizeItem {
    INFANTIL("Infantil"),
    PP("PP"),
    P("P"),
    M("M"),
    G("G"),
    GG("GG");

    private String size;

    SizeItem(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public static SizeItem fromString(String text) {
        for (SizeItem size : SizeItem.values()) {
            if (size.size.equalsIgnoreCase(text)) {
                return size;
            }
        }
        return null;
    }
}
