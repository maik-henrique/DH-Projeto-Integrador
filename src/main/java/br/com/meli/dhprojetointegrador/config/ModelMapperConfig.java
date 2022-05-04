package br.com.meli.dhprojetointegrador.config;


import br.com.meli.dhprojetointegrador.dto.request.InboundOrderUpdateRequest;
import br.com.meli.dhprojetointegrador.dto.response.BatchStockResponse;
import br.com.meli.dhprojetointegrador.dto.response.InboundOrderResponse;
import br.com.meli.dhprojetointegrador.mapper.evaluation.PurchaseOrderEvaluationRegistrationRequestToPurchaseOrderEvaluationConverter;
import br.com.meli.dhprojetointegrador.mapper.evaluation.PurchaseOrderEvaluationToPurchaseOrderEvaluationResponseConverter;
import br.com.meli.dhprojetointegrador.mapper.freshproducts.BatchStockListToFreshProductsQueryResponseConverter;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import br.com.meli.dhprojetointegrador.dto.request.InboundOrderPostRequest;
import br.com.meli.dhprojetointegrador.mapper.freshproducts.BatchStockListToFreshProductsQueryResponseConverter;
import lombok.AllArgsConstructor;
import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Section;



@AllArgsConstructor
@Configuration
public class ModelMapperConfig {

    private final BatchStockListToFreshProductsQueryResponseConverter batchStockListToFreshProductsQueryResponseConverter;
    private final PurchaseOrderEvaluationRegistrationRequestToPurchaseOrderEvaluationConverter purchaseOrderEvaluationRegistrationRequestToPurchaseOrderEvaluationConverter;
    private final PurchaseOrderEvaluationToPurchaseOrderEvaluationResponseConverter purchaseOrderEvaluationToPurchaseOrderEvaluationResponseConverter;

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<InboundOrderUpdateRequest, InboundOrder> converter = getInboundOrderUpdateRequestToInboundOrderConverter();
        Converter<InboundOrder, InboundOrderResponse> inboundOrderToInboundOrderResponseConverter =
                gedtInboundOrderUpdateRequestToInboundOrderConverter();
        Converter<InboundOrderPostRequest, InboundOrder> inboundOrderPostToInboundOrder = getInboundOrderPostRequestToInboundOrderConverter();

        modelMapper.createTypeMap(InboundOrderUpdateRequest.class, InboundOrder.class)
                .setConverter(converter);
        modelMapper.createTypeMap(InboundOrder.class, InboundOrderResponse.class)
                .setConverter(inboundOrderToInboundOrderResponseConverter);
        modelMapper.createTypeMap(InboundOrderPostRequest.class, InboundOrder.class)
                .setConverter(inboundOrderPostToInboundOrder);

        modelMapper.addConverter(batchStockListToFreshProductsQueryResponseConverter);
        modelMapper.addConverter(purchaseOrderEvaluationRegistrationRequestToPurchaseOrderEvaluationConverter);
        modelMapper.addConverter(purchaseOrderEvaluationToPurchaseOrderEvaluationResponseConverter);

        return modelMapper;
    }

    private Converter<InboundOrderUpdateRequest, InboundOrder> getInboundOrderUpdateRequestToInboundOrderConverter() {
        return context -> {
            InboundOrderUpdateRequest source = context.getSource();

            Agent agent = Agent.builder().id(source.getAgentId()).build();
            Section section = Section.builder().id(source.getSectionId()).build();
            Set<BatchStock> batchStock = source.getBatchStock().stream().map(
                    batchStockUpdateRequest -> BatchStock.builder()
                            .batchNumber(batchStockUpdateRequest.getBatchNumber())
                            .minimumTemperature(
                                    batchStockUpdateRequest.getMinimumTemperature())
                            .currentQuantity(batchStockUpdateRequest.getCurrentQuantity())
                            .currentTemperature(
                                    batchStockUpdateRequest.getCurrentTemperature())
                            .dueDate(batchStockUpdateRequest.getDueDate())
                            .initialQuantity(batchStockUpdateRequest.getInitialQuantity())
                            .manufacturingDate(
                                    batchStockUpdateRequest.getManufacturingDate())
                            .manufacturingTime(
                                    batchStockUpdateRequest.getManufacturingTime())
                            .products(Product.builder()
                                    .id(batchStockUpdateRequest.getProductId())
                                    .build())
                            .build())
                    .collect(Collectors.toSet());

            return InboundOrder
                    .builder()
                    .agent(agent)
                    .orderNumber(source.getOrderNumber())
                    .orderDate(source.getOrderDate())
                    .section(section)
                    .batchStockList(batchStock)
                    .build();
        };
    }

    private Converter<InboundOrder, InboundOrderResponse> gedtInboundOrderUpdateRequestToInboundOrderConverter() {
        return context -> {
            InboundOrder source = context.getSource();

            List<BatchStockResponse> batchStock = source.getBatchStockList().stream().map(
                    batchStockSource -> BatchStockResponse.builder()
                            .batchNumber(batchStockSource.getBatchNumber())
                            .currentQuantity(batchStockSource.getCurrentQuantity())
                            .currentTemperature(batchStockSource.getCurrentTemperature())
                            .minimumTemperature(batchStockSource.getMinimumTemperature())
                            .dueDate(batchStockSource.getDueDate())
                            .initialQuantity(batchStockSource.getInitialQuantity())
                            .manufacturingDate(batchStockSource.getManufacturingDate())
                            .manufacturingTime(batchStockSource.getManufacturingTime())
                            .productId(batchStockSource.getProducts().getId())
                            .build())
                    .collect(Collectors.toList());

            return InboundOrderResponse
                    .builder()
                    .batchStock(batchStock)
                    .build();
        };
    }

    private Converter<InboundOrderPostRequest, InboundOrder> getInboundOrderPostRequestToInboundOrderConverter() {
        return context -> {
            InboundOrderPostRequest source = context.getSource();

            Agent agent = Agent.builder().id(source.getAgentId()).build();
            Section section = Section.builder().id(source.getSectionId()).build();
            Set<BatchStock> batchStock = source.getBatchStock().stream().map(
                    batchStockUpdateRequest -> BatchStock.builder()
                            .batchNumber(batchStockUpdateRequest.getBatchNumber())
                            .currentQuantity(batchStockUpdateRequest.getCurrentQuantity())
                            .currentTemperature(
                                    batchStockUpdateRequest.getCurrentTemperature())
                            .dueDate(batchStockUpdateRequest.getDueDate())
                            .initialQuantity(batchStockUpdateRequest.getInitialQuantity())
                            .manufacturingDate(
                                    batchStockUpdateRequest.getManufacturingDate())
                            .manufacturingTime(
                                    batchStockUpdateRequest.getManufacturingTime())
                            .minimumTemperature(
                                    batchStockUpdateRequest.getMinimumTemperature())
                            .products(Product.builder()
                                    .id(batchStockUpdateRequest.getProductId())
                                    .build())
                            .build())
                    .collect(Collectors.toSet());

            return InboundOrder
                    .builder()
                    .agent(agent)
                    .orderDate(source.getOrderDate())
                    .section(section)
                    .batchStockList(batchStock)
                    .build();
        };
    }

}
