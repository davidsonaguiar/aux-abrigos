package com.compass.order.dtos;

import com.compass.item.enums.*;
import com.compass.order.OrderEntity;
import com.compass.order_center.OrderCenterEntity;
import com.compass.shelter.dtos.ShelterResponseDto;

import java.time.LocalDate;
import java.util.List;

public record OrderResponseDto(
        Long id,
        LocalDate date,
        Integer quantity,
        ShelterResponseDto shelterId,
        CategoryItem categoryItem,
        SizeItem sizeItem,
        GenderItem genderItem,
        HygieneTypeItem hygieneType,
        List<OrderCenterEntity> orderCenters
) {
    public static OrderResponseDto fromEntity(OrderEntity orderEntity) {
        return new OrderResponseDto(
                orderEntity.getId(),
                orderEntity.getDate(),
                orderEntity.getQuantity(),
                ShelterResponseDto.fromEntity(orderEntity.getShelter()),
                orderEntity.getCategoryItem(),
                orderEntity.getSizeItem(),
                orderEntity.getGenderItem(),
                orderEntity.getHygieneType(),
                orderEntity.getOrderCenter()
        );
    }

    public static List<OrderResponseDto> fromEntities(List<OrderEntity> orders) {
        return orders.stream()
                .map(OrderResponseDto::fromEntity)
                .toList();
    }
}
