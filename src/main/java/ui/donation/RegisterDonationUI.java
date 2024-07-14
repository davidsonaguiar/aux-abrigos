package ui.donation;

import com.compass.center.CenterController;
import com.compass.center.dtos.CenterResponseDto;
import com.compass.common.Response;
import com.compass.donation.DonationController;
import com.compass.donation.dtos.CreateDonationResponseDto;
import com.compass.donation.dtos.DonationDto;
import com.compass.item.dtos.ItemDto;
import com.compass.item.enums.*;
import ui.Component;
import ui.exceptions.OperationCancelledException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegisterDonationUI {
    private final DonationController donationController;
    private final CenterController centerController;
    private final Component component;

    public RegisterDonationUI(DonationController donationController, CenterController centerController, Component component) {
        this.donationController = donationController;
        this.component = component;
        this.centerController = centerController;
    }

    public void execute() {
        System.out.println();
        System.out.println("Registrar doação");

        try {
            Response<List<CenterResponseDto>> response = centerController.listAll();
            if (response.getData() == null) {
                System.out.println();
                System.out.println(response.getMessage());
                return;
            }

            List<CenterResponseDto> centers = response.getData();
            CenterResponseDto center = component.selectCenter(centers);
            System.out.println();
            System.out.println("Centro selecionado: " + center.name());

            List<ItemDto> items = new ArrayList<>();

            while(true) {
                CategoryItem category = component.selectOption(CategoryItem.class, "Selecione a categoria do item:");
                SizeItem size = null;
                GenderItem gender = null;
                LocalDate expirationDate = null;
                Integer quantity = null;
                UnitItem unit = null;
                HygieneTypeItem hygieneType = null;
                String description = null;

                if(category.equals(CategoryItem.ROUPA)) {
                    size = component.selectOption(SizeItem.class, "Selecione o tamanho da roupa:");
                    gender = component.selectOption(GenderItem.class, "Selecione o gênero da roupa:");
                }

                if(category.equals(CategoryItem.ALIMENTO)) {
                    String label = "Digite a data de validade do alimento:";
                    String textInfo = "Digite uma data no formato dd/MM/yyyy.";
                    expirationDate = component.dateField(label, textInfo, false, false, true);

                    label = "Digite a quantidade do item:";
                    textInfo = "Digite um número inteiro positivo.";
                    String minErrorMsg = "Quantidade inválida. O valor mínimo é 1.";
                    quantity = component.intField(label, textInfo, 1, minErrorMsg, null, null);

                    unit = component.selectOption(UnitItem.class, "Selecione a unidade do alimento:");
                }

                if(category.equals(CategoryItem.HIGIENE)) {
                    hygieneType = component.selectOption(HygieneTypeItem.class, "Selecione o tipo de item de higiene:");
                }

                String label = "Digite a descrição do item:";
                String textInfo = "Digite uma descrição com até 100 caracteres.";
                String minErrorMsg = "Descrição inválida. O valor mínimo é 1.";
                String maxErrorMsg = "Descrição inválida. O valor máximo é 100.";
                description = component.stringField(label,textInfo, 3, minErrorMsg, 100, maxErrorMsg);

                ItemDto item = new ItemDto(null, description, quantity, category, center.id(), size, gender, expirationDate, unit, hygieneType);
                items.add(item);

                Boolean confirmation = component.confirmation("Deseja adicionar mais um item? Caso não, a doação será descartada.");
                if(!confirmation) break;
            }

            System.out.println("Itens adicionados:");

            component.printListItem(items);

            Boolean confirmation = component.confirmation("Deseja confirmar o registro da doação?");
            if(!confirmation) return;

            DonationDto donation = new DonationDto(LocalDate.now(), center.id(), items);
            Response<CreateDonationResponseDto> responseDonationSave = donationController.save(donation);

            System.out.println();
            System.out.println(responseDonationSave.getMessage());

            if(responseDonationSave.getData() != null) {
                System.out.println();
                System.out.printf("ID da doação: %d%n", responseDonationSave.getData().donationId());
                System.out.printf("Centro: %s%n", responseDonationSave.getData().centerName());
                System.out.printf("Quantidade de itens: %d%n", responseDonationSave.getData().quantity());
            }
        } catch (OperationCancelledException exception) {
            Boolean confirmation = component.confirmation("Deseja cancelar o registro da doação?");
            if(confirmation) return;
        }
    }
}
