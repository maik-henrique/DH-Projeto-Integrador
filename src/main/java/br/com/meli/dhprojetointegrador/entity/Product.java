package br.com.meli.dhprojetointegrador.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    @ApiModelProperty(value = "Código do product")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo nome não pode ser nulo")
    private String name;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 3, fraction = 2)
    private BigDecimal price;

    @NumberFormat
    private float volume;

    @ApiModelProperty(value = "Código da category")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ApiModelProperty(value = "Código do seller")
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToMany(mappedBy = "products")
    private Set<BatchStock> batchStockList;

}
