package br.com.meli.dhprojetointegrador.dto.request;

import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderInput {

    @NotNull(message = "O campo nome não pode ser nulo")
    @NotBlank(message = "O campo nome não pode estar em branco")
    @DateTimeFormat
    private LocalDate date;

    @NotBlank(message = "O campo nome não pode estar em branco")
    @NotNull(message = "O campo nome não pode ser nulo")
    @NumberFormat
    private Long buyerId;

    @NotNull(message = "O campo nome não pode ser nulo")
    @NotBlank(message = "O campo nome não pode estar em branco")
    private StatusEnum orderStatus;

    private List<@Valid ProductInput> products;

}
