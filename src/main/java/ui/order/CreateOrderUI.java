package ui.order;

import com.compass.center.CenterController;
import com.compass.center.dtos.CenterResponseDto;
import com.compass.common.Response;
import com.compass.item.dtos.ItemDto;
import com.compass.item.enums.*;
import com.compass.order.OrderController;
import com.compass.order.dtos.CreateOrderRequestDto;
import com.compass.order.dtos.OrderResponseDto;
import com.compass.shelter.ShelterController;
import com.compass.shelter.dtos.ShelterResponseDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import ui.Component;

import java.util.*;

public class CreateOrderUI {
    private final OrderController orderController;
    private final CenterController centerController;
    private final ShelterController shelterController;
    private final Component component;

    public CreateOrderUI(OrderController orderController, CenterController centerController, ShelterController shelterController, Component component) {
        this.orderController = orderController;
        this.centerController = centerController;
        this.shelterController = shelterController;
        this.component = component;
    }

    public void execute() {
        System.out.println();
        System.out.println("Criar pedido");

        Response<List<CenterResponseDto>> centersResponse = centerController.listAll();

        if(centersResponse.getData() == null) {
            System.out.println("Centros não encontrados");
            return;
        }

        String label = "Digite a quantidade de itens";
        String textInfo = "A quantidade deve ser um número inteiro maior que 0";
        String minError = "A quantidade de itens deve ser maior que 0";
        String maxError = "A quantidade de itens deve ser menor que 200";
        int quantity = component.intField(label, textInfo, 0, minError, 200, maxError);

        CategoryItem category = component.selectOption(CategoryItem.class, "Selecione a categoria do pedido");

        List<CenterResponseDto> centers = new ArrayList<>();

        List<CenterResponseDto> centerSorted = centersResponse.getData().stream()
                .sorted(Comparator.comparing((CenterResponseDto item) -> item.quantityCategory(category)).reversed())
                .toList();

        while (true) {
            System.out.println();
            System.out.println("Selecione o centro");

            for (int i = 0; i < centerSorted.size(); i++) {
                CenterResponseDto centerResponseDto = centerSorted.get(i);
                String centerInfo = (i + 1) +
                        " - " + centerResponseDto.name() +
                        " - " + centerResponseDto.quantityCategory(category) + " itens";
                System.out.println(centerInfo);
            }

            label = "Digite o ID do centro";
            textInfo = "O ID deve ser um número inteiro maior que 0";
            minError = "O ID do centro deve ser maior que 1";
            maxError = "O ID do centro deve ser menor que " + centerSorted.size();
            Integer centerId = component.intField(label, textInfo, 1, minError, centerSorted.size(), maxError);

            CenterResponseDto centerResponseDto = centerSorted.get(centerId - 1);

            if (centers.stream().anyMatch(center -> center.id().equals(centerResponseDto.id()))) {
                System.out.println("Centro já selecionado");
                continue;
            }

            centers.add(centerResponseDto);

            if(centers.size() == centerSorted.size()) break;

            Boolean confirmation = component.confirmation("Deseja adicionar mais um centro?");
            if (!confirmation) {
                break;
            }
        }

            ShelterResponseDto shelter;

        while(true) {
            label = "Digite o ID do abrigo";
            textInfo = "O ID deve ser um número inteiro positivo";
            minError = "O ID do abrigo deve ser maior ou igual a 1";
            Integer shelterId = component.intField(label, textInfo, 1, minError, null, null);

            Response<ShelterResponseDto> shelterResponseDto = shelterController.findById(shelterId.longValue());

            if(shelterResponseDto.getData() == null) {
                System.out.println(shelterResponseDto.getMessage());
                continue;
            }
            shelter = shelterResponseDto.getData();
            break;
        }

        if(category.equals(CategoryItem.ROUPA)) createOrderClothing(quantity, centers, shelter);
        if(category.equals(CategoryItem.ALIMENTO)) createOrderFood(quantity, centers, shelter);
        if(category.equals(CategoryItem.HIGIENE)) createOrderHygiene(quantity, centers, shelter);
    }

    public void createOrderClothing(Integer quantity, List<CenterResponseDto> centers, ShelterResponseDto shelter) {
        System.out.println();

        SizeItem size = component.selectOption(SizeItem.class, "Selecione o tamanho da roupa");
        GenderItem gender = component.selectOption(GenderItem.class, "Selecione o gênero da roupa");

        ItemDto item = new ItemDto(null, null, null, CategoryItem.ROUPA, null, size, gender, null, null, null);
        CreateOrderRequestDto createOrderRequestDto = new CreateOrderRequestDto(quantity, shelter.id(), item, centers);

        Response<OrderResponseDto> reaponse = orderController.create(createOrderRequestDto);

        System.out.println();
        System.out.println(reaponse.getMessage());

        if(reaponse.getData() != null) {
            printOrder(reaponse.getData());
        }
    }

    public void createOrderFood(Integer quantity, List<CenterResponseDto> centers, ShelterResponseDto shelter) {
        System.out.println();

        ItemDto item = new ItemDto(null, null, null, CategoryItem.ALIMENTO, null, null, null, null, null, null);
        CreateOrderRequestDto createOrderRequestDto = new CreateOrderRequestDto(quantity, shelter.id(), item, centers);

        Response<OrderResponseDto> reaponse = orderController.create(createOrderRequestDto);

        System.out.println();
        System.out.println(reaponse.getMessage());

        if(reaponse.getData() != null) {
            printOrder(reaponse.getData());
        }
    }

    public void createOrderHygiene(Integer quantity, List<CenterResponseDto> centers, ShelterResponseDto shelter) {
        System.out.println();

        HygieneTypeItem hygieneType = component.selectOption(HygieneTypeItem.class, "Selecione o tipo de higiene");

        ItemDto item = new ItemDto(null, null, null, CategoryItem.HIGIENE, null, null, null, null, null, hygieneType);
        CreateOrderRequestDto createOrderRequestDto = new CreateOrderRequestDto(quantity, shelter.id(), item, centers);

        Response<OrderResponseDto> reaponse = orderController.create(createOrderRequestDto);

        System.out.println();
        System.out.println(reaponse.getMessage());

        if(reaponse.getData() != null) {
            printOrder(reaponse.getData());
        }
    }

    public void printOrder(OrderResponseDto order) {
        System.out.println();
        System.out.println("ID do pedido: " + order.id());
        System.out.println("Quantidade de itens: " + order.quantity());
        System.out.println("Date de criação: " + order.date());
        System.out.println("Categoria: " + order.categoryItem());
        System.out.println("Centros solicitados:");
        for(CenterResponseDto center : order.centersRequest()) {
            System.out.println("Centro: " + center.name());
        }
    }
}
