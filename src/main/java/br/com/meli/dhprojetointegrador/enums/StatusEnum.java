package br.com.meli.dhprojetointegrador.enums;


public enum StatusEnum {

        ABERTO ("Aberto"),
        FINALIZADO ("finalizado");

    private String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
