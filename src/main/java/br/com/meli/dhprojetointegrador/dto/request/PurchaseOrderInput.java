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

    @NotNull
    @NotBlank
    @DateTimeFormat
    private LocalDate date;

    @NotBlank
    @NotNull
    @NumberFormat
    private Long buyerId;

    @NotNull
    @NotBlank
    private StatusEnum orderStatus;

    private List<@Valid ProductInput> products;

}
