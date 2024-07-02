package com.compass.item.enums;

public enum SexItem {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    UNISSEX("Unissex");

    private String sex;

    SexItem(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public static SexItem fromString(String text) {
        for(SexItem sex: SexItem.values()) {
            if(sex.sex.equalsIgnoreCase(text)) {
                return sex;
            }
        }
        return null;
    }
}
