package ui.shelter;

import com.compass.common.Response;
import com.compass.shelter.ShelterController;
import com.compass.shelter.dtos.ShelterResponseDto;
import ui.Component;
import ui.exceptions.OperationCancelledException;

import java.util.List;

public class DeleteShelterUI {
    private ShelterController shelterController;
    private Component component;

    public DeleteShelterUI(ShelterController shelterController, Component component) {
        this.component = component;
        this.shelterController = shelterController;
    }

    public void execute() {
        System.out.println();
        System.out.println("Excluir abrigo:");
        System.out.println();

        String label = "Informe o ID do abrigo:";
        String textInfo = "Deve ser um número inteiro positivo.";
        String minError = "O ID do abrigo deve ser maior que zero.";
        Integer shelterId = component.intField(label, textInfo, 0, minError, null, null);

        Response<ShelterResponseDto> shelter = shelterController.findById(shelterId.longValue());

        System.out.println();
        System.out.println(shelter.getMessage());

        if(shelter.getData() != null) {
            component.printShelters(List.of(shelter.getData()));

            Boolean confirmation = component.confirmation("Deseja realmente excluir o abrigo?");
            if(!confirmation) throw new OperationCancelledException("Operação cancelada.");

            Response<ShelterResponseDto> response = shelterController.delete(shelterId.longValue());

            System.out.println();
            System.out.println(response.getMessage());

            if(response.getData() != null) {
                component.printShelters(List.of(response.getData()));
            }
        }
    }
}
