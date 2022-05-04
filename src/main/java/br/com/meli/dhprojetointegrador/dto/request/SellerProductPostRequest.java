package br.com.meli.dhprojetointegrador.dto.request;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class SellerProductPostRequest {

    @NotNull(message = "name must not be null")
    private String name;

    @NotNull(message = "price must not be null")
    @DecimalMin(value = "0.0", message = "price must be a postive value")
    private BigDecimal price;

    @NotNull(message = "volume must not be null")
    @Positive(message = "volume must be positive and different than 0")
    private float volume;

}
