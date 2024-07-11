package controllers;

import com.compass.center.CenterEntity;
import com.compass.center.CenterService;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
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

import static ui.Component.confirmation;
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
        try {
            CenterEntity center = selectCenter(centerService::findAll, scanner);

            donation.setCenter(center);
            donation.setDate(LocalDate.now());
            donation.setItems(new ArrayList<>());

            createDonationItems(donation, scanner);

            if(donation.getItems().isEmpty()) {
                System.out.println("Doação cancelada");
                return;
            }

            System.out.println();
            listAllItemsOfDonation(donation);

            System.out.println();
            Boolean finish = confirmation("Salvar doação? (Caso não, o processo será cancelado!)", scanner);

            if(finish) {
                donationService.save(donation);
                System.out.println("Doação registrada com sucesso");
            }
            else {
                System.out.println("Doação Cancelada!");
            }
        }
        catch (NotFoundException exception) {
            System.out.println(exception.getMessage());
        }
        catch (DaoException exception) {
            System.out.println("Erro ao tentar salver doação: " + exception.getMessage());
        }
    }
}
