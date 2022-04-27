package br.com.meli.dhprojetointegrador.unit.util;

import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;

public class SectionCreator {

  public static Section createValidSection() {
    return Section.builder()
        .name("Section")
        .warehouse(Warehouse.builder().name("Warehouse 1").id(1L).build())
        .capacity(10)
        .id(1L)
        .category(Category.builder().name(CategoryEnum.CONGELADOS).build())
        .build();
  }

}
