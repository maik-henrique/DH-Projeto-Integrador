package br.com.meli.dhprojetointegrador.dto.response.freshproducts;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockCollectionResponse {
    private List<BatchStock> batchStock;
}
