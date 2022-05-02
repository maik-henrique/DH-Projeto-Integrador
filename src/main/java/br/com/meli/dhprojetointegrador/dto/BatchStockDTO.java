package br.com.meli.dhprojetointegrador.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.Category;
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

    private Long batchNumber;
    private Long product_id;
    private int currentQuantity;
    private LocalDate dueDate;
    private Long category_id;

    /**
    * Author: Pedro Dalpa
    * Author: Mariana Galdino
    * Method: DTO Constructo
    * Description: Retorno da entidade DTO
    **/
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
