package ui;

import ui.exceptions.OperationCancelledException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Component {
    public static <E extends Enum<E>> E selectOption(Class<E> enumClass, Scanner scanner, String displayMessage) throws OperationCancelledException {
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

            try {
                option = scanner.nextInt();

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

    public static boolean confirmation(String message, Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println(message);
            System.out.println("1 - Sim");
            System.out.println("2 - Não");
            System.out.println("Digite a opção desejada: ");

            Integer option = scanner.nextInt();

            System.out.println();

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

    public static String stringField(Scanner scanner, String label, String textInfo, Integer min, String minErrorMsg, Integer max, String maxErrorMsg) throws OperationCancelledException {
        String input;
        List<String> error = new ArrayList<>();

        while (true) {
            System.out.println();
            System.out.println(label);
            System.out.println(textInfo);
            System.out.println("Digite '/sair' para cancelar a operação.");

            try {
                if(scanner.hasNext()) {
                    input = scanner.next().trim();
                } else {
                    scanner.nextLine();
                    continue;
                }

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

    public static Integer intField(Scanner scanner, String label, String textInfo, Integer min, String minErrorMsg, Integer max, String maxErrorMsg) throws OperationCancelledException {
        Integer input;
        List<String> error = new ArrayList<>();

        while (true) {
            System.out.println();
            System.out.println(label);
            System.out.println(textInfo);
            System.out.println("Digite '/sair' para cancelar a operação.");

            try {
                String inputStr;

                if(scanner.hasNext()) {
                    inputStr = scanner.next().trim();
                } else {
                    scanner.nextLine();
                    continue;
                }

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
                } catch (NumberFormatException e) {
                    System.out.println("Por favor, digite um número válido.");
                }
            } catch (OperationCancelledException e) {
                throw e;
            } catch (Exception e) {
                System.out.println("Erro ao ler a entrada. Por favor, tente novamente.");
            }
        }
    }
}