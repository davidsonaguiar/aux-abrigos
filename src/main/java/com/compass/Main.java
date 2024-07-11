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
import com.compass.item.ItemEntity;
import com.compass.item.dtos.ItemDto;
import com.compass.item.enums.*;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = null;
        try {
            entityManager = JpaConnector.getEntityManager("aux-abrigos-persistence-unit");
            System.out.println("Conexão realizada com sucesso!");

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