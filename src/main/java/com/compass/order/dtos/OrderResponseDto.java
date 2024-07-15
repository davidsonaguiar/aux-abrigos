package com.compass.order.dtos;

import com.compass.center.dtos.CenterResponseDto;
import com.compass.item.enums.*;
import com.compass.order.OrderEntity;
import com.compass.order.enums.StatusOrder;
import com.compass.shelter.dtos.ShelterResponseDto;

import java.time.LocalDate;
import java.util.List;

public record OrderResponseDto(
        Long id,
        LocalDate date,
        Integer quantity,
        StatusOrder status,
        ShelterResponseDto shelterId,
        List<CenterResponseDto> centersRequest,
        CategoryItem categoryItem,
        SizeItem sizeItem,
        GenderItem genderItem,
        HygieneTypeItem hygieneType
) {
    public static OrderResponseDto fromEntity(OrderEntity orderEntity) {
        return new OrderResponseDto(
                orderEntity.getId(),
                orderEntity.getDate(),
                orderEntity.getQuantity(),
                orderEntity.getStatus(),
                ShelterResponseDto.fromEntity(orderEntity.getShelter()),
                CenterResponseDto.fromList(orderEntity.getCentersRequested()),
                orderEntity.getCategoryItem(),
                orderEntity.getSizeItem(),
                orderEntity.getGenderItem(),
                orderEntity.getHygieneType()
        );
    }
}
