package ui.donation;

import com.compass.center.CenterController;
import com.compass.center.dtos.CenterResponseDto;
import com.compass.common.Response;
import com.compass.donation.DonationController;
import com.compass.donation.dtos.CreateDonationResponseDto;
import com.compass.donation.dtos.FindDonationResponseDto;
import com.compass.item.ItemController;
import com.compass.item.ItemService;
import com.compass.item.dtos.ItemDto;
import ui.Component;
import ui.exceptions.OperationCancelledException;
import ui.item.UpdateItemUI;

import java.util.List;

public class UpdateDonationUI {
    private final DonationController donationController;
    private final CenterController centerController;
    private final UpdateItemUI updateItemUI;
    private final Component component;

    public UpdateDonationUI(DonationController donationController, CenterController centerController, UpdateItemUI updateItemUI, Component component) {
        this.donationController = donationController;
        this.centerController = centerController;
        this.updateItemUI = updateItemUI;
        this.component = component;
    }

    public void execute() {

        while (true) {
            try {
                System.out.println();
                System.out.println("Atualizar doação");

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
                    System.out.print(" - Quantidade de itens: " + donation.items().size());
                    component.printListItem(donation.items());

                    menuUpdate(donationId.longValue(), donation.items());
                }

            }
            catch (OperationCancelledException exception) {
                System.out.println(exception.getMessage());
                break;
            }
            catch (Exception exception) {
                System.out.println("Erro ao buscar doação. Tente novamente.");
            }
        }
    }

    private void menuUpdate(Long donationId, List<ItemDto> items) throws OperationCancelledException {
        System.out.println();
        System.out.println("1 - Atualizar centro");
        System.out.println("2 - Atualizar itens");

        String label = "Escolha uma opção.";
        String textInfo = "Digite um número inteiro entre 1 e 2.";
        String minError = "Opção inválida. O valor mínimo é 1.";
        String maxError = "Opção inválida. O valor máximo é 2.";
        Integer option = component.intField(label, textInfo, 1, minError, 2, maxError);

        switch (option) {
            case 1:
                updateCenter(donationId);
                break;
            case 2:
                updateItems(donationId, items);
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private void updateCenter(Long donationId) throws OperationCancelledException {
        System.out.println();
        System.out.println("Atualizar centro");

        Response<List<CenterResponseDto>> centers = centerController.listAll();

        System.out.println();
        System.out.println(centers.getMessage());

        if (centers.getData() != null) {
            CenterResponseDto center = component.selectCenter(centers.getData());
            Response<CreateDonationResponseDto> response = donationController.updateCenter(donationId, center.id());

            System.out.println();
            System.out.println(response.getMessage());

            if (response.getData() != null) {
                CreateDonationResponseDto donation = response.getData();
                System.out.println();
                System.out.print("Doação ID: " + donation.donationId());
                System.out.println(" - Centro: " + donation.centerName());
                System.out.println(" - Quantidade de itens: " + donation.quantity());
            }
        }

        Boolean confirmation = component.confirmation("Deseja atualizar mais alguma doação?");
        if (!confirmation) throw new OperationCancelledException("Operação cancelada.");
    }

    private void updateItems(Long donationId, List<ItemDto> items) {
        System.out.println();
        System.out.println("Atualizar itens");

        component.printListItem(items);

        String label = "Selecione um Item, informe o ID.";
        String textInfo = "Digite um número inteiro positivo.";
        String minError = "ID inválido. O valor mínimo é 1.";
        Integer itemId = component.intField(label, textInfo, 1, minError, null, null);

        ItemDto item = items.stream().filter(i -> i.id().equals(itemId.longValue())).findFirst().orElse(null);
        if (item == null) {
            System.out.println();
            System.out.println("ID informado não corresponde a nenhum item.");
        }

        System.out.println();
        System.out.println("O que deseja fazer com o item?");
        System.out.println("1 - Atualizar informações do item");
        System.out.println("2 - Remover o item da lista");

        label = "Escolha uma opção.";
        textInfo = "Digite um número inteiro entre 0 e 2.";
        String maxError = "Opção inválida. O valor máximo é 2.";
        Integer option = component.intField(label, textInfo, 0, minError, 2, maxError);

        switch (option) {
            case 1:
                updateItemUI.menuUpdate(item);
                break;
            case 2:
                removerItem(donationId, item.id(), items.size());
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }

        Boolean confirmation = component.confirmation("Deseja atualizar mais alguma doação?");
        if (!confirmation) throw new OperationCancelledException("Operação cancelada.");
    }

    private void removerItem(Long donationId, Long ItemId, Integer itemsSize) {
        if(itemsSize == 1) {
            System.out.println();
            System.out.println("Há apenas um item na lista de doação, caso continue a doação será deletada");
            Boolean confirm = component.confirmation("Deseja realmente deletar essa doação?");
            if(!confirm) {
                System.out.println("Operação cancelada");
            }
            else {
                Response<CreateDonationResponseDto> response = donationController.delete(donationId);

                System.out.println();
                System.out.println(response.getMessage());

                if (response.getData() != null) {
                    CreateDonationResponseDto donationResponseDto = response.getData();
                    System.out.printf("Doação deletada: ID: %d - Centro: %s\n", donationResponseDto.donationId(), donationResponseDto.centerName());
                }
            }
        }
        else {
            Response<ItemDto> response = donationController.removerItem(donationId, ItemId);

            System.out.println();
            System.out.println(response.getMessage());

            if (response.getData() != null) component.printListItem(List.of(response.getData()));
        }

    }
}
