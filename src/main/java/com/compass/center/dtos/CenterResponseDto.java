package com.compass.center.dtos;

import com.compass.center.CenterEntity;
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
        Integer capacity
){
    public static List<CenterResponseDto> fromList(List<CenterEntity> centers) {
        return centers.stream()
                .map(center -> new CenterResponseDto(center.getId(), center.getName(), center.getAddress(), center.getCapacity()))
                .toList();
    }

    public static CenterResponseDto fromEntity(CenterEntity center) {
        return new CenterResponseDto(center.getId(), center.getName(), center.getAddress(), center.getCapacity());
    }
}
