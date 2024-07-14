package ui.shelter;

import com.compass.shelter.ShelterController;
import com.compass.shelter.dtos.CreateShelterRequestDto;
import ui.Component;
import ui.exceptions.OperationCancelledException;

public class CreateShelterUI {
    private final ShelterController shelterController;
    private final Component component;

    public CreateShelterUI(ShelterController shelterController, Component component) {
        this.shelterController = shelterController;
        this.component = component;
    }

    public void execute() {
        while(true) {
            System.out.println();
            System.out.println("Criar abrigo");

            try {
                CreateShelterRequestDto formData = createShelterForm();
                shelterController.create(formData);
                System.out.println("Abrigo criado com sucesso!");
                break;
            }
            catch (OperationCancelledException e) {
                System.out.println("Operação cancelada.");
                break;
            }
        }
    }

    private CreateShelterRequestDto createShelterForm() throws OperationCancelledException {
        String label = "Informe o nome do abrigo:";
        String textInfo = "Digite um texto com no mínimo 3 e no máximo 100 caracteres.";
        String minError = "Nome do abrigo deve ter no mínimo 3 caracteres.";
        String maxError = "Nome do abrigo deve ter no máximo 100 caracteres.";
        String name = component.stringField(label, textInfo, 3, minError, 100, maxError);

        label = "Informe o endereço do abrigo:";
        textInfo = "Digite um texto com no mínimo 3 e no máximo 100 caracteres.";
        minError = "Endereço do abrigo deve ter no mínimo 3 caracteres.";
        maxError = "Endereço do abrigo deve ter no máximo 100 caracteres.";
        String address = component.stringField(label, textInfo, 3, minError, 100, maxError);

        label = "Informe o telefone do abrigo:";
        textInfo = "Digite um texto com 11 caracteres.";
        minError = "Telefone do abrigo deve ter 11 caracteres.";
        maxError = "Telefone do abrigo deve ter 11 caracteres.";
        String phone = component.stringField(label, textInfo, 11, minError, 11, maxError);

        label = "Informe o e-mail do abrigo:";
        textInfo = "O e-mail deve ser válido.";
        String email = component.emailField(label, textInfo);

        label = "Informe o nome do responsável do abrigo:";
        textInfo = "Digite um texto com no mínimo 3 e no máximo 100 caracteres.";
        minError = "Responsável do abrigo deve ter no mínimo 3 caracteres.";
        maxError = "Responsável do abrigo deve ter no máximo 100 caracteres.";
        String responsible = component.stringField(label, textInfo, 3, minError, 100, maxError);

        label = "Informe a capacidade de pessoas do abrigo:";
        textInfo = "Digite um número inteiro maior que 0.";
        minError = "Capacidade do abrigo deve ser maior que 0.";
        int capacityPeople = component.intField(label, textInfo, 1, minError, null, null);

        label = "Informe a ocupação do abrigo:";
        textInfo = "Digite um número inteiro maior ou igual a 0.";
        minError = "Ocupação do abrigo deve ser maior ou igual a 0.";
        maxError = "Ocupação do abrigo deve ser menor ou igual a capacidade de pessoas.";
        int occupancy = component.intField(label, textInfo, 0, minError, capacityPeople, maxError);

        return new CreateShelterRequestDto(name, address, phone, email, responsible, capacityPeople, occupancy);
    }
}
