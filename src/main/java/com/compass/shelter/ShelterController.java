package com.compass.shelter;

import com.compass.shelter.dtos.CreateShelterRequestDto;

public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    public void create(CreateShelterRequestDto shelterDto) {
        System.out.println("Controller - Criar abrigo");
        System.out.println(shelterDto);
    }
}
