package reserva.model.service;

import java.util.Scanner;

public class GerenciadorDeReservas {


    private Scanner scanner;

    public void menuPrincipal() {
        StringBuilder stringBuilderMenu = new StringBuilder();
        stringBuilderMenu.append("Menu Principal");
        stringBuilderMenu.append("\n");
        stringBuilderMenu.append("1 - Reserva");
        stringBuilderMenu.append("\n");
        stringBuilderMenu.append("2 - Cancelar Reserva");
        stringBuilderMenu.append("\n");
        stringBuilderMenu.append("3 - Estatisticas");
        stringBuilderMenu.append("\n");
        stringBuilderMenu.append("4 - Sair");
        System.out.println(stringBuilderMenu.toString());

        int opcao = scannerInput();

        switch (opcao) {
            case 1:
                gerarReserva();
                menuPrincipal();
                break;

            case 2:
                cancelarReservaMenu();
                menuPrincipal();
                break;

            case 3:
                dadosEstatisticos();
                menuPrincipal();
                break;

            case 4:
                sair();
                menuPrincipal();
                break;

            default:
                break;
        }
    }

    private void sair() {
    }

    private void dadosEstatisticos() {
    }

    private void cancelarReservaMenu() {
    }

    private void gerarReserva() {

    }

    private int scannerInput() {
        String input = scanner.nextLine();
        boolean numeros = input.matches("^\\d+$");

        if (numeros) {
            return Integer.parseInt(input);
        }
        return -1;
    }
}
