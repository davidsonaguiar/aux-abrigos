package ui.shelter;

import com.compass.common.Response;
import com.compass.shelter.ShelterController;
import com.compass.shelter.dtos.ShelterResponseDto;
import com.compass.shelter.dtos.UpdateShelterRequestDto;
import ui.Component;

import java.util.List;

public class UpdateShelterUI {
    private ShelterController shelterController;
    private Component component;

    public UpdateShelterUI(ShelterController shelterController, Component component) {
        this.shelterController = shelterController;
        this.component = component;
    }

    public void execute() {
        System.out.println();
        System.out.println("Atualizar Abrigo:");

        String label = "Digite o ID do abrigo:";
        String textInfo = "Digite um número inteiro positivo.";
        String minError = "ID inválido. O valor mínimo é 1.";
        Integer shelterId = component.intField(label, textInfo, 1, minError, null, null);

        Response<ShelterResponseDto> shelterResponse = shelterController.findById(shelterId.longValue());

        System.out.println();
        System.out.println(shelterResponse.getMessage());

        if(shelterResponse.getData() == null) return;

        ShelterResponseDto shelter = shelterResponse.getData();
        component.printShelters(List.of(shelter));

        Long id = shelter.id();
        String name = shelter.name();
        String address = shelter.address();
        String phone = shelter.phone();
        String email = shelter.email();
        String responsible = shelter.responsible();
        Integer capacityPeople = shelter.capacityPeople();
        Integer occupancy = shelter.occupancy();

        Boolean confirmantion = component.confirmation("Deseja atualizar o abrigo?");
        if(!confirmantion) return;


        confirmantion = component.confirmation("Deseja atualizar o nome do abrigo?");
        if(confirmantion) {
            label = "Digite o novo nome do abrigo:";
            textInfo = "Digite um texto com no mínimo 3 e no máximo 100 caracteres.";
            minError = "Nome do abrigo inválido. O texto deve ter entre 3 e 100 caracteres.";
            String maxError = "Nome do abrigo inválido. O texto deve ter entre 3 e 100 caracteres.";
            name = component.stringField(label, textInfo, 3, minError, 100, maxError);
        }

        confirmantion = component.confirmation("Deseja atualizar o endereço do abrigo?");
        if(confirmantion) {
            label = "Digite o novo endereço do abrigo:";
            textInfo = "Digite um texto com no mínimo 3 e no máximo 100 caracteres.";
            minError = "Endereço do abrigo inválido. O texto deve ter entre 3 e 100 caracteres.";
            String maxError = "Endereço do abrigo inválido. O texto deve ter entre 3 e 100 caracteres.";
            address = component.stringField(label, textInfo, 3, minError, 100, maxError);
        }

        confirmantion = component.confirmation("Deseja atualizar o telefone do abrigo?");
        if(confirmantion) {
            label = "Digite o novo telefone do abrigo:";
            textInfo = "Digite um texto com 11 caracteres.";
            minError = "Telefone do abrigo inválido. O texto deve ter 11 caracteres.";
            phone = component.stringField(label, textInfo, 11, minError, 11, null);
        }

        confirmantion = component.confirmation("Deseja atualizar o e-mail do abrigo?");
        if(confirmantion) {
            label = "Digite o novo e-mail do abrigo:";
            textInfo = "Digite um e-mail válido.";
            minError = "E-mail do abrigo inválido. O e-mail deve ser válido.";
            email = component.stringField(label, textInfo, 1, minError, null, null);
        }

        confirmantion = component.confirmation("Deseja atualizar o responsável do abrigo?");
        if(confirmantion) {
            label = "Digite o novo responsável do abrigo:";
            textInfo = "Digite um texto com no mínimo 3 e no máximo 100 caracteres.";
            minError = "Responsável do abrigo inválido. O texto deve ter entre 3 e 100 caracteres.";
            String maxError = "Responsável do abrigo inválido. O texto deve ter entre 3 e 100 caracteres.";
            responsible = component.stringField(label, textInfo, 3, minError, 100, maxError);
        }

        confirmantion = component.confirmation("Deseja atualizar a capacidade de pessoas do abrigo?");
        if(confirmantion) {
            label = "Digite a nova capacidade de pessoas do abrigo:";
            textInfo = "Digite um número inteiro positivo.";
            minError = "Capacidade de pessoas do abrigo inválida. O valor mínimo é 1.";
            capacityPeople = component.intField(label, textInfo, 1, minError, null, null);
        }

        if(capacityPeople > occupancy) confirmantion = component.confirmation("Deseja atualizar a ocupação do abrigo?");
        if(confirmantion || capacityPeople < occupancy) {
            label = "Digite a nova ocupação do abrigo:";
            textInfo = "Digite um número entre 0 e a capacidade de pessoas do abrigo.";
            minError = "Ocupação do abrigo inválida. O valor mínimo é 0.";
            String maxError = "Ocupação do abrigo inválida. O valor máximo é a capacidade de pessoas do abrigo.";
            occupancy = component.intField(label, textInfo, 0, minError, capacityPeople, maxError);
        }

        UpdateShelterRequestDto updateShelterRequestDto = new UpdateShelterRequestDto(
            id,
            name,
            address,
            phone,
            email,
            responsible,
            capacityPeople,
            occupancy
        );

        Response<ShelterResponseDto> updateShelterResponse = shelterController.updateShelter(updateShelterRequestDto);

        System.out.println();
        System.out.println(updateShelterResponse.getMessage());

        if(updateShelterResponse.getData() != null) component.printShelters(List.of(updateShelterResponse.getData()));
    }
}
