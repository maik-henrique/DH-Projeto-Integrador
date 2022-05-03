package br.com.meli.dhprojetointegrador.entity;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = {
        @Index(name = "PRODUCT_INDEX", columnList = "fk_product_id")
})
public class PurchaseOrderEvaluation implements Serializable {

    private static final long serialVersionUID = -5732848803941349298L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 400)
    private String comment;

    @NotNull
    @Range(min = 0, max = 10)
    private Integer rating;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fk_product_id")
    private Product product;
}
