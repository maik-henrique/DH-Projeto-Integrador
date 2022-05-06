package br.com.meli.dhprojetointegrador.dto.request;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

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
public class PurchaseOrderRequest {

    @NotNull
    @DateTimeFormat
    private LocalDate date;

    @NotNull
    @NumberFormat
    private Long buyerId;

    private StatusEnum orderStatus;

    private List<@Valid ProductRefactor> products;

}