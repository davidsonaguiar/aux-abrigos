package com.compass;

import com.compass.center.CenterDao;
import com.compass.center.CenterEntity;
import com.compass.center.CenterService;
import com.compass.donation.DonationDao;
import com.compass.donation.DonationEntity;
import com.compass.donation.DonationService;
import controllers.RegisterDonation;
import com.compass.db.DatabaseConnectionException;
import com.compass.db.JpaConnector;
import jakarta.persistence.EntityManager;
import ui.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = null;
        Scanner scanner = new Scanner(System.in);

        try {
            entityManager = JpaConnector.getEntityManager("aux-abrigos-persistence-unit");
            System.out.println("Conexão realizada com sucesso!");

            CenterDao centerDao = new CenterDao(entityManager, CenterEntity.class);
            CenterService centerService = new CenterService(centerDao);

            DonationDao donationDao = new DonationDao(entityManager, DonationEntity.class);
            DonationService donationService = new DonationService(donationDao);

            RegisterDonation registerDonation = new RegisterDonation(centerService, donationService, scanner);

            Menu menu = new Menu(scanner, registerDonation);
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
}