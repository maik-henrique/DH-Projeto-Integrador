package br.com.meli.dhprojetointegrador.entity;

import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "purchaseOrder")
public class PurchaseOrder implements Serializable{

    private static final long serialVersionUID = 7156526077883281623L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo nome não pode ser nulo")
    @NotBlank(message = "O campo nome não pode estar em branco")
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @NotNull(message = "O campo nome não pode ser nulo")
    @NotBlank(message = "O campo nome não pode estar em branco")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @NotNull(message = "O campo nome não pode ser nulo")
    @NotBlank(message = "O campo nome não pode estar em branco")
    @DateTimeFormat
    private LocalDate date;
}
