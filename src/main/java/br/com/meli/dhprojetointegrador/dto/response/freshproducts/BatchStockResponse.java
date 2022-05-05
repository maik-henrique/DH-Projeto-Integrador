package br.com.meli.dhprojetointegrador.dto.response.freshproducts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockResponse {
    private String batchNumber;
    private Integer currentQuantity;
    private LocalDate dueDate;
    private String sectionCode;
}
