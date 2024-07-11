package ui;

import com.compass.center.CenterEntity;
import com.compass.donation.DonationEntity;
import com.compass.item.ItemEntity;
import com.compass.item.enums.*;
import controllers.exceptions.ItemCreationCancelledException;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

import static ui.Component.*;

public class RegisterDonationUI {
    public static CenterEntity selectCenter(Supplier<List<CenterEntity>> centerSupplier, Scanner scanner) {
        List<CenterEntity> centers = centerSupplier.get();
        if(centers.isEmpty()) return null;

        System.out.println("Centros disponíveis");

        Integer centerOption = null;

        while(centerOption == null) {
            for(int i = 0; i < centers.size(); i++) {
                System.out.println(i + 1 + " - " + centers.get(i).getName());
            }
            System.out.println("0 - Sair");

            System.out.println("Digite o número do centro desejado: ");
            centerOption = scanner.nextInt();

            if(centerOption < 0 || centerOption > centers.size()) {
                System.out.println("Opção inválida");
                centerOption = null;
            }

            if (centerOption == 0) return null;
        }

        return centers.get(centerOption - 1);
    }

    public static CategoryItem selectCategory(List<ItemEntity> itemsInList, CenterEntity center, Scanner scanner) {
        List<CategoryItem> categories = center.getCategoriesAvailableCapacity();

        if(categories.isEmpty()) {
            System.out.println("Capacidade disponível para todas as categorias esgotada");
            return null;
        }

        System.out.println("Categorias disponíveis");

        Integer categoryOption = null;

        while(categoryOption == null) {
            for(int i = 0; i < categories.size(); i++) {
                System.out.println(i + 1 + " - " + categories.get(i).getCategory());
            }
            System.out.println("0 - Sair");

            System.out.println("Digite o número da categoria desejada: ");
            categoryOption = scanner.nextInt();

            if(categoryOption < 0 || categoryOption > categories.size()) {
                System.out.println("Opção inválida");
                categoryOption = null;
            }

            if (categoryOption == 0) return null;
        }

        CategoryItem category = categories.get(categoryOption - 1);

        Integer quantityItemInCenter = center.getAvailableCapacityForCategory(category);
        Integer quantityItemTypeInList = itemsInList.stream().filter(itemInList -> itemInList.getCategory().equals(category)).mapToInt(ItemEntity::getQuantity).sum();
        Integer availableCapacityForCategory = quantityItemInCenter - quantityItemTypeInList;

        if(availableCapacityForCategory == 0) {
            System.out.println("Capacidade disponível para essa categoria de item esgotada");
            System.out.printf("No centro há apenas uma capacidade de %s %s e você já adicionou nessa doação mais %s.\n", quantityItemInCenter, category.getCategory(), quantityItemTypeInList);
            return null;
        }

        String moreThanOne = availableCapacityForCategory > 1 ? "S" : "";
        System.out.printf("Quantidade de %s%s que ainda podem ser adicionada nessa lista de doação: %s.\n", category.getCategory(), moreThanOne, availableCapacityForCategory);

        return category;
    }

    private static ItemEntity createItem(CategoryItem category, CenterEntity center, Scanner scanner) {
        try {
            ItemEntity item = new ItemEntity();
            item.setCategory(category);
            item.setCenter(center);

            String label = "Digite a descrição do item: ";
            String textInfo = "A descrição deve ter entre 3 e 100 caracteres e não pode ficar em branco!";
            String msgMin = "Descrição deve ter no mínimo 3 caracteres!";
            String msgMax = "Descrição deve ter no máximo 100 caracteres!";
            String description = stringField(scanner, label, textInfo, 3, msgMin, 100, msgMax);
            item.setDescription(description);

            label = "Digite a quantidade do item: ";
            textInfo = "A quantidade do item deve ser maior ou igual a 1!";
            msgMin = "Valor informado é menor que o mínimo!";
            Integer quantity = intField(scanner, label, textInfo, 1, msgMin,null, null);
            item.setQuantity(quantity);

            createSpecificsItem(item, scanner);

            return item;
        }
        catch (ItemCreationCancelledException e) {
            System.out.println("Criação do item cancelada");
            return null;
        }
        catch (Exception e) {
            System.out.println("Erro ao criar item: " + e.getMessage());
            return null;
        }

    }

    private static void createHygieneItem(ItemEntity item, Scanner scanner) {
        HygieneTypeItem hygieneType = selectOption(HygieneTypeItem.class, scanner, "Tipos de higiene disponíveis");
        if(hygieneType == null) throw new ItemCreationCancelledException();
        item.setHygieneType(hygieneType);
    }

    private static void createFoodItem(ItemEntity item, Scanner scanner) {
        System.out.println("Digite a data de validade do item: ");
        item.setExpirationDate(LocalDate.parse(scanner.next()));

        UnitItem unit = selectOption(UnitItem.class, scanner, "Unidades disponíveis");
        if(unit == null) throw new ItemCreationCancelledException();
        item.setUnit(unit);
    }

    private static void createClothingItem(ItemEntity item, Scanner scanner) {
        SizeItem size = selectOption(SizeItem.class, scanner, "Tamanhos disponíveis");
        if (size == null) throw new ItemCreationCancelledException();
        item.setSize(size);

        GenderItem gender = selectOption(GenderItem.class, scanner, "Gêneros disponíveis");
        if(gender == null) throw new ItemCreationCancelledException();
        item.setGender(gender);
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

    public static void createDonationItems(DonationEntity donation, Scanner scanner) {
        do {
            System.out.println(donation.itemsListIsEmpty() ? "Adicionar item à doação" : "Adicionar outro item à doação");
            CategoryItem category = selectCategory(donation.getItems(), donation.getCenter(), scanner);
            if(category == null) break;
            ItemEntity item = createItem(category, donation.getCenter(), scanner);
            donation.addItem(item);
            System.out.println("Item adicionado à doação");
            boolean continueAdding = confirmation("Deseja adicionar outro item à doação?", scanner);
            if(!continueAdding) {
                listAllItemsOfDonation(donation);
                boolean donationConfirmed = confirmation("Deseja confirmar a doação?", scanner);
                if(donationConfirmed) break;
            }
        } while(true);
    }

    public static void listAllItemsOfDonation(DonationEntity donation) {
        System.out.println("Itens da doação");
        for(ItemEntity item : donation.getItems()) {
            System.out.println("-".repeat(50));
            printItem(item);
        }
        System.out.println("-".repeat(50));
    }

    private static void printItem(ItemEntity item) {
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
}
