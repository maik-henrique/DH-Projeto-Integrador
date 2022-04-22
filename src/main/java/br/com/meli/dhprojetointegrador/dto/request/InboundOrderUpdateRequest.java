package br.com.meli.dhprojetointegrador.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class InboundOrderUpdateRequest {

    private Integer orderNumber;
    private LocalDate orderDate;
    private AgentUpdateRequest agent;
    private SectionUpdateRequest section;
    private List<BatchStockUpdateRequest> batchStockList;
}
