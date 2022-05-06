package br.com.meli.dhprojetointegrador.enums;

public enum TokenTypeEnum {
    BEARER("Bearer");

    private final String label;

    TokenTypeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;

    }
}
