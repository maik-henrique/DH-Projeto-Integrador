package br.com.meli.dhprojetointegrador.unit;

import br.com.meli.dhprojetointegrador.dto.request.ProductInput;
import br.com.meli.dhprojetointegrador.dto.request.PurchaseOrderInput;
import br.com.meli.dhprojetointegrador.dto.response.OrderIntermediateDTO;
import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.repository.*;
import br.com.meli.dhprojetointegrador.service.OrderService;
import br.com.meli.dhprojetointegrador.service.validator.ValidadeProduct;
import br.com.meli.dhprojetointegrador.service.validator.ValidateBuyer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class OrderServiceTest {
    CartProductRepository cartProductRepository = mock(CartProductRepository.class);
    PurchaseOrderRepository purchaseOrderRepository = mock(PurchaseOrderRepository.class);
    ProductRepository productRepository = mock(ProductRepository.class);
    BatchStockRepository batchStockRepository = mock(BatchStockRepository.class);
    ValidateBuyer validateBuyer = mock(ValidateBuyer.class);
    ValidadeProduct validadeProduct = mock(ValidadeProduct.class);

    private final OrderService orderService = new OrderService(validateBuyer, validadeProduct, cartProductRepository, purchaseOrderRepository, productRepository, batchStockRepository);

    LocalDate date = LocalDate.of(2021, 04, 25);

    Buyer buyer = Buyer.builder()
            .id(1L)
            .name("Bruno")
            .password("123456")
            .email("bruno@email.com")
            .build();

    BatchStock batch1 = BatchStock.builder()
            .batchNumber(1L)
            .currentQuantity(15)
            .build();

    BatchStock batch1Updated = BatchStock.builder()
            .batchNumber(1L)
            .currentQuantity(10)
            .build();

    BatchStock batch2 = BatchStock.builder()
            .batchNumber(2L)
            .currentQuantity(15)
            .build();

    BatchStock batch2Updated = BatchStock.builder()
            .batchNumber(1L)
            .currentQuantity(10)
            .build();

    Product product1 = Product.builder()
            .id(1L)
            .batchStockList(List.of(batch1))
            .name("Banana")
            .price(new BigDecimal("2.50"))
            .build();

    Product product2 = Product.builder()
            .id(2L)
            .batchStockList(List.of(batch2))
            .name("Cenoura")
            .price(new BigDecimal("4.50"))
            .build();

    PurchaseOrder order1 = PurchaseOrder.builder()
            .id(1L)
            .date(date)
            .buyer(buyer)
            .status(StatusEnum.FECHADO)
            .build();

    PurchaseOrder order0 = PurchaseOrder.builder()
            .buyer(buyer)
            .date(date)
            .status(StatusEnum.FECHADO)
            .build();

    CartProduct cartProduct1 = CartProduct.builder()
            .product(product1)
            .purchaseOrder(order1)
            .quantity(5)
            .build();

    CartProduct cartProduct2 = CartProduct.builder()
            .product(product2)
            .purchaseOrder(order1)
            .quantity(5)
            .build();

    ProductInput product1Input = ProductInput.builder()
            .productId(1L)
            .quantity(5)
            .build();

    ProductInput product2Input = ProductInput.builder()
            .productId(2L)
            .quantity(5)
            .build();

    PurchaseOrderInput orderInput = PurchaseOrderInput.builder()
            .date(date)
            .buyerId(1L)
            .products(Arrays.asList(product1Input, product2Input))
            .build();

    OrderIntermediateDTO response = OrderIntermediateDTO.builder()
            .totalPrice(35.00)
            .createdID(1L)
            .build();

    /**
     * @Author: Bruno
     * @Teste: Teste unitario função createOrder
     * @Description: valida funcionamento correto da função
     */
    @Test
    @DisplayName("TestPI-8 - createOrder")
   public void createOrder_should_return_correct_OrderIntermediateDTO() {
        when(productRepository.getById(1L)).thenReturn(product1);
        when(productRepository.getById(2L)).thenReturn(product2);
        when(cartProductRepository.save(cartProduct1)).thenReturn(cartProduct1);
        when(cartProductRepository.save(cartProduct2)).thenReturn(cartProduct2);
        when(batchStockRepository.getById(1L)).thenReturn(batch1);
        when(batchStockRepository.getById(2L)).thenReturn(batch2);
        when(batchStockRepository.save(batch1Updated)).thenReturn(batch1Updated);
        when(batchStockRepository.save(batch2Updated)).thenReturn(batch2Updated);
        when(purchaseOrderRepository.save(Mockito.any(PurchaseOrder.class))).thenReturn(order1);
        when(validateBuyer.getBuyer(1L)).thenReturn(buyer);
        when(validadeProduct.validateQuantity(5, 1L)).thenReturn(product1);
        when(validadeProduct.validateQuantity(5, 2L)).thenReturn(product2);

        OrderIntermediateDTO result = orderService.createOrder(orderInput);

        assert result.getCreatedID().equals(1L);
        assert result.getTotalPrice().equals(35.00);
    }
}
