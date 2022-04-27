package br.com.meli.dhprojetointegrador.dto.response;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class InboundOrderResponse {
    private List<BatchStockResponse> batchStock;
}
