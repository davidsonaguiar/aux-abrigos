package com.compass.item.entities;

import com.compass.item.enums.Unit;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@DiscriminatorValue("ALIMENTO")
@Data
@NoArgsConstructor
public class ItemFoodEntity extends ItemEntity {
    @Temporal(TemporalType.DATE)
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    public ItemFoodEntity(Long id, String description, Integer quantity, LocalDate expirationDate, Unit unit) {
        super(id, description, quantity);
        this.expirationDate = expirationDate;
        this.unit = unit;
    }
}