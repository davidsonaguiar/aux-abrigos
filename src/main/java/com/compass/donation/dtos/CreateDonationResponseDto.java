package com.compass.donation.dtos;

import com.compass.donation.DonationEntity;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateDonationResponseDto(
        @NotNull Long donationId,
        @NotNull String centerName,
        @NotNull Integer quantity) {

    public static CreateDonationResponseDto fromEntity(DonationEntity donationEntity) {
        return new CreateDonationResponseDto(donationEntity.getId(), donationEntity.getCenter().getName(), donationEntity.getItems().size());
    }

    public static List<CreateDonationResponseDto> fromEntities(List<DonationEntity> donationEntities) {
        return donationEntities.stream().map(CreateDonationResponseDto::fromEntity).toList();
    }
}
