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
    private Long category_id;

    @NotNull(message = "O campo inboundOrder não pode ser nulo")
    private InboundOrder inboundOrder;

    @NotNull(message = "O campo products não pode ser nulo")
    private Product products;

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
