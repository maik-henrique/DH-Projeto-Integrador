package br.com.meli.dhprojetointegrador.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import br.com.meli.dhprojetointegrador.dto.BatchStockDTO;
import br.com.meli.dhprojetointegrador.entity.BatchStock;

@Mapper(componentModel = "spring")
public abstract class BatchStockMapper {
    public static final BatchStockMapper INSTANCE = Mappers.getMapper(BatchStockMapper.class);

    public abstract List<BatchStock> toBatchStock(List<BatchStockDTO> batchStockDTO);

}
