package br.com.meli.dhprojetointegrador.dto.request;


import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInput {

    @NotBlank
    @NotNull
    @NumberFormat
    private Long productId;

    @NotBlank
    @NotNull
    @NumberFormat
    private Integer quantity;
}
