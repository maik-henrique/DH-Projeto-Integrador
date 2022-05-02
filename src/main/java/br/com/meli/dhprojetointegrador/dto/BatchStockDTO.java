package br.com.meli.dhprojetointegrador.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockDTO {

    private Long batchNumber;
    private Long product_id;
    private int currentQuantity;
    private LocalDate dueDate;
    private Long category_id;

    public static BatchStockDTO map(BatchStock batchStock) {
        return BatchStockDTO.builder().batchNumber(batchStock.getBatchNumber()).dueDate(batchStock.getDueDate())
                .product_id(batchStock.getProducts().getId()).currentQuantity(batchStock.getCurrentQuantity())
                .category_id(batchStock.getProducts().getCategory().getId())
                .build();
    }

    public static List<BatchStockDTO> map(List<BatchStock> batchStocks) {
        return batchStocks.stream().map(e -> map(e)).collect(Collectors.toList());
    }

}
