package br.com.meli.dhprojetointegrador.dto.response;

import lombok.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class InboundOrderResponse {
    private List<BatchStockResponse> batchStock;
}
