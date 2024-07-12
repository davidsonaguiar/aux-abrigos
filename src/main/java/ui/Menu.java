package ui;

import com.compass.donation.DonationService;
import controllers.RegisterDonation;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final RegisterDonation registerDonation;

    public Menu(Scanner scanner, RegisterDonation registerDonation) {
        this.scanner = scanner;
        this.registerDonation = registerDonation;
    }

    public void execute() {
        boolean exit = false;
        while (!exit) {
            System.out.println();
            System.out.println("Menu Principal:");
            System.out.println("1 - Doações");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    donationMenu();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void donationMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println();
            System.out.println("Menu de Doações:");
            System.out.println("1 - Registrar Doação");
            System.out.println("2 - Editar Doação");
            System.out.println("3 - Deletar Doação");
            System.out.println("4 - Atualizar Doação");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    registerDonation();
                    break;
                case 2:
                    editDonation();
                    break;
                case 3:
                    deleteDonation();
                    break;
                case 4:
                    updateDonation();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void registerDonation() {
        registerDonation.execute();
    }

    private void editDonation() {
        System.out.println("Editar Doação");
    }

    private void deleteDonation() {
        System.out.println("Deletar Doação");
    }

    private void updateDonation() {
        System.out.println("Atualizar Doação");
    }
}