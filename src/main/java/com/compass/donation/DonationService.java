package com.compass.donation;

import com.compass.center.CenterEntity;
import com.compass.center.CenterService;
import com.compass.common.exception.BadRequestException;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NoCapacityException;
import com.compass.common.exception.NotFoundException;
import com.compass.donation.dtos.CreateDonationResponseDto;
import com.compass.donation.dtos.DonationDto;
import com.compass.donation.dtos.FindDonationResponseDto;
import com.compass.item.ItemEntity;
import com.compass.item.ItemService;
import com.compass.item.dtos.ItemDto;
import com.compass.item.enums.CategoryItem;
import jakarta.persistence.NoResultException;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DonationService {
    private final DonationDao donationDao;
    private final CenterService centerService;
    private final ItemService itemService;

    public DonationService(DonationDao donationDao, CenterService centerService, ItemService itemService) {
        this.donationDao = donationDao;
        this.centerService = centerService;
        this.itemService = itemService;
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

            verifyCapacity(donationDto.items(), center);

            List<ItemEntity> items = new ArrayList<>();

            donationDto.items().forEach(itemDto -> items.add(createItem(itemDto, center)));

            DonationEntity donation = new DonationEntity();
            donation.setCenter(center);
            donation.setDate(donationDto.date());
            donation.setItems(items);

            DonationEntity donationSave = donationDao.save(donation);
            return CreateDonationResponseDto.fromEntity(donationSave);
        }
        catch (NotFoundException exception) {
            throw new NotFoundException(exception.getMessage());
        }
        catch (ConstraintViolationException exception) {
            throw new BadRequestException(exception.getMessage());
        }
    }

    public List<CreateDonationResponseDto> saveMany(List<DonationDto> donations) throws DaoException, NotFoundException, BadRequestException {
        String errors = null;

        try {
            for(DonationDto donation : donations) {
                CenterEntity center = centerService.findById(donation.centerId());
                try {
                    verifyCapacity(donation.items(), center);
                }
                catch (NoCapacityException exception) {
                    if (errors == null) errors = "";
                    errors += "Centro: " + center.getName() + " - " + exception.getMessage() + "\n";
                }
            }

            if (errors != null) {
                throw new NoCapacityException(errors);
            }

            List<DonationEntity> donationsEntities = new ArrayList<>();

            for(DonationDto donation : donations) {
                List<ItemEntity> items = new ArrayList<>();
                donation.items().forEach(itemDto -> items.add(createItem(itemDto, centerService.findById(donation.centerId()))));
                DonationEntity donationEntity = new DonationEntity();
                donationEntity.setCenter(centerService.findById(donation.centerId()));
                donationEntity.setDate(donation.date());
                donationEntity.setItems(items);
                donationsEntities.add(donationEntity);
            }

            List<DonationEntity> donationSave = donationDao.saveMany(donationsEntities);
            return CreateDonationResponseDto.fromEntities(donationSave);
        }
        catch (NotFoundException exception) {
            throw new NotFoundException(exception.getMessage());
        }

        catch (BadRequestException exception) {
            throw new BadRequestException(exception.getMessage());
        }
    }

    private ItemEntity createItem(ItemDto itemDto, CenterEntity center) {
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

        return itemEntity;
    }

    private void verifyCapacity(List<ItemDto> items, CenterEntity center) throws NoCapacityException {
        Map<CategoryItem, Long> quantityByCategory = items.stream()
                .collect(Collectors.groupingBy(ItemDto::category, Collectors.counting()));

        String errorsCapacity = null;
        for (Map.Entry<CategoryItem, Long> entry : quantityByCategory.entrySet()) {
            CategoryItem category = entry.getKey();
            Long quantity = entry.getValue();
            if (!center.existsCapacityForCategoryItem(quantity.intValue(), category)) {
                if (errorsCapacity == null) errorsCapacity = "";
                errorsCapacity += "Capacidade insuficiente para " + category + "\n";
            }
        }

        if (errorsCapacity != null) {
            throw new NoCapacityException(errorsCapacity);
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

    public ItemDto removeItem(Long donationId, Long itemId) {
        try {
            DonationEntity donation = donationDao.findById(donationId);
            if (donation == null) throw new NotFoundException("Doação não encontrada");
            ItemEntity item = donation.getItems().stream().filter(i -> i.getId().equals(itemId)).findFirst().orElse(null);
            if (item == null) throw new NotFoundException("Item não encontrado");
            donation.getItems().remove(item);
            donationDao.update(donation);
            return itemService.delete(itemId);
        }
        catch (NotFoundException exception) {
            throw exception;
        }
    }
}
