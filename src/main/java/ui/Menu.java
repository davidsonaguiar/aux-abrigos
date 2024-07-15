package ui;

import com.compass.donation.DonationController;
import ui.donation.*;
import ui.exceptions.OperationCancelledException;
import ui.order.CreateOrderUI;
import ui.shelter.*;

public class Menu {
    private RegisterDonationByFileUI registerDonationByFileUI;
    private RegisterDonationUI registerDonationUI;
    private ListDonationUI listDonationUI;
    private FindDonationUI findDonationUI;
    private UpdateDonationUI updateDonationUI;
    private DeleteDonationUI deleteDonationUI;

    private CreateShelterUI createShelterUI;
    private ListShelterUI listShelterUI;
    private FindShelderUI findShelterUI;
    private UpdateShelterUI updateShelterUI;
    private DeleteShelterUI deleteShelterUI;

    private CreateOrderUI createOrderUI;

    private Component component;

    public Menu(Component component) {
        this.component = component;
    }

    public void execute() {
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println();
                System.out.println("Menu Principal:");
                System.out.println("1 - Doações");
                System.out.println("2 - Abrigos");
                System.out.println("3 - Pedidos");

                String label = "Escollha uma opção:";
                String textInfo = "Digite um número inteiro entre 1 e 3.";
                String minError = "Opção inválida. O valor mínimo é 1.";
                String maxError = "Opção inválida. O valor máximo é 3.";

                Integer option = component.intField(label, textInfo, 0, minError, 3, maxError);

                switch (option) {
                    case 1:
                        donationMenu();
                        break;
                    case 2:
                        shelterMenu();
                        break;
                    case 3:
                        orderMenu();
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

    private void orderMenu() {
        while(true) {
            try {
                System.out.println();
                System.out.println("Menu de Pedidos:");
                System.out.println("1 - Criar Pedido");
                System.out.println("2 - Listar Pedidos");
                System.out.println("3 - Procurar Pedido");
                System.out.println("4 - Atualizar Pedido");
                System.out.println("5 - Deletar Pedido");

                String label = "Escollha uma opção:";
                String textInfo = "Digite um número inteiro entre 1 e 5.";
                String minError = "Opção inválida. O valor mínimo é 1.";
                String maxError = "Opção inválida. O valor máximo é 5.";
                Integer option = component.intField(label, textInfo, 0, minError, 5, maxError);

                switch (option) {
                    case 1:
                        createOrderUI.execute();
                        break;
                    case 2:
                        System.out.println("Listar Pedidos");
                        break;
                    case 3:
                        System.out.println("Procurar Pedido");
                        break;
                    case 4:
                        System.out.println("Atualizar Pedido");
                        break;
                    case 5:
                        System.out.println("Deletar Pedido");
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

    private void shelterMenu() {
        while(true) {
            try {
                System.out.println();
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
                        listShelterUI.execute();
                        break;
                    case 3:
                        findShelterUI.execute();
                        break;
                    case 4:
                        updateShelterUI.execute();
                        break;
                    case 5:
                        deleteShelterUI.execute();
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

    public void getOrderUI(CreateOrderUI createOrderUI) {
        this.createOrderUI = createOrderUI;
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

    public void getShelterUI(
            CreateShelterUI createShelterUI,
            ListShelterUI listShelterUI,
            FindShelderUI findShelterUI,
            UpdateShelterUI updateShelterUI,
            DeleteShelterUI deleteShelterUI) {
        this.createShelterUI = createShelterUI;
        this.listShelterUI = listShelterUI;
        this.findShelterUI = findShelterUI;
        this.updateShelterUI = updateShelterUI;
        this.deleteShelterUI = deleteShelterUI;
    }
}