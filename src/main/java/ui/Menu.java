package ui;

import com.compass.donation.DonationController;
import controllers.donation.RegisterDonationController;
import controllers.donation.RegisterDonationByFile;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final DonationController donationController;
    private final RegisterDonationByFileUI registerDonationByFileUI;

    public Menu(Scanner scanner, DonationController donationController, RegisterDonationByFileUI registerDonationByFileUI) {
        this.scanner = scanner;
        this.donationController = donationController;
        this.registerDonationByFileUI = registerDonationByFileUI;
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
            System.out.println("1 - Registrar Doação Manuealmente");
            System.out.println("2 - Registrar Doação por Arquivo (CSV)");

            System.out.print("Escolha uma opção: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("Em construcao");
                    break;
                case 2:
                    registerDonationByFileUI.execute();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}