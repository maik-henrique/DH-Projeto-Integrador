package br.com.meli.dhprojetointegrador.util;

import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.entity.Warehouse;

public class SectionCreator {

  public static Section createValidSection() {
    return Section.builder()
        .name("Section")
        .warehouse(Warehouse.builder().name("Warehouse 1").id(1L).build())
        .capacity(10)
        .id(1L)
        .build();
  }

}
