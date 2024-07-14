package com.compass.donation.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateDonationResponseDto(
        @NotNull Long donationId,
        @NotNull String centerName,
        @NotNull Integer quantity
) {}
