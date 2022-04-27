package br.com.meli.dhprojetointegrador.dto.response;

import br.com.meli.dhprojetointegrador.entity.CartProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDTO {
    private Long productId;
    private Integer quantity;

    public CartProductDTO(CartProduct cartProduct) {
        this.productId = getProductId();
        this.quantity = getQuantity();
    }

    public static List<CartProductDTO> convert(List<CartProduct> cartProducts) {
        return cartProducts.stream().map(CartProductDTO::new).collect(Collectors.toList());
    }
}
