package ui.item;

import com.compass.common.Response;
import com.compass.item.ItemController;
import com.compass.item.dtos.ItemDto;
import com.compass.item.enums.*;
import ui.Component;

import java.time.LocalDate;
import java.util.List;

public class UpdateItemUI {
    private final ItemController itemController;
    private final Component component;

    public UpdateItemUI(ItemController itemController, Component component) {
        this.itemController = itemController;
        this.component = component;
    }

    public void execute() {
        System.out.println();
        System.out.println("Atualizar item");
    }

    public void menuUpdate(ItemDto item) {
        ItemDto itemDto = null;

        switch (item.category()) {
            case ROUPA -> itemDto = updateCothing(item);
            case ALIMENTO -> itemDto = updateFood(item);
            case HIGIENE -> itemDto = updateHygiene(item);
        }

        Response<ItemDto> response = itemController.update(itemDto);

        System.out.println();
        System.out.println(response.getMessage());

        if(response.getData() != null) {
            ItemDto updatedItem = response.getData();
            System.out.println();
            component.printListItem(List.of(updatedItem));
        }
    }

    private ItemDto updateCothing(ItemDto item) {
        SizeItem size = item.size();
        GenderItem gender = item.gender();
        String description = item.description();

        System.out.println("Atualizar roupa");

        Boolean confirmation = component.confirmation("Deseja atualizar o tamanho da roupa?");
        if(confirmation) size = component.selectOption(SizeItem.class, "Selecione o tamanho da roupa:");

        confirmation = component.confirmation("Deseja atualizar o gênero da roupa?");
        if(confirmation) gender = component.selectOption(GenderItem.class, "Selecione o gênero da roupa:");

        confirmation = component.confirmation("Deseja atualizar a descrição da roupa?");
        if (confirmation) {
            String label = "Digite a descrição do item:";
            String textInfo = "Digite uma descrição com até 100 caracteres.";
            String minErrorMsg = "Descrição inválida. O valor mínimo é 1.";
            String maxErrorMsg = "Descrição inválida. O valor máximo é 100.";
            description = component.stringField(label,textInfo, 3, minErrorMsg, 100, maxErrorMsg);
        }

        return new ItemDto(item.id(), description, null, item.category(), item.centerId(), size, gender, null, null, null);
    }

    private ItemDto updateFood(ItemDto item) {
        LocalDate expirationDate = item.expirationDate();
        Integer quantity = item.quantity();
        UnitItem unit = item.unit();
        String description = item.description();

        System.out.println("Atualizar alimento");

        Boolean confirmation = component.confirmation("Deseja atualizar a data de validade do alimento?");
        if(confirmation) {
            String label = "Digite a data de validade do alimento:";
            String textInfo = "Digite uma data no formato dd/MM/yyyy.";
            expirationDate = component.dateField(label, textInfo, false, false, true);
        }

        confirmation = component.confirmation("Deseja atualizar a quantidade do alimento?");
        if(confirmation) {
            String label = "Digite a quantidade do item:";
            String textInfo = "Digite um número inteiro positivo.";
            String minErrorMsg = "Quantidade inválida. O valor mínimo é 1.";
            quantity = component.intField(label, textInfo, 1, minErrorMsg, null, null);
        }

        confirmation = component.confirmation("Deseja atualizar a unidade do alimento?");
        if(confirmation) unit = component.selectOption(UnitItem.class, "Selecione a unidade do alimento:");

        confirmation = component.confirmation("Deseja atualizar a descrição do alimento?");
        if (confirmation) {
            String label = "Digite a descrição do item:";
            String textInfo = "Digite uma descrição com até 100 caracteres.";
            String minErrorMsg = "Descrição inválida. O valor mínimo é 1.";
            String maxErrorMsg = "Descrição inválida. O valor máximo é 100.";
            description = component.stringField(label,textInfo, 3, minErrorMsg, 100, maxErrorMsg);
        }

        return new ItemDto(item.id(), description, quantity, item.category(), item.centerId(), null, null, expirationDate, unit, null);
    }

    private ItemDto updateHygiene(ItemDto item) {
        HygieneTypeItem hygieneType = item.hygieneType();
        String description = item.description();

        System.out.println("Atualizar item de higiene");

        Boolean confirmation = component.confirmation("Deseja atualizar o tipo de item de higiene?");
        if(confirmation) hygieneType = component.selectOption(HygieneTypeItem.class, "Selecione o tipo de item de higiene:");

        confirmation = component.confirmation("Deseja atualizar a descrição do item de higiene?");
        if (confirmation) {
            String label = "Digite a descrição do item:";
            String textInfo = "Digite uma descrição com até 100 caracteres.";
            String minErrorMsg = "Descrição inválida. O valor mínimo é 1.";
            String maxErrorMsg = "Descrição inválida. O valor máximo é 100.";
            description = component.stringField(label,textInfo, 3, minErrorMsg, 100, maxErrorMsg);
        }

        return new ItemDto(item.id(), description, null, item.category(), item.centerId(), null, null, null, null, hygieneType);
    }
}
