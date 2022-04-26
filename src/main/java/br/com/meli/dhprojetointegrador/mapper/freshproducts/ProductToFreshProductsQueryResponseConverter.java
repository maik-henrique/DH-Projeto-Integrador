package br.com.meli.dhprojetointegrador.mapper.freshproducts;

import br.com.meli.dhprojetointegrador.dto.response.freshproducts.BatchStockResponse;
import br.com.meli.dhprojetointegrador.dto.response.freshproducts.FreshProductsQueriedResponse;
import br.com.meli.dhprojetointegrador.dto.response.freshproducts.SectionResponse;
import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Product;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductToFreshProductsQueryResponseConverter extends AbstractConverter<Product, FreshProductsQueriedResponse> {
    @Override
    protected FreshProductsQueriedResponse convert(Product source) {
        Set<SectionResponse> sections = extractSection(source);
        Set<BatchStockResponse> batchStockSet = extractBatchStock(source);

        return FreshProductsQueriedResponse.builder()
                .productId(String.valueOf(source.getId()))
                .sections(sections)
                .batchStock(batchStockSet)
                .build();
    }

    private Set<BatchStockResponse> extractBatchStock(Product source) {
        return source.getBatchStockList()
                .stream().map(batchStock -> BatchStockResponse.builder()
                        .batchNumber(String.valueOf(batchStock.getBatchNumber()))
                        .currentQuantity(batchStock.getCurrentQuantity())
                        .dueDate(batchStock.getDueDate())
                        .build()
                ).collect(Collectors.toSet());
    }

    private Set<SectionResponse> extractSection(Product source) {
        return source.getBatchStockList()
                .stream().map(BatchStock::getInboundOrder)
                .map(InboundOrder::getSection)
                .map(section -> SectionResponse.builder()
                        .sectionCode(String.valueOf(section.getId()))
                        .warehouseCode(String.valueOf(section.getWarehouse().getId()))
                        .build()
                ).collect(Collectors.toSet());
    }
}
