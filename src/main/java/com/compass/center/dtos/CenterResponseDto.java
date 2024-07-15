package com.compass.center.dtos;

import com.compass.center.CenterEntity;
import com.compass.item.dtos.ItemDto;
import com.compass.item.enums.CategoryItem;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CenterResponseDto(
        @NotNull
        Long id,
        @NotNull
        String name,
        @NotNull
        String address,
        @NotNull
        Integer capacity,
        List<ItemDto> items
){
    public static List<CenterResponseDto> fromList(List<CenterEntity> centers) {
        return centers.stream()
                .map(center -> new CenterResponseDto(center.getId(), center.getName(), center.getAddress(), center.getCapacity(), ItemDto.fromEntities(center.getItems())))
                .toList();
    }

    public static CenterResponseDto fromEntity(CenterEntity center) {
        return new CenterResponseDto(center.getId(), center.getName(), center.getAddress(), center.getCapacity(), ItemDto.fromEntities(center.getItems()));
    }

    public Integer quantityCategory(CategoryItem categoryItem) {
        return items.stream()
                .filter(item -> item.category().equals(categoryItem))
                .mapToInt(item -> 1)
                .sum();
    }
}
