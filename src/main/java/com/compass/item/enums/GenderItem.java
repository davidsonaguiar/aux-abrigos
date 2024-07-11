package com.compass.item.enums;

public enum GenderItem {
    MASCULINO("M"),
    FEMININO("F");

    private String gender;

    GenderItem(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public static GenderItem fromString(String text) {
        for(GenderItem gender: GenderItem.values()) {
            if(gender.gender.equalsIgnoreCase(text)) {
                return gender;
            }
        }
        return null;
    }
}
