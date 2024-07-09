package com.compass.donation;

import com.compass.center.CenterEntity;
import com.compass.center.CenterService;
import com.compass.common.exception.NotFoundException;
import com.compass.donation.dtos.DonationDto;
import com.compass.item.dtos.ItemDto;
import com.compass.item.entities.ItemEntity;
import com.compass.item.entities.ItemServiceFactory;
import com.compass.item.entities.ItemServiceFactoryProtocol;
import com.compass.item.services.ItemService;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

public class DonationService {
    private final DonationDao donationDao;
    private final CenterService centerService;
    private final ItemServiceFactoryProtocol itemService;

    public DonationService(DonationDao donationDao, CenterService centerService, ItemServiceFactory itemService) {
        this.donationDao = donationDao;
        this.centerService = centerService;
        this.itemService = itemService;
    }

    public DonationEntity findDonationById(Long id) {
        return donationDao.findById(id);
    }

    public List<DonationEntity> findAll() {
        return donationDao.findAll();
    }

    public void delete(Long id) {
        donationDao.deleteById(id);
    }

    @Transactional
    public DonationEntity save(DonationDto donation) {
        try {
            CenterEntity center = centerService.findById(donation.centerId());
            List<ItemEntity> items = new ArrayList<>();
            DonationEntity donationEntity = new DonationEntity();
            donationEntity.setDate(donation.date());
            donationEntity.setCenter(center);


            for(ItemDto item : donation.items()) {
                try {
                    ItemService<?> itemService = this.itemService.createItemService(item.category());
                    ItemEntity itemSaved = itemService.save(item);
                    items.add(itemSaved);
                }
                catch (Exception exception) {
                    System.out.println("Erro ao salvar item: " + exception.getMessage());
                }
            }
            donationEntity.setItems(items);
            System.out.println(donationEntity.getItems());
            return donationDao.save(donationEntity);
        }
        catch (NotFoundException exception) {
            throw new NotFoundException(exception.getMessage());
        }
        catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
