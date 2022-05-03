package br.com.meli.dhprojetointegrador.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewProductRequest {

    @NotNull
    private String name;

    @NotNull
    private Long sellerId;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Long categoryId;

    @NotNull
    private Float volume;

}
