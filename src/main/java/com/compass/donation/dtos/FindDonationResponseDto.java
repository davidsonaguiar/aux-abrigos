package com.compass.donation.dtos;

import com.compass.donation.DonationEntity;
import com.compass.item.ItemEntity;
import com.compass.item.dtos.ItemDto;
import jakarta.validation.constraints.NotNull;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
import java.util.List;

public record FindDonationResponseDto(
        @NotNull Long donationId,
        @NotNull String centerName,
        @NotNull LocalDate date,
        @NotNull List<ItemDto> items
        ) {

    public static FindDonationResponseDto fromEntity(DonationEntity donation) {
        return new FindDonationResponseDto(
                donation.getId(),
                donation.getCenter().getName(),
                donation.getDate(),
                ItemDto.fromEntities(donation.getItems()));
    }
}
