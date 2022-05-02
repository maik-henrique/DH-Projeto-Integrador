package br.com.meli.dhprojetointegrador.dto.response.freshproducts;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreshProductsQueriedResponse {
    private String warehouseCode;
    private String productId;
    private List<BatchStockResponse> batchStock;
}
