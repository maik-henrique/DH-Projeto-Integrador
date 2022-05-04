package br.com.meli.dhprojetointegrador.enums;

public enum TokenType {
    BEARER("Bearer");

    private final String label;

    TokenType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;

    }
}
