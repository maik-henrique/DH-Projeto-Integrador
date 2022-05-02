package br.com.meli.dhprojetointegrador.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PurchaseOrderEvaluation implements Serializable {

    private static final long serialVersionUID = -5732848803941349298L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "fk_purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "fk_product_id")
    private Product product;
}
