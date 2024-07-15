package com.compass.order.dtos;

import com.compass.center.dtos.CenterResponseDto;
import com.compass.item.enums.CategoryItem;
import com.compass.item.enums.GenderItem;
import com.compass.item.enums.HygieneTypeItem;
import com.compass.item.enums.SizeItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequestDto(
        @NotNull(message = "Quantidade é obrigatória") @Min(value = 1, message = "Quantidade mínima é 1")
        Integer quantity,
        @NotNull(message = "O ID do abrigo é obrigatório")
        Long shelterId,
        @NotNull(message = "Categoria do item é obrigatória")
        CategoryItem categoryItem,
        @NotEmpty(message = "Centros são obrigatórios")
        List<CenterResponseDto> centersRequest,
        SizeItem sizeItem,
        GenderItem genderItem,
        HygieneTypeItem hygieneType
) {}
