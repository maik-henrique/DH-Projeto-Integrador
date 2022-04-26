package br.com.meli.dhprojetointegrador.dto.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    private List<@Valid ProductInput> products;

}
