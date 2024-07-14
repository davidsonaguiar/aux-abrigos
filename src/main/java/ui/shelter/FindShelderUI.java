package ui.shelter;

import com.compass.common.Response;
import com.compass.shelter.ShelterController;
import com.compass.shelter.dtos.ShelterResponseDto;
import ui.Component;

import java.util.List;

public class FindShelderUI {
    private ShelterController shelterController;
    private Component component;

    public FindShelderUI(ShelterController shelterController, Component component) {
        this.shelterController = shelterController;
        this.component = component;
    }

    public void execute() {
        System.out.println();
        System.out.println("Procurar Abrigo:");

        String label = "Digite o ID do abrigo:";
        String textInfo = "Digite um número inteiro positivo.";
        String error = "ID inválido. O valor mínimo é 1.";
        Integer shelterId = component.intField(label, textInfo, 1, error, null, null);

        Response<ShelterResponseDto> response = shelterController.findShelter(shelterId.longValue());

        System.out.println();
        System.out.println(response.getMessage());

        if (response.getData() != null) {
            ShelterResponseDto shelter = response.getData();
            component.printShelters(List.of(shelter));
            if(shelter.items().size() > 0) {
                System.out.print("");
                component.printListItem(shelter.items());
            }
            else {
                System.out.println("Nenhum item cadastrado.");
            }
        }
    }

}
