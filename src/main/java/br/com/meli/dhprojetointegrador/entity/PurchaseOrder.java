package br.com.meli.dhprojetointegrador.entity;

import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "purchaseOrder")
public class PurchaseOrder implements Serializable {

    private static final long serialVersionUID = 7156526077883281623L;

    @ApiModelProperty(value = "Código da PurchaseOrder")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "Código do buyer")
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @NotNull(message = "O campo nome não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @NotNull(message = "O campo nome não pode ser nulo")
    @DateTimeFormat
    private LocalDate date;
}
