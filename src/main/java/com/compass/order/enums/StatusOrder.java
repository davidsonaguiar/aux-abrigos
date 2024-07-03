package com.compass.order.enums;

public enum StatusOrder {
    PEDENTE("Pendente"),
    RECUSADO("Recusado"),
    ACEITO("Aceito");

    private String status;

    StatusOrder(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static StatusOrder fromString(String status) {
        for (StatusOrder s : StatusOrder.values()) {
            if (s.status.equalsIgnoreCase(status)) {
                return s;
            }
        }
        return null;
    }
}
