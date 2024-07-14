package com.compass.shelter.dtos;

import com.compass.item.dtos.ItemDto;
import com.compass.shelter.ShelterEntity;
import jakarta.validation.constraints.*;

import java.util.List;

public record ShelterResponseDto(
        @NotNull(message = "Id do abrigo é obrigatório")
        Long id,

        @NotBlank(message = "Nome do abrigo é obrigatório")
        String name,

        @NotBlank(message = "Endereço do abrigo é obrigatório")
        String address,

        @NotBlank(message = "Telefone do abrigo é obrigatório")
        String phone,

        @NotBlank(message = "E-mail do abrigo é obrigatório")
        String email,

        @NotBlank(message = "Responsável do abrigo é obrigatório")
        String responsible,

        @NotNull(message = "Capacidade de itens do abrigo é obrigatória")
        Integer capacityItem,

        @NotNull(message = "Capacidade de pessoas do abrigo é obrigatória")
        Integer capacityPeople,

        @NotNull(message = "Ocupação do abrigo é obrigatória")
        Integer occupancy,

        List<ItemDto> items) {

        public static ShelterResponseDto fromEntity(ShelterEntity shelter) {
                return new ShelterResponseDto(
                        shelter.getId(),
                        shelter.getName(),
                        shelter.getAddress(),
                        shelter.getPhone(),
                        shelter.getEmail(),
                        shelter.getResponsible(),
                        shelter.getCapacityItem(),
                        shelter.getCapacityPeople(),
                        shelter.getOccupancy(),
                        ItemDto.fromEntities(shelter.getItems()));
        }

        public static List<ShelterResponseDto> fromEntityList(List<ShelterEntity> shelters) {
                return shelters.stream()
                        .map(ShelterResponseDto::fromEntity)
                        .toList();
        }
}
