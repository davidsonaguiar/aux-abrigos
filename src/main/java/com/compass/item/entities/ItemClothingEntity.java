package com.compass.item.entities;

import com.compass.item.enums.GenderItem;
import com.compass.item.enums.SizeItem;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@DiscriminatorValue("ROUPA")
@Data
@NoArgsConstructor
public class ItemClothingEntity extends ItemEntity {
    @NotNull(message = "Tamanho da Roupa é obrigatório (Infantil, PP, P, M, G, GG)")
    private SizeItem size;

    @NotNull(message = "Gênero da Roupa é obrigatório (M ou F)")
    private GenderItem gender;

    public ItemClothingEntity(Long id, String description, Integer quantity, SizeItem size) {
        super(id, description, quantity);
        this.size = size;
    }
}
