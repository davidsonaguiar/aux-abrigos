package ui.donation;

import com.compass.common.Response;
import com.compass.donation.DonationController;
import com.compass.donation.dtos.CreateDonationResponseDto;
import ui.Component;

import java.util.List;

public class ListDonationUI {
    private final DonationController donationController;
    private final Component component;

    public ListDonationUI(DonationController donationController, Component component) {
        this.donationController = donationController;
        this.component = component;
    }

    public void execute() {
        System.out.println();
        System.out.println("Listar doações");

        System.out.println();
        System.out.println("Doações realizadas:");

        Response<List<CreateDonationResponseDto>> response = donationController.listAll();

        if(response.getData() == null) {
            System.out.println();
            System.out.println(response.getMessage());
        }
        else {
            System.out.println();
            response.getData().forEach(item -> {
                System.out.print("Doação ID: " + item.donationId());
                System.out.print(" - Centro: " + item.centerName());
                System.out.println(" - Quantidade de itens: " + item.quantity());
            });
        }

    }
}
