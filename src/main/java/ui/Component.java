package ui;

import com.compass.center.dtos.CenterResponseDto;
import com.compass.common.exception.NotFoundException;
import com.compass.item.dtos.ItemDto;
import com.compass.shelter.dtos.ShelterResponseDto;
import ui.exceptions.OperationCancelledException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Component {
    private final Scanner scanner;

    public Component(Scanner scanner) {
        this.scanner = scanner;
    }

    public CenterResponseDto selectCenter(List<CenterResponseDto> centers)  throws OperationCancelledException {
        if(centers.isEmpty()) throw new NotFoundException("Não há centro cadastrado no sistema");

        System.out.println();
        System.out.println("Centros disponíveis");

        Integer centerOption = null;

        while(centerOption == null) {
            try {

                for(int i = 0; i < centers.size(); i++) {
                    System.out.println(i + 1 + " - " + centers.get(i).name());
                }
                System.out.println();
                System.out.println("Digite '/sair' para cancelar a operação.");
                System.out.println("Digite o número do centro desejado: ");
                System.out.print("Digite aqui -> ");

                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("/sair")) {
                    throw new OperationCancelledException("Operação cancelada pelo usuário.");
                }

                centerOption = Integer.parseInt(input);

                if(centerOption < 0 || centerOption > centers.size()) {
                    System.out.println();
                    System.out.println("Opção inválida");
                    System.out.println();
                    centerOption = null;
                }

                if (centerOption == 0) throw new OperationCancelledException("Operação cancelada pelo usuário.");
            }
            catch (OperationCancelledException exception) {
                throw exception;
            }
            catch (NumberFormatException exception) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
                centerOption = null;
            }
        }

        return centers.get(centerOption - 1);
    }

    public <E extends Enum<E>> E selectOption(Class<E> enumClass, String displayMessage) throws OperationCancelledException {
        E[] enumConstants = enumClass.getEnumConstants();

        System.out.println();
        System.out.println(displayMessage);

        Integer option = null;

        while (option == null) {
            for (int i = 0; i < enumConstants.length; i++) {
                System.out.println((i + 1) + " - " + enumConstants[i].toString());
            }
            System.out.println("0 - Para cancelar operação");

            System.out.println("Digite o número da opção desejada: ");
            System.out.print("Digite aqui -> ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                if (option < 0 || option > enumConstants.length) {
                    System.out.println("Opção inválida");
                    option = null;
                } else if (option == 0) {
                    throw new OperationCancelledException("Operação cancelada pelo usuário.");
                }
            }
            catch (OperationCancelledException exception) {
                throw exception;
            }
            catch (InputMismatchException exception) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
                option = null;
            }
        }

        return enumConstants[option - 1];
    }

    public boolean confirmation(String message) throws NumberFormatException {
        while (true) {
            try {
                System.out.println();
                System.out.println(message);
                System.out.println("1 - Sim");
                System.out.println("2 - Não");
                System.out.println("Digite a opção desejada: ");
                System.out.print("Digite aqui -> ");

                Integer option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        return true;
                    case 2:
                        return false;
                    default:
                        System.out.println("Opção inválida. Por favor, digite 1 para Sim ou 2 para Não.");
                }
            } catch (NumberFormatException exception) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }


    public String stringField(String label, String textInfo, Integer min, String minErrorMsg, Integer max, String maxErrorMsg) throws OperationCancelledException {
        String input;
        List<String> error = new ArrayList<>();

        while (true) {
            System.out.println();
            System.out.println(label);
            System.out.println(textInfo);
            System.out.println("Digite '/sair' para cancelar a operação.");
            System.out.print("Digite aqui -> ");

            try {
                input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("/sair")) {
                    throw new OperationCancelledException("Operação cancelada pelo usuário.");
                }

                if (input.isEmpty()) {
                    error.add("Campo não pode ficar em branco");
                }
                if (min != null && input.length() < min) {
                    error.add(minErrorMsg);
                }
                if (max != null && input.length() > max) {
                    error.add(maxErrorMsg);
                }

                if (error.isEmpty()) {
                    return input;
                } else {
                    System.out.println("Erro: " + String.join(", ", error));
                    error.clear();
                }
            } catch (OperationCancelledException e) {
                throw e;
            } catch (Exception e) {
                System.out.println("Erro ao ler a entrada. Por favor, tente novamente.");
                scanner.nextLine();
            }
        }
    }

    public Integer intField(String label, String textInfo, Integer min, String minErrorMsg, Integer max, String maxErrorMsg) throws OperationCancelledException {
        Integer input;
        List<String> error = new ArrayList<>();

        while (true) {
            System.out.println();
            System.out.println(label);
            System.out.println(textInfo);
            System.out.println("Digite '/sair' para cancelar a operação.");
            System.out.print("Digite aqui -> ");

            try {
                String inputStr = scanner.nextLine().trim();

                if (inputStr.equalsIgnoreCase("/sair")) throw new OperationCancelledException("Operação cancelada pelo usuário.");

                try {
                    input = Integer.parseInt(inputStr);

                    if (min != null && input < min) {
                        error.add(minErrorMsg);
                    }
                    if (max != null && input > max) {
                        error.add(maxErrorMsg);
                    }

                    if (error.isEmpty()) {
                        return input;
                    } else {
                        System.out.println("Erro: " + String.join(", ", error));
                        error.clear();
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println("Por favor, digite um número válido.");
                }
            }
            catch (OperationCancelledException e) {
                throw e;
            }
            catch (Exception e) {
                System.out.println("Erro ao ler a entrada. Por favor, tente novamente.");
            }
        }
    }

    public LocalDate dateField(String label, String textInfo, Boolean allowPast, Boolean allowPresent, Boolean allowFuture) throws OperationCancelledException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date;
        List<String> error = new ArrayList<>();

        while (true) {
            System.out.println();
            System.out.println(label);
            System.out.println(textInfo);
            System.out.println("Digite '/sair' para cancelar a operação.");
            System.out.print("Digite aqui -> ");

            String input = scanner.nextLine().trim();

            try {
                if (input.equalsIgnoreCase("/sair")) {
                    throw new OperationCancelledException("Operação cancelada pelo usuário.");
                }

                if (input.isEmpty()) {
                    throw new DateTimeParseException("Input vazio", input, 0);
                }

                date = LocalDate.parse(input, formatter);

                LocalDate currentDate = LocalDate.now();

                if (!allowPast && date.isBefore(currentDate)) {
                    error.add("Data no passado não é permitida.");
                }
                if (!allowPresent && date.isEqual(currentDate)) {
                    error.add("Data de hoje não é permitida.");
                }
                if (!allowFuture && date.isAfter(currentDate)) {
                    error.add("Data futura não é permitida.");
                }

                if (error.isEmpty()) {
                    return date;
                } else {
                    System.out.println("Erro: " + String.join(", ", error));
                    error.clear();
                }
            }
            catch (OperationCancelledException exception) {
                throw exception;
            }
            catch (DateTimeParseException exception) {
                System.out.println("Formato de data inválido. Por favor, use o formato (dd/MM/yyyy)");
            }
        }
    }

    public File fileField(String label, String textInfo, String type) throws OperationCancelledException {
        List<String> error = new ArrayList<>();

        while (true) {
            System.out.println();
            System.out.println(label);
            System.out.println(textInfo);
            System.out.println("Digite '/sair' para cancelar a operação.");
            System.out.print("Digite aqui -> ");

            String input = scanner.nextLine().trim();

            try {
                if (input.equalsIgnoreCase("/sair")) {
                    throw new OperationCancelledException("Operação cancelada pelo usuário.");
                }

                Path filePath = Paths.get(input);
                if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
                    error.add("Arquivo não encontrado");
                } else {
                    String fileName = filePath.getFileName().toString();
                    String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);

                    if (!fileExtension.equalsIgnoreCase(type)) {
                        error.add("Extensão do arquivo incorreta!");
                    }
                }

                if (error.isEmpty()) {
                    return filePath.toFile();
                } else {
                    System.out.println("Erro: " + String.join(", ", error));
                    error.clear();
                }
            } catch (OperationCancelledException exception) {
                throw exception;
            } catch (Exception exception) {
                System.out.println("Erro ao ler a entrada. Por favor, tente novamente.");
            }
        }
    }

    public String emailField(String label, String textInfo) throws OperationCancelledException {
        List<String> error = new ArrayList<>();

        while (true) {
            System.out.println();
            System.out.println(label);
            System.out.println(textInfo);
            System.out.println("Digite '/sair' para cancelar a operação.");
            System.out.print("Digite aqui -> ");

            try {
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("/sair")) throw new OperationCancelledException("Operação cancelada pelo usuário.");
                if (input.isEmpty()) error.add("Campo não pode ficar em branco");
                if (!input.contains("@") || !input.contains(".")) error.add("E-mail inválido");

                if (error.isEmpty()) {
                    return input;
                } else {
                    System.out.println("Erro: " + String.join(", ", error));
                    error.clear();
                }
            } catch (OperationCancelledException e) {
                throw e;
            } catch (Exception e) {
                System.out.println("Erro ao ler a entrada. Por favor, tente novamente.");
                scanner.nextLine();
            }
        }
    }

    public void printListItem(List<ItemDto> items) {
        Integer idColLength = 5;
        Integer descritionColLength = 35;
        Integer quantityColLength = 10;
        Integer categoryColLength = 10;
        Integer sizeColLength = 10;
        Integer genderColLength = 10;
        Integer expirationDateColLength = 20;
        Integer unitColLength = 5;
        Integer hygieneTypeColLength = 10;

        String line = "%s | %s | %s | %s | %s | %s | %s | %s | %s | %s\n";

        System.out.println();
        System.out.println("Itens da doação:");
        System.out.printf(line,
                padRight("ID", idColLength),
                padRight("Quantidade", quantityColLength),
                padRight("Categoria", categoryColLength),
                padRight("Tamanho", sizeColLength),
                padRight("Gênero", genderColLength),
                padRight("Tipo", hygieneTypeColLength),
                padRight("Data de validade", expirationDateColLength),
                padRight("Unidade", unitColLength),
                padRight("Descrição", descritionColLength)
        );

        items.forEach(item -> {
            String ifNull = "x";
            String id = item.id() == null ? ifNull : item.id().toString();
            String size = item.size() == null ? ifNull : item.size().getSize();
            String gender = item.gender() == null ? ifNull : item.gender().getGender();
            String expirationDate = item.expirationDate() == null ? ifNull : item.expirationDate().toString();
            String quantity = item.quantity() == null ? ifNull : item.quantity().toString();
            String unit = item.unit() == null ? ifNull : item.unit().name();
            String hygieneType = item.hygieneType() == null ? ifNull : item.hygieneType().name();

            System.out.printf(line,
                    padRight(id, idColLength),
                    padRight(quantity, quantityColLength),
                    padRight(item.category().name(), categoryColLength),
                    padRight(size, sizeColLength),
                    padRight(gender, genderColLength),
                    padRight(hygieneType, hygieneTypeColLength),
                    padRight(expirationDate, expirationDateColLength),
                    padRight(unit, unitColLength),
                    padRight(item.description(), descritionColLength)
            );
        });
    }

    public void printShelters(List<ShelterResponseDto> items) {
        Integer idColLength = 3;
        Integer nameColLength = 25;
        Integer addressColLength = 25;
        Integer phoneColLength = 11;
        Integer emailColLength = 20;
        Integer responsibleColLength = 15;
        Integer capacityItemColLength = 10;
        Integer capacityPeopleColLength = 10;
        Integer capacityOccupancyColLength = 10;
        Integer capacityOccupancyPercentageColLength = 10;

        String line = "%s | %s | %s | %s | %s | %s | %s | %s | %s | %s\n";

        System.out.println();
        System.out.println("Abrigados disponíveis:");
        System.out.printf(line,
                padRight("ID", idColLength),
                padRight("Nome", nameColLength),
                padRight("Endereço", addressColLength),
                padRight("Telefone", phoneColLength),
                padRight("E-mail", emailColLength),
                padRight("Responsável", responsibleColLength),
                padRight("Cap. itens", capacityItemColLength),
                padRight("Cap. pessoas", capacityPeopleColLength),
                padRight("Ocupação", capacityOccupancyColLength),
                padRight("Percentual de ocupação", capacityOccupancyPercentageColLength)
        );

        items.forEach(shelter -> {
            Integer percentage = (shelter.occupancy() * 100) / shelter.capacityPeople();

            System.out.printf(line,
                    padRight(shelter.id().toString(), idColLength),
                    padRight(shelter.name(), nameColLength),
                    padRight(shelter.address(), addressColLength),
                    padRight(shelter.phone(), phoneColLength),
                    padRight(shelter.email(), emailColLength),
                    padRight(shelter.responsible(), responsibleColLength),
                    padRight(shelter.capacityItem().toString(), capacityItemColLength),
                    padRight(shelter.capacityPeople().toString(), capacityPeopleColLength),
                    padRight(shelter.occupancy().toString(), capacityOccupancyColLength),
                    padRight(percentage.toString() + "%" , capacityOccupancyColLength)
            );
        });
    }

    private String padRight(String text, Integer sizeCol) {
        if(text == null) text = "";
        if(text.length() > sizeCol)
            return text.substring(0, sizeCol) + "\t";
        return text + " ".repeat(sizeCol - text.length()) + "\t";
    }
}