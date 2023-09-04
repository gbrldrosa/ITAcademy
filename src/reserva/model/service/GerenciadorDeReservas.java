package reserva.model.service;

import reserva.model.Assento;
import reserva.model.Cidade;
import reserva.model.InicioReserva;
import reserva.model.Rota;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GerenciadorDeReservas {

    private List<Rota> rotas = new ArrayList<Rota>();
    private Scanner scanner;

    public GerenciadorDeReservas() {
        rotas.add(new Rota(Cidade.PORTO_ALEGRE.getDesc(), Cidade.FLORIANOPOLIS.getDesc(), 6, 19.45, criarAssentos()));
        rotas.add(new Rota(Cidade.PORTO_ALEGRE.getDesc(), Cidade.FLORIANOPOLIS.getDesc(), 16, 23.50, criarAssentos()));
        rotas.add(new Rota(Cidade.PORTO_ALEGRE.getDesc(), Cidade.CRICIUMA.getDesc(), 6, 12.90, criarAssentos()));
        rotas.add(new Rota(Cidade.PORTO_ALEGRE.getDesc(), Cidade.CRICIUMA.getDesc(), 16, 15.90, criarAssentos()));
        rotas.add(new Rota(Cidade.CRICIUMA.getDesc(), Cidade.FLORIANOPOLIS.getDesc(), 10, 7.30, criarAssentos()));
        rotas.add(new Rota(Cidade.CRICIUMA.getDesc(), Cidade.FLORIANOPOLIS.getDesc(), 20, 10.30, criarAssentos()));
        scanner = new Scanner(System.in);

    }

    private List<Assento> criarAssentos() {
        List<Assento> assentos = new ArrayList<Assento>();

        for (int i = 1; i <= 46; i++){
            boolean disponivel = i > 7;
            assentos.add(new Assento(i, disponivel));
        }
        return assentos;
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

    private void cancelarReservaMenu() {
        System.out.println("Informe ticket para cancelamento: ");
        String numeroTicket = scanner.next();
        System.out.println("Numero ticket: " + numeroTicket);

        if (numeroTicket.isEmpty() || numeroTicket == null){
            System.out.println("Ticket nao informado");
        } else {
            cancelarReserva(numeroTicket);
        }
    }

    private void sair() {
        System.out.println("...Ate logo!...");
        System.exit(1);
    }

    private void dadosEstatisticos() {

        long totalAssentos = 0;
        long totalAssentosReservadosGeral = 0;
        long totalAssentosDisponiveisGeral = 0;

        for (Rota rota : this.rotas){
            long quantidadeAssentos = rota.getListaAssentos().stream().count();
            long quantidadeReservada = rota.getListaAssentos().stream().filter(a -> a.isDisponivel() == false).count();
            long quantidadeDisponivel = rota.getListaAssentos().stream().filter(a -> a.isDisponivel()).count();

            totalAssentos += quantidadeAssentos;
            totalAssentosReservadosGeral += quantidadeReservada;
            totalAssentosDisponiveisGeral += quantidadeDisponivel;

            System.out.println(rota.toString() + "| Quantidade reservado: " + quantidadeReservada + "| Quantidade disponivel: " + quantidadeDisponivel);
        }
        System.out.println("Total de assentos: " + totalAssentos);
        System.out.println("Total de assentos reservados: " + totalAssentosReservadosGeral);
        System.out.println("Total de assentos disponiveis: " + totalAssentosDisponiveisGeral);

    }

    private void cancelarReserva(String ticket) {

        Assento assentoCancelar = null;
        for (Rota rota : this.rotas) {
            Optional<Assento> assento = rota.getListaAssentos().stream().filter(a -> a.getIdTicket() != null && a.getIdTicket().equals(ticket)).findFirst();
            if (assento.isPresent()){
                assentoCancelar = assento.get();
                break;
            }
        }
        if (assentoCancelar != null){
            assentoCancelar.cancelarReserva();
            System.out.println("Assento cancelado com sucesso!");
        } else {
            System.out.println("Ticket nao encontrado!");
        }
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

        if (quantidadeReservas > 1) {
            System.out.println("Sequencia de assentos disponiveis: " + numeroAssentosReservar);

            for(Integer assento : numeroAssentosReservar){
                Assento assentoReservar = rotaSelecionada.getListaAssentos().stream().filter(a -> a.getNumero() == assento.intValue()).findFirst().get();
                assentoReservar.reservar();
                System.out.println("Assento numero: " + assentoReservar.getNumero() + "| Ticket: " + assentoReservar.getIdTicket() + "| Reservado com sucesso! ");
            }
        } else if(quantidadeReservas == 1) {
            System.out.println("Lista de assentos: ");
            assentosDisponiveis.forEach(System.out::println);

            System.out.println("Digite o numero do assento: (Digite apenas numeros!)");
            final int numeroAssento = scannerInput();

            Optional<Assento> optionalAssentoReservar = rotaSelecionada.getListaAssentos().stream().filter(a -> a.getNumero() == numeroAssento).findFirst();

            if (!optionalAssentoReservar.isPresent()){
                System.out.println("Numero assento invalido ");
            } else {
                if (!optionalAssentoReservar.get().isDisponivel()){
                    System.out.println("Assento indisponivel para reserva");
                } else {
                    optionalAssentoReservar.get().reservar();
                    System.out.println("Assento numero: " + optionalAssentoReservar.get().getNumero() + "| Ticket "
                            + optionalAssentoReservar.get().getIdTicket() + "| Reservado com sucesso");
                }
            }
        }
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

    private String exibeDadosAssentoSelecionado (Assento assento){
        return "Assento: " + assento.getNumero();
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
