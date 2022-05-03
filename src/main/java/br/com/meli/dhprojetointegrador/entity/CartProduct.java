package br.com.meli.dhprojetointegrador.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartProduct {

    @ApiModelProperty(value = "Código do cart product")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "PurchaseOrder indentification")
    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @ApiModelProperty(value = "Nome do produto")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull(message = "O campo quantity não pode ser nulo")
    @NumberFormat
    private Integer quantity;
}
