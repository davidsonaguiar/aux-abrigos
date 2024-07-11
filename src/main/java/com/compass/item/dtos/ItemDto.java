package com.compass.item.dtos;

import com.compass.item.enums.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ItemDto(
        @NotNull(message = "Descrição do item é obrigatória") @Size(min = 3, max = 100, message = "Descrição do item deve ter entre 3 e 100 caracteres")
        String description,
        @NotNull(message = "Quantidade do item é obrigatória") @Size(min = 0, message = "Quantidade do item deve ser maior ou igual a 0")
        Integer quantity,
        @NotNull(message = "Categoria do item é obrigatória (Roupa, Alimento ou Higiene)")
        CategoryItem category,
        @NotNull(message = "Centro para qual o item será doado é obrigatório")
        Long centerId,
        SizeItem size,
        GenderItem gender,
        LocalDate expirationDate,
        Unit unit,
        HygieneTypeItem hygieneType
) {}
