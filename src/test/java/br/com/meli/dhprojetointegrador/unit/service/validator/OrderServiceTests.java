package br.com.meli.dhprojetointegrador.unit.service.validator;

import br.com.meli.dhprojetointegrador.dto.request.ProductRefactor;
import br.com.meli.dhprojetointegrador.dto.request.PurchaseOrderRequest;
import br.com.meli.dhprojetointegrador.dto.response.OrderIntermediateResponse;
import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.repository.*;
import br.com.meli.dhprojetointegrador.service.OrderService;
import br.com.meli.dhprojetointegrador.service.validator.ProductValidator;
import br.com.meli.dhprojetointegrador.service.validator.BuyerValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import static org.mockito.Mockito.*;

public class OrderServiceTests {
    CartProductRepository cartProductRepository = mock(CartProductRepository.class);
    PurchaseOrderRepository purchaseOrderRepository = mock(PurchaseOrderRepository.class);
    ProductRepository productRepository = mock(ProductRepository.class);
    BatchStockRepository batchStockRepository = mock(BatchStockRepository.class);
    BuyerValidator buyerValidator = mock(BuyerValidator.class);
    ProductValidator productValidator = mock(ProductValidator.class);
    OrderRepository orderRepository = mock(OrderRepository.class);

    private final OrderService orderService = new OrderService(buyerValidator, productValidator, cartProductRepository, purchaseOrderRepository, productRepository, batchStockRepository, orderRepository);

    LocalDate date = LocalDate.of(2021, 04, 25);


    Buyer buyer = Buyer.builder()
            .id(1L)
            .name("Bruno")
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
            .batchStockList(Set.of(batch1))
            .name("Banana")
            .price(new BigDecimal("2.50"))
            .build();

    Product product2 = Product.builder()
            .id(2L)
            .batchStockList(Set.of(batch2))
            .name("Cenoura")
            .price(new BigDecimal("4.50"))
            .build();

    PurchaseOrder order1 = PurchaseOrder.builder()
            .id(1L)
            .date(date)
            .buyer(buyer)
            .status(StatusEnum.FINALIZADO)
            .build();

    PurchaseOrder order0 = PurchaseOrder.builder()
            .buyer(buyer)
            .date(date)
            .status(StatusEnum.FINALIZADO)
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

    ProductRefactor product1Input = ProductRefactor.builder()
            .productId(1L)
            .quantity(5)
            .build();

    ProductRefactor product2Input = ProductRefactor.builder()
            .productId(2L)
            .quantity(5)
            .build();

    PurchaseOrderRequest orderInput = PurchaseOrderRequest.builder()
            .date(date)
            .buyerId(1L)
            .products(Arrays.asList(product1Input, product2Input))
            .build();

    OrderIntermediateResponse response = OrderIntermediateResponse.builder()
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
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));
        when(cartProductRepository.save(cartProduct1)).thenReturn(cartProduct1);
        when(cartProductRepository.save(cartProduct2)).thenReturn(cartProduct2);
        when(batchStockRepository.findById(1L)).thenReturn(Optional.of(batch1));
        when(batchStockRepository.findById(2L)).thenReturn(Optional.of(batch2));
        when(batchStockRepository.save(batch1Updated)).thenReturn(batch1Updated);
        when(batchStockRepository.save(batch2Updated)).thenReturn(batch2Updated);
        when(purchaseOrderRepository.save(Mockito.any(PurchaseOrder.class))).thenReturn(order1);
        when(buyerValidator.getBuyer(1L)).thenReturn(buyer);
        when(productValidator.validateQuantity(5, 1L)).thenReturn(product1);
        when(productValidator.validateQuantity(5, 2L)).thenReturn(product2);

        OrderIntermediateResponse result = orderService.createOrder(orderInput);

        assert result.getCreatedID().equals(1L);
        assert result.getTotalPrice().equals(35.00);
    }
}
