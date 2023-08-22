package reserva.model;

public enum Cidade {

    PORTO_ALEGRE("Porto Alegre"),
    CRICIUMA("Criciuma"),
    FLORIANOPOLIS("Florianopolis");

    private String desc;
    private Cidade(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
