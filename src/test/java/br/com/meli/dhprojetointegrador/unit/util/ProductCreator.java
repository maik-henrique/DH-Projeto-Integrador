package br.com.meli.dhprojetointegrador.unit.util;

import java.math.BigDecimal;

import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;

public class ProductCreator {

  public static Product createValidProduct() {

    return Product.builder()
        .name("Frango")
        .price(new BigDecimal(25))
        .volume(2)
        .id(1L)
        .category(Category.builder().name(CategoryEnum.CONGELADOS).build())
        .brand("Seara")
        .build();
  }

}
