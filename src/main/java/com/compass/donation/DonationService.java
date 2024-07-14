package com.compass.donation;

import com.compass.center.CenterEntity;
import com.compass.center.CenterService;
import com.compass.common.exception.BadRequestException;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
import com.compass.donation.dtos.CreateDonationResponseDto;
import com.compass.donation.dtos.DonationDto;
import com.compass.donation.dtos.FindDonationResponseDto;
import com.compass.item.ItemEntity;
import jakarta.persistence.NoResultException;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

public class DonationService {
    private final DonationDao donationDao;
    private final CenterService centerService;

    public DonationService(DonationDao donationDao, CenterService centerService) {
        this.donationDao = donationDao;
        this.centerService = centerService;
    }

    public FindDonationResponseDto findDonationById(Long id) {
        try {
            DonationEntity donation = donationDao.findById(id);
            if(donation == null) throw new NotFoundException("Doação não encontrada");
            return FindDonationResponseDto.fromEntity(donation);
        }
        catch (NotFoundException exception) {
            throw exception;
        }
        catch (DaoException exception) {
            throw exception;
        }
    }

    public List<CreateDonationResponseDto> findAll() {
        try {
            List<DonationEntity> centers = donationDao.findAll();
            List<CreateDonationResponseDto> response = new ArrayList<>();
            for (DonationEntity donation : centers) {
                CreateDonationResponseDto donationResponse = new CreateDonationResponseDto(donation.getId(), donation.getCenter().getName(), donation.getItems().size());
                response.add(donationResponse);
            }
            return response;
        } catch (DaoException exception) {
            throw exception;
        }
    }

    public CreateDonationResponseDto save(DonationDto donationDto) throws DaoException, NotFoundException, BadRequestException {
        try {
            CenterEntity center = centerService.findById(donationDto.centerId());
            List<ItemEntity> item = new ArrayList<>();

            donationDto.items().forEach(itemDto -> {
                ItemEntity itemEntity = new ItemEntity();
                itemEntity.setDescription(itemDto.description());
                itemEntity.setCategory(itemDto.category());
                itemEntity.setQuantity(itemDto.quantity());
                itemEntity.setCenter(center);
                itemEntity.setSize(itemDto.size());
                itemEntity.setGender(itemDto.gender());
                itemEntity.setExpirationDate(itemDto.expirationDate());
                itemEntity.setUnit(itemDto.unit());
                itemEntity.setHygieneType(itemDto.hygieneType());
                item.add(itemEntity);
            });

            DonationEntity donation = new DonationEntity();
            donation.setCenter(center);
            donation.setDate(donationDto.date());
            donation.setItems(item);

            DonationEntity donationSave = donationDao.save(donation);
            CreateDonationResponseDto response = new CreateDonationResponseDto(donationSave.getId(), center.getName(), item.size());
            return response;
        }
        catch (ConstraintViolationException exception) {
            throw new BadRequestException(exception.getMessage());
        }
    }

    public List<CreateDonationResponseDto> saveMany(List<DonationDto> donations) {
        List<CreateDonationResponseDto> response = new ArrayList<>();

        donations.forEach(donation -> {
            try {
                CreateDonationResponseDto donationSave = save(donation);
                response.add(donationSave);
            }
            catch (NotFoundException exception) {
                throw new NotFoundException(exception.getMessage());
            }
            catch (BadRequestException exception) {
                throw new BadRequestException(exception.getMessage());
            }
            catch (DaoException exception) {
                exception.printStackTrace();
                throw exception;
            }
        });
        return response;
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

    public CreateDonationResponseDto updateCenter(Long donationId, Long centerId) {
        try {
            DonationEntity donation = donationDao.findById(donationId);
            if (donation == null) throw new NotFoundException("Doação não encontrada");
            CenterEntity center = centerService.findById(centerId);
            if (center == null) throw new NotFoundException("Centro não encontrado");

            donation.setCenter(center);
            DonationEntity donationUpdate = donationDao.updateCenter(donation);
            return new CreateDonationResponseDto(donationUpdate.getId(), center.getName(), donationUpdate.getItems().size());
        }
        catch (NotFoundException exception) {
            throw exception;
        }
        catch (DaoException exception) {
            throw exception;
        }
    }
}
