package br.com.meli.dhprojetointegrador.enums;

public enum CategoryEnum {
  FF("Congelado"),
  RF("Refrigerado"),
  FS("Fresco");

  private String value;

  CategoryEnum(String valor) {
    this.value = valor;
  }

  public String getValue() {
    return value;

  }
}
