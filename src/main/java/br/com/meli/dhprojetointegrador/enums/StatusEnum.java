package br.com.meli.dhprojetointegrador.enums;


public enum StatusEnum {
    AB("Aberto"),
    FN("Finalizado");

    private final String value;

    StatusEnum(String valor) {
        this.value = valor;
    }

    public String getValue() {
        return value;

    }
}
