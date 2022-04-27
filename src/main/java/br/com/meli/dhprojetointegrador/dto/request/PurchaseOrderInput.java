package br.com.meli.dhprojetointegrador.dto.request;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
