package com.compass.shelter.dtos;

import com.compass.shelter.ShelterEntity;
import jakarta.validation.constraints.*;

import java.util.ArrayList;

public record UpdateShelterRequestDto(
    @NotNull
    Long id,

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
    Integer occupancy) {

    public static UpdateShelterRequestDto fromEntity(ShelterEntity shelterEntity) {
        return new UpdateShelterRequestDto(
            shelterEntity.getId(),
            shelterEntity.getName(),
            shelterEntity.getAddress(),
            shelterEntity.getPhone(),
            shelterEntity.getEmail(),
            shelterEntity.getResponsible(),
            shelterEntity.getCapacityPeople(),
            shelterEntity.getOccupancy()
        );
    }

    public static ShelterEntity toEntity(UpdateShelterRequestDto updateShelterRequestDto) {
        return new ShelterEntity(
            updateShelterRequestDto.id(),
            updateShelterRequestDto.name(),
            updateShelterRequestDto.address(),
            updateShelterRequestDto.phone(),
            updateShelterRequestDto.email(),
            updateShelterRequestDto.responsible(),
            200,
            updateShelterRequestDto.capacityPeople(),
            updateShelterRequestDto.occupancy(),
            new ArrayList<>()
        );
    }
}
