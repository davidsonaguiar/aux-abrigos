package ui;

import com.compass.donation.DonationController;
import ui.donation.*;
import ui.exceptions.OperationCancelledException;
import ui.shelter.CreateShelterUI;

public class Menu {
    private RegisterDonationByFileUI registerDonationByFileUI;
    private RegisterDonationUI registerDonationUI;
    private ListDonationUI listDonationUI;
    private FindDonationUI findDonationUI;
    private UpdateDonationUI updateDonationUI;
    private DeleteDonationUI deleteDonationUI;

    private CreateShelterUI createShelterUI;

    private Component component;

    public Menu(Component component) {
        this.component = component;
    }

    public void getDonationUI(
            RegisterDonationByFileUI registerDonationByFileUI,
            RegisterDonationUI registerDonationUI,
            ListDonationUI listDonationUI,
            FindDonationUI findDonationUI,
            UpdateDonationUI updateDonationUI,
            DeleteDonationUI deleteDonationUI) {
        this.registerDonationByFileUI = registerDonationByFileUI;
        this.registerDonationUI = registerDonationUI;
        this.listDonationUI = listDonationUI;
        this.findDonationUI = findDonationUI;
        this.updateDonationUI = updateDonationUI;
        this.deleteDonationUI = deleteDonationUI;
    }

    public void getShelterUI(CreateShelterUI createShelterUI) {
        this.createShelterUI = createShelterUI;
    }

    public void execute() {
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println();
                System.out.println("Menu Principal:");
                System.out.println("1 - Doações");
                System.out.println("2 - Abrigos");

                String label = "Escollha uma opção:";
                String textInfo = "Digite um número inteiro entre 1 e 2.";
                String minError = "Opção inválida. O valor mínimo é 1.";
                String maxError = "Opção inválida. O valor máximo é 2.";

                Integer option = component.intField(label, textInfo, 0, minError, 2, maxError);

                switch (option) {
                    case 1:
                        donationMenu();
                        break;
                    case 2:
                        shelterMenu();
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

    private void shelterMenu() {
        System.out.println("Menu de Abrigos:");
        System.out.println("1 - Criar Abrigo");
        System.out.println("2 - Listar Abrigos");
        System.out.println("3 - Procurar Abrigo");
        System.out.println("4 - Atualizar Abrigo");
        System.out.println("5 - Deletar Abrigo");

        String label = "Escollha uma opção:";
        String textInfo = "Digite um número inteiro entre 1 e 5.";
        String minError = "Opção inválida. O valor mínimo é 1.";
        String maxError = "Opção inválida. O valor máximo é 5.";
        Integer option = component.intField(label, textInfo, 0, minError, 5, maxError);

        switch (option) {
            case 1:
                createShelterUI.execute();
                break;
            case 2:
                System.out.println("Listar Abrigos");
                break;
            case 3:
                System.out.println("Procurar Abrigo");
                break;
            case 4:
                System.out.println("Atualizar Abrigo");
                break;
            case 5:
                System.out.println("Deletar Abrigo");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
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
                System.out.println("6 - Deletar Doação");

                String label = "Escollha uma opção:";
                String textInfo = "Digite um número inteiro entre 1 e 6.";
                String minError = "Opção inválida. 1 valor mínimo é 0.";
                String maxError = "Opção inválida. 1 valor máximo é 6.";
                int option = component.intField(label, textInfo, 0, minError, 6, maxError);

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
                    case 6:
                        deleteDonationUI.execute();
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
            catch (OperationCancelledException exception) {
                break;
            }
        }
    }
}