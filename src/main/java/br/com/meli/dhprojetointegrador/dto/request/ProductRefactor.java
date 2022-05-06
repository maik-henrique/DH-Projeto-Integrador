package br.com.meli.dhprojetointegrador.dto.request;


import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRefactor {

    @NotNull
    @NumberFormat
    private Long productId;

    @NotNull
    @NumberFormat
    private Integer quantity;
}