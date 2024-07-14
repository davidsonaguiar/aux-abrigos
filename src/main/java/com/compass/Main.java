package com.compass;

import com.compass.center.CenterController;
import com.compass.center.CenterDao;
import com.compass.center.CenterEntity;
import com.compass.center.CenterService;
import com.compass.donation.DonationController;
import com.compass.donation.DonationDao;
import com.compass.donation.DonationEntity;
import com.compass.donation.DonationService;
import com.compass.db.DatabaseConnectionException;
import com.compass.db.JpaConnector;
import com.compass.item.ItemController;
import com.compass.item.ItemDao;
import com.compass.item.ItemEntity;
import com.compass.item.ItemService;
import com.compass.shelter.ShelterController;
import com.compass.shelter.ShelterDao;
import com.compass.shelter.ShelterEntity;
import com.compass.shelter.ShelterService;
import jakarta.persistence.EntityManager;
import ui.Component;
import ui.Menu;
import ui.donation.*;
import ui.item.UpdateItemUI;
import ui.shelter.CreateShelterUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = null;
        Scanner scanner = new Scanner(System.in);

        try {
            entityManager = JpaConnector.getEntityManager("aux-abrigos-persistence-unit");
            System.out.println("Conexão realizada com sucesso!");

            Menu menu = getMenu(entityManager, scanner);
            menu.execute();
        }
        catch (DatabaseConnectionException exception) {
            exception.printStackTrace();
        }
        finally {
            JpaConnector.closeEntityManager(entityManager);
            JpaConnector.closeEntityManagerFactory();
            scanner.close();
            System.out.println("Conexão encerrada!");
        }
    }

    private static Menu getMenu(EntityManager entityManager, Scanner scanner) {
        CenterDao centerDao = new CenterDao(entityManager, CenterEntity.class);
        CenterService centerService = new CenterService(centerDao);
        CenterController centerController = new CenterController(centerService);

        ItemDao itemDao = new ItemDao(entityManager, ItemEntity.class);
        ItemService itemService = new ItemService(itemDao);
        ItemController itemController = new ItemController(itemService);

        DonationDao donationDao = new DonationDao(entityManager, DonationEntity.class);
        DonationService donationService = new DonationService(donationDao, centerService, itemService);
        DonationController donationController = new DonationController(donationService);

        ShelterDao shelterDao = new ShelterDao(entityManager, ShelterEntity.class);
        ShelterService shelterService = new ShelterService(shelterDao);
        ShelterController shelterController = new ShelterController(shelterService);

        Component component = new Component(scanner);

        UpdateItemUI updateItemUI = new UpdateItemUI(itemController, component);

        RegisterDonationByFileUI registerDonationByFileUI = new RegisterDonationByFileUI(donationController, component);
        RegisterDonationUI registerDonationUI = new RegisterDonationUI(donationController, centerController, component);
        ListDonationUI listDonationUI = new ListDonationUI(donationController, component);
        FindDonationUI findDonationUI = new FindDonationUI(donationController, component);
        UpdateDonationUI updateDonationUI = new UpdateDonationUI(donationController, centerController, updateItemUI, component);
        DeleteDonationUI deleteDonationUI = new DeleteDonationUI(donationController, component);

        CreateShelterUI createShelterUI = new CreateShelterUI(shelterController, component);

        Menu menu = new Menu(component);
        menu.getDonationUI(registerDonationByFileUI, registerDonationUI, listDonationUI, findDonationUI, updateDonationUI, deleteDonationUI);
        menu.getShelterUI(createShelterUI);
        return menu;
    }

}