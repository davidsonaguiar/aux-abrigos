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

    public ShelterEntity findShelterById(Long id) throws NotFoundException, DaoException {
        try {
            ShelterEntity shelter = shelterDao.findById(id);
            if (shelter == null) throw new NotFoundException("Abrigo não encontrado.");
            return shelter;
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
            shelterDao.findByName(shelterDto.name());
            throw new ContentConflictException("Abrigo já cadastrado.");
        }
        catch (NoResultException exception) {
            ShelterEntity newShelter = CreateShelterRequestDto.toEntity(shelterDto);
            newShelter = shelterDao.save(newShelter);
            return CreateShelterResponseDto.fromEntity(newShelter);
        }
    }

    public ShelterResponseDto update(UpdateShelterRequestDto shelterDto) throws NotFoundException, ContentConflictException, DaoException {
        ShelterEntity shelter;

        try {
            shelter = shelterDao.findByName(shelterDto.name());
        }
        catch (NoResultException exception) {
            shelter = null;
        }

        if(shelter != null && shelter.getId() != shelterDto.id()) throw new ContentConflictException("Nome já cadastrado.");

        try {
            shelter = shelterDao.findById(shelterDto.id());

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
        catch (NoResultException exception) {
            throw new NotFoundException("Abrigo não encontrado.");
        }
    }

    public ShelterResponseDto delete(long id) throws NotFoundException, DaoException {
        ShelterEntity shelter = shelterDao.findById(id);
        if(shelter == null) throw new NotFoundException("Abrigo não encontrado.");
        shelterDao.deleteById(shelter.getId());
        return ShelterResponseDto.fromEntity(shelter);
    }
}
