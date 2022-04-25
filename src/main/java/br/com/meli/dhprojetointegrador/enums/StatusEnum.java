package br.com.meli.dhprojetointegrador.enums;


public enum StatusEnum {

        ABERTO ("cart"),
        FINALIZADO ("order");

    private String status;

    StatusEnum(String status) {
        this.status = status;
    }
}
