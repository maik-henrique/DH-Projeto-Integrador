package br.com.meli.dhprojetointegrador.unit.util;

import br.com.meli.dhprojetointegrador.dto.request.BatchStockPostRequest;
import br.com.meli.dhprojetointegrador.entity.BatchStock;

public class BatchStockCreator {

  public static BatchStockPostRequest batchStockPostRequest() {
    return BatchStockPostRequest.builder()
        .batchNumber(10L)
        .productId(ProductCreator.createValidProduct().getId())
        .minimumTemperature((float) 5.0)
        .currentQuantity(10)
        .initialQuantity(10)
        .currentTemperature((float) 12)
        .build();
  }

  public static BatchStock batchStock() {
    return BatchStock.builder()
        .batchNumber(10L)
        .minimumTemperature((float) 5.0)
        .currentQuantity(10)
        .initialQuantity(10)
        .currentTemperature((float) 12)
        .products(ProductCreator.createValidProduct())
        .build();
  }

}
