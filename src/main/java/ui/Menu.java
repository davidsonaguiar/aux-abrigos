package ui;

import com.compass.donation.DonationController;
import ui.donation.*;
import ui.exceptions.OperationCancelledException;

public class Menu {
    private final DonationController donationController;
    private final RegisterDonationByFileUI registerDonationByFileUI;
    private final RegisterDonationUI registerDonationUI;
    private final ListDonationUI listDonationUI;
    private final FindDonationUI findDonationUI;
    private final UpdateDonationUI updateDonationUI;
    private final Component component;

    public Menu(Component component, DonationController donationController, RegisterDonationByFileUI registerDonationByFileUI, RegisterDonationUI registerDonationUI, ListDonationUI listDonationUI, FindDonationUI findDonationUI, UpdateDonationUI updateDonationUI) {
        this.component = component;
        this.donationController = donationController;
        this.registerDonationByFileUI = registerDonationByFileUI;
        this.registerDonationUI = registerDonationUI;
        this.listDonationUI = listDonationUI;
        this.findDonationUI = findDonationUI;
        this.updateDonationUI = updateDonationUI;
    }

    public void execute() {
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println();
                System.out.println("Menu Principal:");
                System.out.println("1 - Doações");

                String label = "Escollha uma opção:";
                String textInfo = "Digite um número inteiro entre 1 e 1.";
                String minError = "Opção inválida. O valor mínimo é 1.";
                String maxError = "Opção inválida. O valor máximo é 1.";

                int option = component.intField(label, textInfo, 0, minError, 1, maxError);

                switch (option) {
                    case 1:
                        donationMenu();
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (OperationCancelledException exception) {
                System.out.println("Operação cancelada.");
                Boolean finish = component.confirmation("Deseja sair do sistema?");
                if (finish) exit = true;
            }
        }
    }

    private void donationMenu() {
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println();
                System.out.println("Menu de Doações:");
                System.out.println("1 - Registrar Doação Manuealmente");
                System.out.println("2 - Registrar Doação por Arquivo (CSV)");
                System.out.println("3 - Listar Doações");
                System.out.println("4 - Procurar Doações");
                System.out.println("5 - Atualizar Doação");

                String label = "Escollha uma opção:";
                String textInfo = "Digite um número inteiro entre 0 e 5.";
                String minError = "Opção inválida. O valor mínimo é 0.";
                String maxError = "Opção inválida. O valor máximo é 5.";
                int option = component.intField(label, textInfo, 0, minError, 5, maxError);

                switch (option) {
                    case 1:
                        registerDonationUI.execute();
                        break;
                    case 2:
                        registerDonationByFileUI.execute();
                        break;
                    case 3:
                        listDonationUI.execute();
                        break;
                    case 4:
                        findDonationUI.execute();
                        break;
                    case 5:
                        updateDonationUI.execute();
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
            catch (OperationCancelledException exception) {
                System.out.println("Operação cancelada.");
                Boolean finish = component.confirmation("Deseja voltar para o menu principal?");
                return;
            }
        }
    }
}