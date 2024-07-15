package ui.order;

import com.compass.center.CenterController;
import com.compass.center.dtos.CenterResponseDto;
import com.compass.common.Response;
import com.compass.item.dtos.ItemDto;
import com.compass.item.enums.CategoryItem;
import com.compass.order.OrderController;
import com.compass.order.dtos.OrderResponseDto;
import com.compass.order.enums.StatusOrder;
import ui.Component;

import java.util.ArrayList;
import java.util.List;

public class ListOrderUI {
    private final OrderController orderController;
    private final CenterController centerController;
    private final Component component;

    public ListOrderUI(OrderController orderController, CenterController centerController, Component component) {
        this.orderController = orderController;
        this.centerController = centerController;
        this.component = component;
    }

    public void execute() {
        System.out.println();
        System.out.println("Listar pedidos");

        while(true) {
            System.out.println();
            System.out.println("1 - Listar todos os pedidos");
            System.out.println("2 - Listar pedidos por abrigo");
            System.out.println("3 - Listar pedidos por centro de doação");
            System.out.println("4 - Voltar");

            String label = "Escolha uma opção:";
            String textInfo = "Digite um número inteiro entre 1 e 4.";
            String minError = "Opção inválida. O valor mínimo é 1.";
            String maxError = "Opção inválida. O valor máximo é 4.";
            Integer option = component.intField(label, textInfo, 0, minError, 4, maxError);

            switch (option) {
                case 1:
                    listAll();
                    break;
                case 2:
                    listByShelter();
                    break;
                case 3:
                    listByCenter();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        }
    }

    private void listAll() {
        Response<List<OrderResponseDto>> ordersResponse = orderController.listAll();

        System.out.println();
        System.out.println(ordersResponse.getMessage());

        if(ordersResponse.getData() != null) {
            component.printOrders(ordersResponse.getData());
        }
    }

    private void listByShelter() {
        System.out.println();
        System.out.println("Listar pedidos por abrigo");

        while(true) {
            String label = "Digite o ID do abrigo";
            String textInfo = "O ID deve ser um número inteiro maior que 0";
            String minError = "O ID do abrigo deve ser maior que 0";
            Integer shelterId = component.intField(label, textInfo, 1, minError, null, null);

            Response<List<OrderResponseDto>> ordersResponse = orderController.listByShelter(shelterId.longValue());

            System.out.println();
            System.out.println(ordersResponse.getMessage());

            if(ordersResponse.getData() != null) {
                component.printOrders(ordersResponse.getData());
                break;
            }
        }
    }

    private void listByCenter() {
        System.out.println();
        System.out.println("Listar pedidos por centro de doação");

        Response<List<CenterResponseDto>> centers = centerController.listAll();

        if(centers.getData() == null) {
            System.out.println(centers.getMessage());
            return;
        }

        while(true) {
            CenterResponseDto center = component.selectCenter(centers.getData());

            Response<List<OrderResponseDto>> ordersResponse = orderController.listByCenter(center.id().longValue());

            System.out.println();
            System.out.println(ordersResponse.getMessage());

            if(ordersResponse.getData() != null) {
                component.printOrders(ordersResponse.getData());

                Boolean confirmation = component.confirmation("Deseja fazer checkout de algum pedido?");
                if(!confirmation) break;

                checkout(center, ordersResponse.getData());
            }
            else {
                Boolean tryAgain = component.confirmation("Deseja tentar novamente?");
                if(!tryAgain) break;
            }
        }
    }

    private void checkout(CenterResponseDto center, List<OrderResponseDto> data) {
        while(true) {
            component.printOrders(data);

            String label = "Digite o ID do pedido que deseja fazer checkout";
            String textInfo = "Selecione um pedido pendente";
            String minError = "O ID do pedido deve ser maior que 0";
            Integer orderId = component.intField(label, textInfo, 1, minError, null, null);

            OrderResponseDto order = null;

            for (OrderResponseDto orderResponseDto : data) {
                if(orderResponseDto.id().equals(orderId.longValue())) {
                    order = orderResponseDto;
                    break;
                }
            }

            if(order == null) {
                System.out.println("Pedido não encontrado. Tente novamente.");
                continue;
            }

            Boolean confirmation = component.confirmation("Deseja aceitar o pedido?");
            StatusOrder status = confirmation ? StatusOrder.ACEITO : StatusOrder.RECUSADO;

            List<ItemDto> itemsEgualsOrdermItem = new ArrayList<>();


            for(ItemDto item : center.items()) {
                if(order.categoryItem().equals(item.category())) {
                    switch (order.categoryItem()) {
                        case ROUPA:
                            if (item.size().equals(order.sizeItem()) && item.gender().equals(order.genderItem()))
                                itemsEgualsOrdermItem.add(item);
                            break;
                        case ALIMENTO:
                            itemsEgualsOrdermItem.add(item);
                            break;
                        case HIGIENE:
                            if (item.hygieneType().equals(order.hygieneType()))
                                itemsEgualsOrdermItem.add(item);
                            break;
                        default:
                            break;
                    }
                }
            }

            if(itemsEgualsOrdermItem.isEmpty() && status.equals(StatusOrder.ACEITO)) {
                System.out.println("Não há itens disponíveis para atender o pedido.");
                break;
            }

            Response<OrderResponseDto> checkoutResponse = orderController.checkout(center.id().longValue(), order.id().longValue(), status);

            System.out.println();
            System.out.println(checkoutResponse.getMessage());

            if(checkoutResponse.getData() != null) {
                component.printOrders(List.of(checkoutResponse.getData()));
                break;
            }
            else {
                Boolean tryAgain = component.confirmation("Deseja tentar novamente?");
                if(!tryAgain) break;
            }
        }
    }
}
