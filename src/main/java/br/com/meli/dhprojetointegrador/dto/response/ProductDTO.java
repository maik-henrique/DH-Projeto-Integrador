package br.com.meli.dhprojetointegrador.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

  private Long id;
  private String name;
  private BigDecimal price;
  private CategoryEnum categoryName;
  private Long categoryId;
  private String sellerName;
  private Long sellerId;
  private String brand;

  public static ProductDTO map(Product product) {
    return ProductDTO.builder()
        .id(product.getId())
        .name(product.getName())
        .price(product.getPrice())
        .categoryName(product.getCategory().getName())
        .categoryId(product.getCategory().getId())
        .sellerName(product.getSeller().getName())
        .sellerId(product.getSeller().getId())
        .brand(product.getBrand())
        .build();
  }

  public static List<ProductDTO> map(List<Product> products) {
    return products.stream().map(e -> map(e)).collect(Collectors.toList());
  }

}
