package controllers;

import com.compass.center.CenterEntity;
import com.compass.center.CenterService;
import com.compass.common.exception.DaoException;
import com.compass.donation.DonationEntity;
import com.compass.donation.DonationService;
import com.compass.item.ItemEntity;
import com.compass.item.ItemService;
import com.compass.item.enums.*;
import ui.RegisterDonationUI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static ui.RegisterDonationUI.*;

public class RegisterDonation {
    private final CenterService centerService;
    private final DonationService donationService;
    private final Scanner scanner;

    public RegisterDonation(CenterService centerService, DonationService donationService, Scanner scanner) {
        this.centerService = centerService;
        this.donationService = donationService;
        this.scanner = scanner;
    }

    public void execute() {
        DonationEntity donation = new DonationEntity();
        CenterEntity center = selectCenter(centerService::findAll, scanner);
        if(center == null) {
            System.out.println("Não há centros cadastrados");
            return;
        }

        donation.setCenter(center);
        donation.setDate(LocalDate.now());
        donation.setItems(new ArrayList<>());

        createDonationItems(donation, scanner);
        if(donation.getItems().isEmpty()) {
            System.out.println("Doação cancelada");
            return;
        }

        try {
            donationService.save(donation);
            System.out.println("Doação registrada com sucesso");
        }
        catch (DaoException exception) {
            System.out.println("Erro ao tentar salver doação: " + exception.getMessage());
        }
    }
}
