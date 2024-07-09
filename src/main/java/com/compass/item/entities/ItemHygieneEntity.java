package com.compass.item.entities;

import com.compass.item.enums.HygieneTypeItem;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("HIGIENE")
@Data
@NoArgsConstructor
public class ItemHygieneEntity extends ItemEntity {
    @NotNull(message = "Tipo de higiene é obrigatório")
    private HygieneTypeItem hygieneType;

    public ItemHygieneEntity(Long id, String description, Integer quantity, HygieneTypeItem hygieneType) {
        super(id, description, quantity);
        this.hygieneType = hygieneType;
    }
}