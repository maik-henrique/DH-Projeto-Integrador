package br.com.meli.dhprojetointegrador.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class CartProduct {

    @ManyToOne
    @JoinColumn(name = "purchaseOrder_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
}
