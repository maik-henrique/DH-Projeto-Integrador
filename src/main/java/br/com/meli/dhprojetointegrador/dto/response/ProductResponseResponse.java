package br.com.meli.dhprojetointegrador.dto.response;

import br.com.meli.dhprojetointegrador.entity.Product;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseResponse {

    private String name;
    private BigDecimal price;
    private float volume;


    public static ProductResponseResponse map(Product product) {
        return ProductResponseResponse.builder().name(product.getName()).price(product.getPrice())
                .volume(product.getVolume()).build();
    }

    public static List<ProductResponseResponse> map(List<Product> products) {
        return products.stream().map(e -> map(e)).collect(Collectors.toList());
    }
}