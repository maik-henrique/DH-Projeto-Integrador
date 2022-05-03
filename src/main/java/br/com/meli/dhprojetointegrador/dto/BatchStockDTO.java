package br.com.meli.dhprojetointegrador.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockDTO {

    @NumberFormat
    private Long batchNumber;

    @NotNull(message = "O código product_id não pode ser nulo")
    private Long product_id;

    @NotNull(message = "O campo minimumTemperature não pode ser nulo")
    private float minimumTemperature;

    @NotNull(message = "O campo currentTemperature não pode ser nulo")
    private float currentTemperature;

    @NotNull(message = "O campo initialQuantity não pode ser nulo")
    @NumberFormat
    private int initialQuantity;

    @NotNull(message = "O campo currentQuantity não pode ser nulo")
    @NumberFormat
    private int currentQuantity;

    @NotNull(message = "O campo manufacturingDate não pode ser nulo")
    private LocalDate manufacturingDate;

    @NotNull(message = "O campo manufacturingTime não pode ser nulo")
    private LocalDateTime manufacturingTime;

    @NotNull(message = "O campo dueDate não pode ser nulo")
    private LocalDate dueDate;

    @NotNull(message = "O campo inboundOrder não pode ser nulo")
    private InboundOrder inboundOrder;

    @NotNull(message = "O campo products não pode ser nulo")
    private Product products;

}
