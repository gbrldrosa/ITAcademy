package reserva.model;

import java.util.UUID;

public class Assento {

    private int numero;
    private boolean disponivel;
    private String idTicket;

    public Assento(int numero, boolean disponivel) {
        this.numero = numero;
        this.disponivel = disponivel;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(String idTicket) {
        this.idTicket = idTicket;
    }

    public void reservar() {
        this.disponivel = false;
        this.idTicket = UUID.randomUUID().toString();
    }

    public void cancelarReserva() {
        this.disponivel = true;
    }

    public String getDescricaoDisponibilidade() {
        if (disponivel == true) {
            return "Disponivel";
        } else {
            return "Indisponivel";
        }
    }

    @Override
    public String toString(){
        return this.numero + "-" + getDescricaoDisponibilidade();
    }


}
