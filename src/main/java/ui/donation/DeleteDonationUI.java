package ui.donation;

import com.compass.common.Response;
import com.compass.donation.DonationController;
import com.compass.donation.dtos.CreateDonationResponseDto;
import com.compass.donation.dtos.DonationDto;
import com.compass.donation.dtos.FindDonationResponseDto;
import ui.Component;
import ui.exceptions.OperationCancelledException;

import java.util.List;

public class DeleteDonationUI {
    private final DonationController donationController;
    private final Component component;

    public DeleteDonationUI(DonationController donationController, Component component) {
        this.donationController = donationController;
        this.component = component;
    }

    public void execute() {
        while(true) {
            try {
                System.out.println();
                System.out.println("Deletar doação");

                String label = "Digite o id da doação que deseja deletar";
                String textInfo = "Digite um número inteiro positivo!";
                String minError = "O id deve ser um número inteiro positivo!";
                Integer donationId = component.intField(label, textInfo, 1, minError, null, null);

                Response<FindDonationResponseDto> donation = donationController.findById(donationId.longValue());
                if(donation.getData() == null) {
                    System.out.println("Doação não encontrada");
                    continue;
                }

                FindDonationResponseDto donationResponse = donation.getData();

                System.out.println("Doação encontrada:");
                System.out.printf("ID: %d - Centro: %s  %s", donationResponse.donationId(), donationResponse.centerName(), donationResponse.date());
                component.printListItem(donationResponse.items());

                Boolean confirm = component.confirmation("Deseja realmente deletar essa doação?");
                if(!confirm) {
                    System.out.println("Operação cancelada");
                    break;
                }

                Response<CreateDonationResponseDto> response = donationController.delete(donationId.longValue());

                System.out.println();
                System.out.println(response.getMessage());

                if (response.getData() != null) {
                    CreateDonationResponseDto donationResponseDto = response.getData();
                    System.out.printf("Doação deletada: ID: %d - Centro: %s\n", donationResponseDto.donationId(), donationResponseDto.centerName());
                    break;
                }
            }
            catch (OperationCancelledException exception) {
                break;
            }
        }
    }
}
