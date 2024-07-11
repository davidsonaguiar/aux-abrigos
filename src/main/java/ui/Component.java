package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Component {
    public static <E extends Enum<E>> E selectOption(Class<E> enumClass, Scanner scanner, String displayMessage) {
        E[] enumConstants = enumClass.getEnumConstants();
        System.out.println(displayMessage);

        Integer option = null;
        while (option == null) {
            for (int i = 0; i < enumConstants.length; i++) {
                System.out.println((i + 1) + " - " + enumConstants[i].toString());
            }
            System.out.println("0 - Sair");

            System.out.println("Digite o número da opção desejada: ");
            option = scanner.nextInt();

            if (option < 0 || option > enumConstants.length) {
                System.out.println("Opção inválida");
                option = null;
            } else if (option == 0) {
                return null;
            }
        }

        return enumConstants[option - 1];
    }

    public static boolean confirmation(String message, Scanner scanner) {
        while (true) {
            System.out.println(message);
            System.out.println("1 - Sim");
            System.out.println("2 - Não");
            System.out.println("Digite a opção desejada: ");

            Integer option = scanner.nextInt();

            switch (option) {
                case 1:
                    return true;
                case 2:
                    return false;
                default:
                    System.out.println("Opção inválida. Por favor, digite 1 para Sim ou 2 para Não.");
            }
        }
    }

    public static String stringField(Scanner scanner, String label, String textInfo, Integer min, String minErrorMsg, Integer max, String maxErrorMsg) {
        String input;
        List<String> error = new ArrayList<>();

        do {
            System.out.println(label);
            System.out.println(textInfo);
            scanner.useDelimiter("\n");

            if(scanner.hasNext()) {
                input = scanner.next().trim();
            } else {
                scanner.nextLine();
                continue;
            }

            if(min != null && input.length() < min) error.add(minErrorMsg);
            if(max != null && input.length() > max) error.add(maxErrorMsg);
            if(input.isBlank()) error.add("Campo não pode ficar em branco");

            if(error.isEmpty()) {
                return input;
            }
            else {
                System.out.println("Erro: " + String.join(", ", error));
                error.clear();
            }
        } while (true);
    }

    public static Integer intField(Scanner scanner, String label, String textInfo, Integer min, String minErrorMsg, Integer max, String maxErrorMsg) {
        Integer input;
        List<String> error = new ArrayList<>();

        do {
            System.out.println(label);
            System.out.println(textInfo);
            if(scanner.hasNextInt()) {
                input = scanner.nextInt();
                if(min != null && input < min) error.add(minErrorMsg);
                if(max != null && input > max) error.add(maxErrorMsg);
                if(error.isEmpty()) {
                    return input;
                }
                else {
                    System.out.println("Erro: " + String.join(", ", error));
                    error.clear();
                }
            } else {
                System.out.println("Por favor, digite um número válido.");
                scanner.next();
            }
        } while (true);
    }
}
