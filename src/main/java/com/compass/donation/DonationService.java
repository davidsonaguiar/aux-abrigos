package com.compass.donation;

import com.compass.center.CenterEntity;
import com.compass.center.CenterService;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
import com.compass.donation.dtos.DonationDto;
import com.compass.item.ItemEntity;
import jakarta.persistence.NoResultException;

import java.util.ArrayList;
import java.util.List;

public class DonationService {
    private final DonationDao donationDao;

    public DonationService(DonationDao donationDao) {
        this.donationDao = donationDao;
    }

    public DonationEntity findDonationById(Long id) {
        try {
            return donationDao.findById(id);
        } catch (DaoException exception) {
            throw exception;
        }
    }

    public List<DonationEntity> findAll() {
        try {
            return donationDao.findAll();
        } catch (DaoException exception) {
            throw exception;
        }
    }

    public DonationEntity save(DonationEntity donation) {
        try {
            return donationDao.save(donation);
        }
        catch (NotFoundException exception) {
            throw new NotFoundException(exception.getMessage());
        }
        catch (DaoException exception) {
            throw exception;
        }
    }

    public DonationEntity update(DonationEntity donation) {
        try {
            return donationDao.update(donation);
        }
        catch (NotFoundException exception) {
            throw new NotFoundException(exception.getMessage());
        }
        catch (DaoException exception) {
            throw exception;
        }
    }

    public void delete(Long id) {
        try {
            donationDao.deleteById(id);
        }
        catch (NoResultException exception) {
            throw new NotFoundException("Doação não encontrada");
        }
        catch (DaoException exception) {
            throw exception;
        }
    }
}
