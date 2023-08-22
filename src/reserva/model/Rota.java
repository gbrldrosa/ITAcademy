package reserva.model;

import java.util.ArrayList;
import java.util.List;

public class Rota {

    private String partida;
    private String destino;
    private int hora;
    private int valor;
    private List<Assento> listaAssentos = new ArrayList<Assento>();

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public List<Assento> getListaAssentos() {
        return listaAssentos;
    }

    public void setListaAssentos(List<Assento> listaAssentos) {
        this.listaAssentos = listaAssentos;
    }

    @Override
    public String toString(){
        String formatar = "| %-15s | %-15s | %-10s | %-10s |%n";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+-----------------+-----------------+------------+------------+%n");
        stringBuilder.append("| Partida         | Destino         | Horário    | Valor      |%n");
        stringBuilder.append("+-----------------+-----------------+------------+------------+%n");
        stringBuilder.append(String.format(formatar, partida, destino, hora + "h", "R$" + valor));
        stringBuilder.append("+-----------------+-----------------+------------+------------+%n");

        return stringBuilder.toString();
    }
}
