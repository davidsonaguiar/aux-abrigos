package ui;

import com.compass.center.CenterEntity;
import com.compass.common.exception.NotFoundException;
import com.compass.donation.DonationEntity;
import com.compass.item.ItemEntity;
import com.compass.item.enums.*;
import ui.exceptions.NoCapacityException;
import ui.exceptions.OperationCancelledException;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

import static ui.Component.*;

public class RegisterDonationUI {
    public static void createDonationItems(DonationEntity donation, Scanner scanner) throws OperationCancelledException {
        while(true) {
            System.out.println();
            System.out.printf("Adicionar o %s item na lista de doação: ", donation.getItems().size() + 1);

            try {
                CategoryItem category = selectOption(CategoryItem.class, scanner, "Categorias disponíveis: ");;
                ItemEntity item = createItem(donation.getQuantityItemsByCategory(category), category, donation.getCenter(), scanner);
                donation.addItem(item);

                System.out.println();
                System.out.println("Item adicionado com sucesso!");

                Boolean shouldContinueAddingItems = confirmation("Deseja continuar adicionando itens para essa doação", scanner);
                if(!shouldContinueAddingItems) return;
            }
            catch (OperationCancelledException exception) {
                System.out.println("Operação cancelada!");
                Boolean shouldContinueAddingItems = confirmation("Deseja continuar adicionando itens para essa doação", scanner);
                if(!shouldContinueAddingItems) return;
            }
            catch (NoCapacityException exception) {
                System.out.println(exception.getMessage());
                System.out.println("Você poderá criar uma nova doação para outro centro!");
                Boolean shouldContinueAddingItems = confirmation("Deseja continuar adicionando itens para essa doação?", scanner);
                if(!shouldContinueAddingItems) return;
            }
        }
    }

    private static ItemEntity createItem(Integer quantityItemTypeInList, CategoryItem category, CenterEntity center, Scanner scanner) throws NoCapacityException, OperationCancelledException {
        try {
            Integer quantityItemInCenter = center.getAvailableCapacityForCategory(category);
            Integer availableCapacityForCategory = quantityItemInCenter - quantityItemTypeInList;

            if(availableCapacityForCategory == 0) {
                String text = "No centro há apenas uma capacidade de %s %s e você já adicionou nessa doação mais %s.\n";
                throw new NoCapacityException(String.format(text, quantityItemInCenter, category.getCategory(), quantityItemTypeInList));
            }

            ItemEntity item = new ItemEntity();
            item.setCategory(category);
            item.setCenter(center);

            String label = "Digite a descrição do item: ";
            String textInfo = "A descrição deve ter entre 3 e 100 caracteres e não pode ficar em branco!";
            String msgMin = "Descrição deve ter no mínimo 3 caracteres!";
            String msgMax = "Descrição deve ter no máximo 100 caracteres!";
            String description = stringField(scanner, label, textInfo, 3, msgMin, 100, msgMax);
            item.setDescription(description);

            System.out.println();
            String moreThanOne = availableCapacityForCategory > 1 ? "S" : "";
            String text = "**Quantidade de %s%s que ainda podem ser adicionada nessa lista de doação: %s.**\n";
            System.out.printf(text, category.getCategory(), moreThanOne, availableCapacityForCategory);

            label = "Digite a quantidade do item: ";
            textInfo = "A quantidade do item deve ser maior que 0 e menor ou igual a " + availableCapacityForCategory + "!";
            msgMin = "Valor informado é menor que o mínimo!";
            msgMax = "Valor informado é maior (" + availableCapacityForCategory + ") que a capacidade disponível para essa categoria de item!";
            Integer quantity = intField(scanner, label, textInfo, 1, msgMin, availableCapacityForCategory, msgMax);
            item.setQuantity(quantity);

            createSpecificsItem(item, scanner);

            return item;
        }
        catch (NoCapacityException exception) {
            throw exception;
        }
        catch (OperationCancelledException exception) {
            throw exception;
        }
    }

    private static void createSpecificsItem(ItemEntity item, Scanner scanner) {
        if(item.getCategory() == CategoryItem.HIGIENE) {
            createHygieneItem(item, scanner);
        } else if(item.getCategory() == CategoryItem.ALIMENTO) {
            createFoodItem(item, scanner);
        } else if(item.getCategory() == CategoryItem.ROUPA) {
            createClothingItem(item, scanner);
        }
    }

    private static void createHygieneItem(ItemEntity item, Scanner scanner) throws OperationCancelledException {
        HygieneTypeItem hygieneType = selectOption(HygieneTypeItem.class, scanner, "Tipos de higiene disponíveis");
        item.setHygieneType(hygieneType);
    }

    private static void createFoodItem(ItemEntity item, Scanner scanner) throws OperationCancelledException {
        String label = "Digite a data de validade do item (dd/MM/yyyy): ";
        String textInfo = "A data de validade deve ser maior que a data atual!";
        LocalDate expirationDate = dateField(scanner, label, textInfo,false,false, true);
        item.setExpirationDate(expirationDate);

        UnitItem unit = selectOption(UnitItem.class, scanner, "Unidades disponíveis");
        item.setUnit(unit);
    }

    private static void createClothingItem(ItemEntity item, Scanner scanner) throws OperationCancelledException {
        SizeItem size = selectOption(SizeItem.class, scanner, "Tamanhos disponíveis");
        item.setSize(size);

        GenderItem gender = selectOption(GenderItem.class, scanner, "Gêneros disponíveis");
        item.setGender(gender);
    }

    public static void listAllItemsOfDonation(DonationEntity donation) {
        if(donation.getItems().isEmpty()) {
            System.out.println("Nenhum item foi adicionado à doação");
            return;
        }
        System.out.println("Itens da doação");
        for(ItemEntity item : donation.getItems()) {
            System.out.println("-".repeat(50));
            System.out.println("Item");
            System.out.println("Categoria: " + item.getCategory().getCategory());
            System.out.println("Descrição: " + item.getDescription());
            System.out.println("Quantidade: " + item.getQuantity());
            if(item.getCategory() == CategoryItem.HIGIENE) {
                System.out.println("Tipo de higiene: " + item.getHygieneType().getType());
            } else if(item.getCategory() == CategoryItem.ALIMENTO) {
                System.out.println("Data de validade: " + item.getExpirationDate());
                System.out.println("Unidade: " + item.getUnit().getUnit());
            } else if(item.getCategory() == CategoryItem.ROUPA) {
                System.out.println("Tamanho: " + item.getSize().getSize());
                System.out.println("Gênero: " + item.getGender().getGender());
            }
        }
        System.out.println("-".repeat(50));
    }
}
