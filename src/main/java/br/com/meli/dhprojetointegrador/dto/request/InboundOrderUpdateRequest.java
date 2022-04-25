package br.com.meli.dhprojetointegrador.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class InboundOrderUpdateRequest {

    private Integer orderNumber;
    private LocalDate orderDate;
    private Integer sectionId;
    private Integer agentId;
    private List<BatchStockUpdateRequest> batchStock;
}
