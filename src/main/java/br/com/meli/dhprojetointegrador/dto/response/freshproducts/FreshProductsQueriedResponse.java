package br.com.meli.dhprojetointegrador.dto.response.freshproducts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreshProductsQueriedResponse {
    private String warehouseCode;
    private String productId;
    private List<BatchStockResponse> batchStock;
}
