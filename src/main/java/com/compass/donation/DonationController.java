package com.compass.donation;

import com.compass.donation.dtos.DonationDto;

import java.util.List;

public class DonationController {
    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    public void saveMany(List<DonationDto> donations) {
        System.out.println("Donation Controller");
        donations.forEach(System.out::println);
    }
}
