package br.com.meli.dhprojetointegrador.enums;

public enum CategoryEnum {
  FRIOS("FRIOS"),
  CONGELADOS("CONGELADOS"),
  FF("Congelado"),
  RF("Refrigerado"),
  FS("Fresco");

  private final String value;

  CategoryEnum(String valor) {
    this.value = valor;
  }

  public String getValue() {
    return value;

  }
}
