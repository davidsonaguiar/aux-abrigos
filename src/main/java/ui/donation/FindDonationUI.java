package ui.donation;

import com.compass.common.Response;
import com.compass.donation.DonationController;
import com.compass.donation.dtos.FindDonationResponseDto;
import com.compass.item.dtos.ItemDto;
import ui.Component;

import java.util.List;

public class FindDonationUI {
    private final DonationController donationController;
    private final Component component;

    public FindDonationUI(DonationController donationController, Component component) {
        this.donationController = donationController;
        this.component = component;
    }

    public void execute() {
        System.out.println();
        System.out.println("Buscar doação");

        String label = "Informe o ID da doação.";
        String textInfo = "Digite um número inteiro positivo.";
        String minError = "ID inválido. O valor mínimo é 1.";
        Integer donationId = component.intField(label, textInfo, 1, minError, null, null);

        Response<FindDonationResponseDto> response = donationController.findById(donationId.longValue());

        System.out.println();
        System.out.println(response.getMessage());

        if (response.getData() != null) {
            FindDonationResponseDto donation = response.getData();
            System.out.println();
            System.out.print("Doação ID: " + donation.donationId());
            System.out.print(" - Centro: " + donation.centerName());
            System.out.println(" - Quantidade de itens: " + donation.items().size());
            component.printListItem(donation.items());
        }
    }
}
