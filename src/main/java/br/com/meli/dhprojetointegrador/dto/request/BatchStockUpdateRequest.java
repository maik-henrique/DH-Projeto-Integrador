package br.com.meli.dhprojetointegrador.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class BatchStockUpdateRequest {
    private Long batchNumber;
    private int currentQuantity;
    private LocalDate dueDate;
    private int initialQuantity;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private float currentTemperature;
}
