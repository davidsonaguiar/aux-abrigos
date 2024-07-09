package com.compass;

import com.compass.center.CenterDao;
import com.compass.center.CenterEntity;
import com.compass.center.CenterService;
import com.compass.db.DatabaseConnectionException;
import com.compass.db.JpaConnector;
import com.compass.donation.DonationDao;
import com.compass.donation.DonationEntity;
import com.compass.donation.DonationService;
import com.compass.donation.dtos.DonationDto;
import com.compass.item.ItemDao;
import com.compass.item.dtos.ItemDto;
import com.compass.item.entities.*;
import com.compass.item.enums.*;
import com.compass.item.services.ItemClothingService;
import com.compass.item.services.ItemFoodService;
import com.compass.item.services.ItemHygieneService;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = null;
        try {
            entityManager = JpaConnector.getEntityManager("aux-abrigos-persistence-unit");
            System.out.println("Conexão realizada com sucesso!");

            CenterDao centerDao = new CenterDao(entityManager, CenterEntity.class);
            CenterService centerService = new CenterService(centerDao);

            ItemDao itemDao = new ItemDao(entityManager, ItemEntity.class);
            ItemFoodService itemFoodService = new ItemFoodService(itemDao, centerService);
            ItemClothingService itemClothingService = new ItemClothingService(itemDao, centerService);
            ItemHygieneService itemHygieneService = new ItemHygieneService(itemDao, centerService);
            ItemServiceFactory itemServiceFactory = new ItemServiceFactory(itemFoodService, itemClothingService, itemHygieneService);

            DonationDao donationDao = new DonationDao(entityManager, DonationEntity.class);
            DonationService donationService = new DonationService(donationDao, centerService, itemServiceFactory);

            DonationEntity donationEntity = donationService.findDonationById(1L);
            System.out.println("Doação encontrada: " + donationEntity.getId());
            System.out.printf("Data: %s\n", donationEntity.getDate());
            System.out.printf("Centro: %s\n", donationEntity.getCenter().getName());
            for (ItemEntity item : donationEntity.getItems()) {
                System.out.println();

                System.out.printf("Item: %s\n", item.getDescription());
                System.out.printf("Categoria: %s\n", item.getCategory());
                System.out.printf("Quantidade: %d\n", item.getQuantity());

                if (item instanceof ItemClothingEntity) {
                    ItemClothingEntity itemClothing = (ItemClothingEntity) item;
                    System.out.printf("Tamanho: %s\n", itemClothing.getSize());
                    System.out.printf("Gênero: %s\n", itemClothing.getGender());
                }
                else if (item instanceof ItemHygieneEntity) {
                    ItemHygieneEntity itemHygiene = (ItemHygieneEntity) item;
                    System.out.printf("Tipo de higiene: %s\n", itemHygiene.getHygieneType());
                }
                else if (item instanceof ItemFoodEntity) {
                    ItemFoodEntity itemFood = (ItemFoodEntity) item;
                    System.out.printf("Data de validade: %s\n", itemFood.getExpirationDate());
                    System.out.printf("Unidade: %s\n", itemFood.getUnit());
                }
            }

            /*
            ItemDto itemDto = new ItemDto("Arroz", 10, CategoryItem.ALIMENTO, 1L, null, null, LocalDate.of(2025, 1, 1), Unit.QUILOGRAMA, null);
            ItemDto itemDto2 = new ItemDto("Camisa", 5, CategoryItem.ROUPA, 1L, SizeItem.M, GenderItem.MASCULINO, null, null, null);
            ItemDto itemDto3 = new ItemDto("Com Abas", 2, CategoryItem.HIGIENE, 1L, null, null, null, null, HygieneTypeItem.ABSORVENTE);

            DonationDto DanationDto = new DonationDto(LocalDate.now(), 1L, List.of(itemDto, itemDto2, itemDto3));

            DonationEntity donationEntity = donationService.save(DanationDto);
            System.out.println("Doação salva com sucesso: " + donationEntity);
             */
        }
        catch (DatabaseConnectionException exception) {
            exception.printStackTrace();
        }
        finally {
            JpaConnector.closeEntityManager(entityManager);
            JpaConnector.closeEntityManagerFactory();
            System.out.println("Conexão encerrada!");
        }
    }
}