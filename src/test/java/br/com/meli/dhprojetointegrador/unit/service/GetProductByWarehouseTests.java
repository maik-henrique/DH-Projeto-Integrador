package br.com.meli.dhprojetointegrador.unit.service;

import br.com.meli.dhprojetointegrador.dto.response.ProductByWarehouseResponse;
import br.com.meli.dhprojetointegrador.dto.response.WarehouseQuantityResponse;
import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.repository.*;
import br.com.meli.dhprojetointegrador.service.ProductService;
import br.com.meli.dhprojetointegrador.service.validator.ProductValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetProductByWarehouseTests {

    ProductRepository productRepository = mock(ProductRepository.class);
    ProductValidator productValidator = mock(ProductValidator.class);

    private final ProductService productService = new ProductService(productRepository, productValidator);

    Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("warehouse 1")
            .build();

    Section section = Section.builder()
            .warehouse(warehouse)
            .id(1L)
            .build();

    InboundOrder inboundOrder = InboundOrder.builder()
            .orderNumber(1L)
            .section(section)
            .build();

    BatchStock batch1 = BatchStock.builder()
            .inboundOrder(inboundOrder)
            .batchNumber(1L)
            .currentQuantity(15)
            .build();

    BatchStock batch2 = BatchStock.builder()
            .inboundOrder(inboundOrder)
            .batchNumber(1L)
            .currentQuantity(10)
            .build();

    Product product1 = Product.builder()
            .id(1L)
            .name("Banana")
            .price(new BigDecimal("2.50"))
            .batchStockList(Set.of(batch1, batch2))
            .build();

    WarehouseQuantityResponse whQuantity = WarehouseQuantityResponse.builder()
            .totalQuantity(15)
            .warehouseCode(1L)
            .build();

    ProductByWarehouseResponse expectedResponse = ProductByWarehouseResponse.builder()
            .productId(1L)
            .warehouses(List.of(whQuantity))
            .build();

    /**
     * @Author: Bruno
     * @Teste: Teste unitario função createOrder
     * @Description: valida funcionamento correto da função
     */
    @Test
    @DisplayName("TestPI-43 Get products by warehouse")
    public void getProductsByWarehouse_should_return_ProductWarehouseResponse() {
        when(productValidator.validateQuantity(1, 1L)).thenReturn(product1);

        ProductByWarehouseResponse result = productService.getProductByWarehouse(1L);

        assert result.getWarehouses().get(0).getTotalQuantity().equals(25);
        assert result.getWarehouses().get(0).getWarehouseCode().equals(1L);
        assert result.getProductId().equals(1L);
    }
}
