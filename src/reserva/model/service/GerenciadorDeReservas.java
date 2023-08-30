package reserva.model.service;

import reserva.model.Assento;
import reserva.model.InicioReserva;
import reserva.model.Rota;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GerenciadorDeReservas {

    private List<Rota> rotas = new ArrayList<Rota>();
    private Scanner scanner;

    public GerenciadorDeReservas() {
        scanner = new Scanner(System.in);
    }


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
        int opcao;
        InicioReserva inicioReserva = new InicioReserva();

        StringBuilder stringBuilderMenuRota = new StringBuilder();
        stringBuilderMenuRota.append
                ("Bem Vindo A Viacao Santa Cruz. Vamos efetuar sua reserva! Escolha sua rota e horario: (digite um numero correspondente)\r\n");
        int numeroItem = 1;
        for (Rota rota : rotas) {
            stringBuilderMenuRota.append(String.format("%s - %s -> %s | %sh | R$ %s \r\n", numeroItem, rota.getPartida(),
                    rota.getDestino(), rota.getHora(), rota.getValor()));
            numeroItem++;
        }
        System.out.println(stringBuilderMenuRota.toString());

        Rota rotaSelecionada = null;
        do {
            opcao = scannerInput();
            if (opcao <= 0 || opcao > rotas.size()) {
                System.out.println("opcao invalida tente novamente!");
            } else {
                rotaSelecionada = rotas.get(opcao - 1);
                System.out.println(exibeDados(rotaSelecionada));
            }
        } while (rotaSelecionada == null);

        System.out.println("Digite a quantidade de reservas: (Digite apenas numeros!");

        int quantidadeReservas = Integer.parseInt(scanner.nextLine());

        List<Assento> assentosDisponiveis = rotaSelecionada.getListaAssentos().stream().filter(Assento::isDisponivel).collect(Collectors.toList());

        List<Integer> numeroAssentosReservar = getListaNumeroAsssentosReserva(quantidadeReservas, assentosDisponiveis);
    }

    private List<Integer> getListaNumeroAsssentosReserva(int quantidadeDesejadaReserva, List<Assento> assentos) {

        List<Integer> sequenciaDisponivel = new ArrayList<Integer>();

        int indiceAssentoInicial = 0;
        int count = 0;

        do {
            Assento assento = assentos.get(indiceAssentoInicial);
            int assentoEsperado = sequenciaDisponivel.size() > 0 ? sequenciaDisponivel.get(sequenciaDisponivel.size() - 1) + 1 : assento.getNumero();
            if (sequenciaDisponivel.size() == 0 || assento.getNumero() == assentoEsperado) {
                sequenciaDisponivel.add(assento.getNumero());
            } else {
                sequenciaDisponivel.clear();
            }
            indiceAssentoInicial = count++;
        } while (quantidadeDesejadaReserva > sequenciaDisponivel.size() && count <= assentos.size());

        return sequenciaDisponivel;
    }

    private String exibeDados(Rota rota) {
        return "Origem: " + rota.getPartida() + " | Destino: " + rota.getDestino() + " | Hora: " + rota.getHora() + "h | Valor: R$" + rota.getValor();
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
