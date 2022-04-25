package br.com.meli.dhprojetointegrador.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockDTO {

    private Integer batchNumber;
    private Integer product_id;
    private float minimumTemperature;
    private float currentTemperature;
    private int initialQuantity;
    private int currentQuantity;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private LocalDate dueDate;

    private InboundOrder inboundOrder;
    private Product products;

}
