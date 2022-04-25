package br.com.meli.dhprojetointegrador.config;

import br.com.meli.dhprojetointegrador.dto.request.InboundOrderUpdateRequest;
import br.com.meli.dhprojetointegrador.entity.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<InboundOrderUpdateRequest, InboundOrder> converter = context -> {
            InboundOrderUpdateRequest source = context.getSource();

            Agent agent = Agent.builder().id(source.getAgentId()).build();
            Section section = Section.builder().id(source.getSectionId()).build();
            List<BatchStock> batchStock = source.getBatchStock().stream().map(
                    batchStockUpdateRequest -> BatchStock.builder()
                            .batchNumber(batchStockUpdateRequest.getBatchNumber())
                            .currentQuantity(batchStockUpdateRequest.getCurrentQuantity())
                            .currentTemperature(batchStockUpdateRequest.getCurrentTemperature())
                            .dueDate(batchStockUpdateRequest.getDueDate())
                            .initialQuantity(batchStockUpdateRequest.getInitialQuantity())
                            .manufacturingDate(batchStockUpdateRequest.getManufacturingDate())
                            .manufacturingTime(batchStockUpdateRequest.getManufacturingTime())
                            .products(Product.builder().id(batchStockUpdateRequest.getProductId()).build())
                            .build()
            ).collect(Collectors.toList());

            return InboundOrder
                    .builder()
                    .agent(agent)
                    .orderNumber(source.getOrderNumber())
                    .orderDate(source.getOrderDate())
                    .section(section)
                    .batchStockList(batchStock)
                    .build();
        };

        modelMapper.createTypeMap(InboundOrderUpdateRequest.class, InboundOrder.class)
                .setConverter(converter);



        return modelMapper;
    }
}
