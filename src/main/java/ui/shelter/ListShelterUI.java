package ui.shelter;

import com.compass.common.Response;
import com.compass.shelter.ShelterController;
import com.compass.shelter.dtos.ShelterResponseDto;
import ui.Component;

import java.util.List;

public class ListShelterUI {
    private final ShelterController shelterController;
    private final Component component;

    public ListShelterUI(ShelterController shelterController, Component component) {
        this.shelterController = shelterController;
        this.component = component;
    }

    public void execute() {
        System.out.println();
        System.out.println("Lista de abrigos");

        Response<List<ShelterResponseDto>> shelters = shelterController.findAll();

        System.out.println();
        System.out.println(shelters.getMessage());

        if (shelters.getData() != null) {
            component.printShelters(shelters.getData());
        }
    }
}
