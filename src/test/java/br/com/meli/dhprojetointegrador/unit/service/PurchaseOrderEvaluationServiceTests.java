package br.com.meli.dhprojetointegrador.unit.service;


import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.PurchaseOrderEvaluationRepository;
import br.com.meli.dhprojetointegrador.service.OrderService;
import br.com.meli.dhprojetointegrador.service.PurchaseOrderEvaluationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderEvaluationServiceTests {
    @InjectMocks
    private PurchaseOrderEvaluationService purchaseOrderEvaluationService;

    @Mock
    private PurchaseOrderEvaluationRepository purchaseOrderEvaluationRepository;

    @Mock
    private OrderService orderService;


    @Test
    public void save_shouldCallSaveOnRepository_whenAllValidationsPasses() {
        Product product = Product.builder()
                .id(1L)
                .build();
        CartProduct cartProduct = CartProduct.builder().product(product).build();

        PurchaseOrder purchaseOrderActual = PurchaseOrder.builder()
                .id(1L)
                .status(StatusEnum.FINALIZADO)
                .buyer(Buyer.builder().id(1L).build())
                .cartProduct(Set.of(cartProduct))
                .build();

        PurchaseOrder purchaseOrderArgument = PurchaseOrder.builder()
                .id(1L)
                .buyer(Buyer.builder().id(1L).build()).build();

        PurchaseOrderEvaluation purchaseOrderEvaluation = PurchaseOrderEvaluation.builder()
                .product(product)
                .comment("Comentario")
                .rating(10)
                .purchaseOrder(purchaseOrderArgument)
                .build();

        when(orderService.findPurchaseOrderById(anyLong())).thenReturn(purchaseOrderActual);
        when(purchaseOrderEvaluationRepository.existsByProductIdAndPurchaseOrderId(anyLong(), anyLong())).thenReturn(false);

        purchaseOrderEvaluationService.save(purchaseOrderEvaluation);

        verify(purchaseOrderEvaluationRepository, times(1))
                .save(purchaseOrderEvaluation);
    }

    @Test
    public void save_shouldThrowBusinessValidatorException_whenPurchaseWasAlreadyEvaluated() {
        Product product = Product.builder()
                .id(1L)
                .build();

        PurchaseOrder purchaseOrderArgument = PurchaseOrder.builder()
                .id(1L)
                .buyer(Buyer.builder().id(1L).build()).build();

        CartProduct cartProduct = CartProduct.builder().product(product).build();

        PurchaseOrder purchaseOrderActual = PurchaseOrder.builder()
                .id(1L)
                .status(StatusEnum.FINALIZADO)
                .buyer(Buyer.builder().id(1L).build())
                .cartProduct(Set.of(cartProduct))
                .build();

        PurchaseOrderEvaluation purchaseOrderEvaluation = PurchaseOrderEvaluation.builder()
                .product(product)
                .comment("Comentario")
                .rating(10)
                .purchaseOrder(purchaseOrderArgument)
                .build();

        when(orderService.findPurchaseOrderById(anyLong())).thenReturn(purchaseOrderActual);
        when(purchaseOrderEvaluationRepository.existsByProductIdAndPurchaseOrderId(anyLong(), anyLong())).thenReturn(true);

        assertThrows(BusinessValidatorException.class, () -> purchaseOrderEvaluationService.save(purchaseOrderEvaluation));
    }
}
