package br.com.meli.dhprojetointegrador.unit.util;

import br.com.meli.dhprojetointegrador.entity.CartProduct;

public class CardProductCreator {
    public static CartProduct createValidCardProduct() {

        return CartProduct.builder()
                .product(ProductCreator.createValidProduct())
                .purchaseOrder(PurchaseOrderCreator.createValidPurchaseOrder())
                .quantity(5)
                .build();
    }
}
