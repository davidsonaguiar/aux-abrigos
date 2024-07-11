package com.compass.shelter;

import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;

import java.util.List;

public class ShelterService {
    private final ShelterDao shelterDao;

    public ShelterService(ShelterDao shelterDao) {
        this.shelterDao = shelterDao;
    }

    public ShelterEntity findShelterById(Long id) {
        try {
            return shelterDao.findById(id);
        } catch (DaoException exception) {
            throw exception;
        }
    }

    public List<ShelterEntity> findAll() {
        try {
            return shelterDao.findAll();
        } catch (DaoException exception) {
            throw exception;
        }
    }

    public ShelterEntity save(ShelterEntity shelter) {
        try {
            return shelterDao.save(shelter);
        }
        catch (NotFoundException exception) {
            throw new NotFoundException(exception.getMessage());
        }
        catch (DaoException exception) {
            throw exception;
        }
    }

    public ShelterEntity update(ShelterEntity shelter) {
        try {
            return shelterDao.update(shelter);
        }
        catch (NotFoundException exception) {
            throw new NotFoundException(exception.getMessage());
        }
        catch (DaoException exception) {
            throw exception;
        }
    }
}
