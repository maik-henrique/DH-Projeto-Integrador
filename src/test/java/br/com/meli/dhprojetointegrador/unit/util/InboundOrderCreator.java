package br.com.meli.dhprojetointegrador.unit.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.meli.dhprojetointegrador.dto.request.BatchStockPostRequest;
import br.com.meli.dhprojetointegrador.dto.request.InboundOrderPostRequest;
import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;

public class InboundOrderCreator {
  public static InboundOrderPostRequest createInboundOrderPostRequest() {
    List<BatchStockPostRequest> batchStockPostRequests = new ArrayList<>();
    batchStockPostRequests.add(BatchStockCreator.batchStockPostRequest());

    return InboundOrderPostRequest.builder()
        .agentId(WarehouseCreator.createValidWarehouse().getId())
        .sectionId(SectionCreator.createValidSection().getId())
        .batchStock(batchStockPostRequests)
        .build();
  }

  public static InboundOrder createInboundOrder() {
    Set<BatchStock> batchStocks = new HashSet<>();
    batchStocks.add(BatchStockCreator.batchStock());

    return InboundOrder.builder()
        .batchStockList(batchStocks)
        .orderNumber(1L)
        .orderDate(LocalDate.of(2022, 04, 25))
        .agent(AgentCreator.createValidAgent())
        .section(SectionCreator.createValidSection())
        .build();
  }

}
