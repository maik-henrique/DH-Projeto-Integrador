package br.com.meli.dhprojetointegrador.dto.request;

import javax.validation.constraints.NotNull;

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
public class ProductInput {

    @NotNull
    @NumberFormat
    private Long productId;

    @NotNull
    @NumberFormat
    private Integer quantity;
}