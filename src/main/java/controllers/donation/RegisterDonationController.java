package controllers.donation;

import com.compass.center.CenterEntity;
import com.compass.center.CenterService;
import com.compass.common.exception.DaoException;
import com.compass.common.exception.NotFoundException;
import com.compass.donation.DonationEntity;
import com.compass.donation.DonationService;
import ui.exceptions.OperationCancelledException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static ui.Component.confirmation;
import static ui.Component.selectCenter;
import static ui.RegisterDonationUI.*;

public class RegisterDonationController {
    private final CenterService centerService;
    private final DonationService donationService;
    private final Scanner scanner;

    public RegisterDonationController(CenterService centerService, DonationService donationService, Scanner scanner) {
        this.centerService = centerService;
        this.donationService = donationService;
        this.scanner = scanner;
    }

    public void execute() {
        DonationEntity donation = new DonationEntity();
        try {
            List<CenterEntity> centers = centerService.findAll();
            CenterEntity center = selectCenter(centers, scanner);

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
                DonationEntity donationSaved = donationService.save(donation);
                System.out.printf("Doação registrada com sucesso - ID: %d\n", donationSaved.getId());
            }
            else {
                System.out.println("Doação Cancelada!");
            }
        }
        catch (OperationCancelledException exception) {
            System.out.println(exception.getMessage());
        }
        catch (NotFoundException exception) {
            System.out.println(exception.getMessage());
        }
        catch (DaoException exception) {
            System.out.println("Erro ao tentar salver doação: " + exception.getMessage());
        }
    }
}
