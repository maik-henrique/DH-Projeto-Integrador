package br.com.meli.dhprojetointegrador.dto.response.freshproducts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreshProductsQueriedResponse {
    private Set<SectionResponse> sections;
    private String productId;
    private Set<BatchStockResponse> batchStock;
}
