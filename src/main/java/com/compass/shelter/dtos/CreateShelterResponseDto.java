package com.compass.shelter.dtos;

import com.compass.shelter.ShelterEntity;
import jakarta.validation.constraints.NotNull;

public record CreateShelterResponseDto (
        @NotNull  Long id,
        @NotNull String name
){
    public static CreateShelterResponseDto fromEntity(ShelterEntity shelterEntity) {
        return new CreateShelterResponseDto(
                shelterEntity.getId(),
                shelterEntity.getName()
        );
    }
}
