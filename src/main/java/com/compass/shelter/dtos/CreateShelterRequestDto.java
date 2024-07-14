package com.compass.shelter.dtos;

import com.compass.item.ItemEntity;
import com.compass.shelter.ShelterEntity;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public record CreateShelterRequestDto(
        @NotBlank(message = "Nome do abrigo é obrigatório")
        @Size(min = 3, max = 100, message = "Nome do abrigo deve ter entre 3 e 100 caracteres")
        String name,

        @NotBlank(message = "Endereço do abrigo é obrigatório")
        @Size(min = 3, max = 100, message = "Endereço do abrigo deve ter entre 3 e 100 caracteres")
        String address,

        @NotBlank(message = "Telefone do abrigo é obrigatório")
        @Size(min = 11, max = 11, message = "Telefone do abrigo deve ter 11 caracteres")
        String phone,

        @NotBlank(message = "E-mail do abrigo é obrigatório")
        @Email(message = "E-mail do abrigo inválido")
        String email,

        @NotBlank(message = "Responsável do abrigo é obrigatório")
        @Size(min = 3, max = 100, message = "Responsável do abrigo deve ter entre 3 e 100 caracteres")
        String responsible,

        @NotNull(message = "Capacidade de pessoas do abrigo é obrigatória")
        @Min(value = 1, message = "Capacidade do abrigo deve ser maior que 0")
        Integer capacityPeople,

        @NotNull(message = "Ocupação do abrigo é obrigatória")
        @Min(value = 0, message = "Ocupação do abrigo deve ser maior ou igual a 0")
        Integer occupancy
) {
    public static CreateShelterRequestDto fromEntity(ShelterEntity shelterEntity) {
        return new CreateShelterRequestDto(
                shelterEntity.getName(),
                shelterEntity.getAddress(),
                shelterEntity.getPhone(),
                shelterEntity.getEmail(),
                shelterEntity.getResponsible(),
                shelterEntity.getCapacityPeople(),
                shelterEntity.getOccupancy()
        );
    }

    public static List<CreateShelterRequestDto> fromEntities(List<ShelterEntity> shelterEntities) {
        return shelterEntities.stream()
                .map(CreateShelterRequestDto::fromEntity)
                .toList();
    }

    public static ShelterEntity toEntity(CreateShelterRequestDto createShelterRequestDto) {
        return new ShelterEntity(
                null,
                createShelterRequestDto.name(),
                createShelterRequestDto.address(),
                createShelterRequestDto.phone(),
                createShelterRequestDto.email(),
                createShelterRequestDto.responsible(),
                200,
                createShelterRequestDto.capacityPeople(),
                createShelterRequestDto.occupancy(),
                new ArrayList<>());
    }
}
