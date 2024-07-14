package com.compass.shelter;

import com.compass.common.Response;
import com.compass.common.exception.ContentConflictException;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
import com.compass.shelter.dtos.CreateShelterRequestDto;
import com.compass.shelter.dtos.CreateShelterResponseDto;
import com.compass.shelter.dtos.ShelterResponseDto;
import com.compass.shelter.dtos.UpdateShelterRequestDto;

import java.util.List;

public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    public Response<CreateShelterResponseDto> create(CreateShelterRequestDto shelterDto) {
        try {
            CreateShelterResponseDto responseDto = shelterService.save(shelterDto);
            return new Response<>(responseDto, "Abrigo criado com sucesso!");
        }
        catch (ContentConflictException | DaoException exception) {
            return new Response<>(null, exception.getMessage());
        }
    }

    public Response<List<ShelterResponseDto>> findAll() {
        try {
            List<ShelterResponseDto> shelters = shelterService.findAll();
            return new Response<>(shelters, "Lista de abrigos recuperada com sucesso!");
        }
        catch (NotFoundException | DaoException exception) {
            return new Response<>(null, exception.getMessage());
        }
    }

    public Response<ShelterResponseDto> findById(Long shelterId) {
        try {
            ShelterResponseDto shelter = shelterService.findShelterById(shelterId);
            return new Response<>(shelter, "Abrigo recuperado com sucesso!");
        }
        catch (NotFoundException | DaoException exception) {
            return new Response<>(null, exception.getMessage());
        }
    }

    public Response<ShelterResponseDto> updateShelter(UpdateShelterRequestDto updateShelterRequestDto) {
        try {
            ShelterResponseDto shelter = shelterService.update(updateShelterRequestDto);
            System.out.println(shelter);
            return new Response<>(shelter, "Abrigo atualizado com sucesso!");
        }
        catch (ContentConflictException | NotFoundException | DaoException exception) {
            return new Response<>(null, exception.getMessage());
        }
    }

    public Response<ShelterResponseDto> delete(long id) {
        try {
            ShelterResponseDto shelter = shelterService.delete(id);
            return new Response<>(shelter, "Abrigo excluído com sucesso!");
        }
        catch (NotFoundException | DaoException exception) {
            return new Response<>(null, exception.getMessage());
        }
    }
}
