package br.com.meli.dhprojetointegrador.util;

import br.com.meli.dhprojetointegrador.entity.Warehouse;

public class WarehouseCreator {

  public static Warehouse createValidWarehouse() {
    return Warehouse.builder()
        .name("Warehouse")
        .id(1L)
        .build();
  }

}
