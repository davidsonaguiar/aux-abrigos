package ui;

import com.compass.donation.DonationService;
import controllers.RegisterDonation;
import controllers.RegisterDonationByFile;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final RegisterDonation registerDonation;
    private final RegisterDonationByFile registerDonationByFile;

    public Menu(Scanner scanner, RegisterDonation registerDonation, RegisterDonationByFile registerDonationByFile) {
        this.scanner = scanner;
        this.registerDonation = registerDonation;
        this.registerDonationByFile = registerDonationByFile;
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
                    registerDonation();
                    break;
                case 2:
                    registerDonationByFile();
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

    private void registerDonationByFile() {
        registerDonationByFile.execute();
    }
}