package com.compass.shelter;

import com.compass.common.exception.ContentConflictException;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
import com.compass.shelter.dtos.CreateShelterRequestDto;
import com.compass.shelter.dtos.CreateShelterResponseDto;
import com.compass.shelter.dtos.ShelterResponseDto;
import com.compass.shelter.dtos.UpdateShelterRequestDto;
import jakarta.persistence.NoResultException;

import java.util.List;

public class ShelterService {
    private final ShelterDao shelterDao;

    public ShelterService(ShelterDao shelterDao) {
        this.shelterDao = shelterDao;
    }

    public ShelterResponseDto findShelterById(Long id) throws NotFoundException, DaoException {
        try {
            ShelterEntity shelter = shelterDao.findById(id);
            if (shelter == null) throw new NotFoundException("Abrigo não encontrado.");
            return ShelterResponseDto.fromEntity(shelter);
        }
        catch (NotFoundException exception) {
            throw exception;
        }
        catch (NoResultException exception) {
            throw new NotFoundException(exception.getMessage());
        }
    }

    public List<ShelterResponseDto> findAll() throws NotFoundException, DaoException {
        try {
           List<ShelterEntity> shelters = shelterDao.findAll();
              return ShelterResponseDto.fromEntityList(shelters);
        }
        catch (NoResultException exception) {
            throw new NotFoundException("Nenhum abrigo cadastrado.");
        }
    }

    public CreateShelterResponseDto save(CreateShelterRequestDto shelterDto) throws ContentConflictException, DaoException {
        try {
            ShelterEntity shelterEntity = shelterDao.findByName(shelterDto.name());
            throw new ContentConflictException("Abrigo já cadastrado.");
        }
        catch (NoResultException exception) {
            ShelterEntity newShelter = CreateShelterRequestDto.toEntity(shelterDto);
            newShelter = shelterDao.save(newShelter);
            return CreateShelterResponseDto.fromEntity(newShelter);
        }
        catch (ContentConflictException exception) {
            throw exception;
        }
    }

    public ShelterResponseDto update(UpdateShelterRequestDto shelterDto) {
        try {
            ShelterEntity shelter = shelterDao.findById(shelterDto.id());
            if(shelter == null) throw new NotFoundException("Abrigo não encontrado.");

            shelter.setName(shelterDto.name());
            shelter.setAddress(shelterDto.address());
            shelter.setPhone(shelterDto.phone());
            shelter.setEmail(shelterDto.email());
            shelter.setResponsible(shelterDto.responsible());
            shelter.setCapacityPeople(shelterDto.capacityPeople());
            shelter.setOccupancy(shelterDto.occupancy());

            ShelterEntity updatedShelter = shelterDao.update(shelter);
            return ShelterResponseDto.fromEntity(updatedShelter);
        }
        catch (NotFoundException exception) {
            throw exception;
        }
        catch (DaoException exception) {
            throw exception;
        }
    }
}
