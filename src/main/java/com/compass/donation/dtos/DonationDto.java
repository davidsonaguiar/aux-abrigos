package com.compass.donation.dtos;

import com.compass.item.dtos.ItemDto;
import jakarta.persistence.Temporal;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.List;

public record DonationDto(
        @PastOrPresent(message = "Data da doação deve ser a atual") @NotNull(message = "Data da doação é obrigatória")
        LocalDate date,
        @NotNull(message = "Centro de doação é obrigatório")
        Long centerId,
        @NotEmpty(message = "A lista de itens da doação não pode ser vazia ou nula")
        List<ItemDto> items
) {}
