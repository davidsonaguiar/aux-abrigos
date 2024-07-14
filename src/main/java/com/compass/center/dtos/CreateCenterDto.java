package com.compass.center.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCenterDto(
        @Size(message = "Nome do centro deve ter entre 3 e 100 caracteres", min = 3, max = 100) @NotBlank(message = "Nome do centro é obrigatório")
        String name,
        @Size(message = "Endereço do centro deve ter entre 3 e 100 caracteres", min = 3, max = 100) @NotBlank(message = "Endereço do centro é obrigatório")
        String address,
        @NotNull(message = "Capacidade do centro é obrigatória") @Min(message = "Capacidade mínima do centro é 100", value = 100)
        Integer capacity) {
}