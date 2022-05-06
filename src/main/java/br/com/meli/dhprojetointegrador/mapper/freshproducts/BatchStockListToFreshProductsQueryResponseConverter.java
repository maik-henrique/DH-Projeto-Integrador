package br.com.meli.dhprojetointegrador.mapper.freshproducts;

import br.com.meli.dhprojetointegrador.dto.response.freshproducts.BatchStockCollectionResponse;
import br.com.meli.dhprojetointegrador.dto.response.freshproducts.BatchStockResponse;
import br.com.meli.dhprojetointegrador.dto.response.freshproducts.FreshProductsQueriedResponse;
import br.com.meli.dhprojetointegrador.dto.response.freshproducts.SectionResponse;
import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Product;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BatchStockListToFreshProductsQueryResponseConverter extends AbstractConverter<BatchStockCollectionResponse, FreshProductsQueriedResponse> {
    @Override
    protected FreshProductsQueriedResponse convert(BatchStockCollectionResponse source) {
        List<BatchStock> batchStockList = source.getBatchStock();
        BatchStock batchStock = batchStockList.stream().findAny().orElseThrow(() -> new RuntimeException("No Batchstock found"));
        Product product = batchStock.getProducts();
        String warehouseCode = String.valueOf(batchStock.getInboundOrder().getSection().getWarehouse().getId());

        List<BatchStockResponse> batchStockResponses = extractBatchStock(batchStockList);
        Set<SectionResponse> section = extractSection(batchStockList);

        return FreshProductsQueriedResponse.builder()
                .productId(String.valueOf(product.getId()))
                .batchStock(batchStockResponses)
                .warehouseCode(warehouseCode)
                .build();
    }

    private List<BatchStockResponse> extractBatchStock(List<BatchStock> source) {
        return source
                .stream().map(batchStock -> BatchStockResponse.builder()
                        .batchNumber(String.valueOf(batchStock.getBatchNumber()))
                        .currentQuantity(batchStock.getCurrentQuantity())
                        .dueDate(batchStock.getDueDate())
                        .sectionCode(String.valueOf(batchStock.getInboundOrder().getSection().getId()))
                        .build()
                ).collect(Collectors.toList());
    }

    private Set<SectionResponse> extractSection(List<BatchStock> source) {
        return source
                .stream().map(BatchStock::getInboundOrder)
                .map(InboundOrder::getSection)
                .map(section -> SectionResponse.builder()
                        .sectionCode(String.valueOf(section.getId()))
                        .warehouseCode(String.valueOf(section.getWarehouse().getId()))
                        .build()
                ).collect(Collectors.toSet());
    }


}
