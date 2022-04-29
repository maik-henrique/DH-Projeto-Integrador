package br.com.meli.dhprojetointegrador.mapper;

import br.com.meli.dhprojetointegrador.dto.BatchStockDTO;
import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.BatchStock.BatchStockBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-28T23:48:28-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.14 (Azul Systems, Inc.)"
)
@Component
public class BatchStockMapperImpl extends BatchStockMapper {

    @Override
    public List<BatchStock> toBatchStock(List<BatchStockDTO> batchStockDTO) {
        if ( batchStockDTO == null ) {
            return null;
        }

        List<BatchStock> list = new ArrayList<BatchStock>( batchStockDTO.size() );
        for ( BatchStockDTO batchStockDTO1 : batchStockDTO ) {
            list.add( batchStockDTOToBatchStock( batchStockDTO1 ) );
        }

        return list;
    }

    protected BatchStock batchStockDTOToBatchStock(BatchStockDTO batchStockDTO) {
        if ( batchStockDTO == null ) {
            return null;
        }

        BatchStockBuilder batchStock = BatchStock.builder();

        batchStock.batchNumber( batchStockDTO.getBatchNumber() );
        batchStock.currentQuantity( batchStockDTO.getCurrentQuantity() );
        batchStock.dueDate( batchStockDTO.getDueDate() );
        batchStock.initialQuantity( batchStockDTO.getInitialQuantity() );
        batchStock.manufacturingDate( batchStockDTO.getManufacturingDate() );
        batchStock.manufacturingTime( batchStockDTO.getManufacturingTime() );
        batchStock.currentTemperature( batchStockDTO.getCurrentTemperature() );
        batchStock.minimumTemperature( batchStockDTO.getMinimumTemperature() );
        batchStock.products( batchStockDTO.getProducts() );
        batchStock.inboundOrder( batchStockDTO.getInboundOrder() );

        return batchStock.build();
    }
}
