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
public class CartProductResponse {
    private Integer quantity;
    private String product;

    public CartProductResponse(CartProduct cartProduct) {
        this.product = cartProduct.getProduct().getName();
        this.quantity = cartProduct.getQuantity();
    }

    public static List<CartProductResponse> convertToProductList(List<CartProduct> cartProducts) {
        return cartProducts.stream().map(CartProductResponse::new).collect(Collectors.toList());
    }
}
