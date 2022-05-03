package br.com.meli.dhprojetointegrador.unit.service;


import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.entity.view.PurchaseOrderEvaluationView;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.exception.ResourceNotFoundException;
import br.com.meli.dhprojetointegrador.repository.PurchaseOrderEvaluationRepository;
import br.com.meli.dhprojetointegrador.service.OrderService;
import br.com.meli.dhprojetointegrador.service.PurchaseOrderEvaluationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    public void findEvaluationByBuyerId_shouldThrowResourceNotFoundException_whenAnEmptyListIsReturnedFromRepository() {
        when(purchaseOrderEvaluationRepository.findByBuyerId(anyLong())).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> purchaseOrderEvaluationService.findEvaluationByBuyerId(1L));
    }

    @Test
    public void findEvaluationByBuyerId_shouldNotThrowResourceNotFoundException_whenEvaluationsAreFound() {
        PurchaseOrderEvaluation singleEvaluation = PurchaseOrderEvaluation.builder().build();
        List<PurchaseOrderEvaluation> expectedList = List.of(singleEvaluation);
        when(purchaseOrderEvaluationRepository.findByBuyerId(anyLong())).thenReturn(expectedList);

        List<PurchaseOrderEvaluation> evaluations = purchaseOrderEvaluationService.findEvaluationByBuyerId(1L);
        assertEquals(expectedList.size(), evaluations.size());
    }

    @Test
    public void update_shouldThrowResourceNotFoundException_whenEvaluationIsNotFound() {
        Buyer buyer = Buyer.builder().id(1L).build();
        PurchaseOrder purchaseOrder = PurchaseOrder.builder().buyer(buyer).build();
        PurchaseOrderEvaluation newPurchaseOrder = PurchaseOrderEvaluation.builder().id(1L)
                .purchaseOrder(purchaseOrder)
                .comment("Produto ruim de mais").build();

        when(purchaseOrderEvaluationRepository.findByIdAndBuyerId(anyLong(), anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> purchaseOrderEvaluationService.update(newPurchaseOrder));
    }

    @Test
    public void update_shouldCallSaveWithUpdatedEvaluation_whenEvaluationExists() {
        PurchaseOrderEvaluation oldEvaluation = PurchaseOrderEvaluation.builder().id(1L)
                .comment("Produto bom de mais").build();

        when(purchaseOrderEvaluationRepository.findByIdAndBuyerId(anyLong(), anyLong()))
                .thenReturn(Optional.of(oldEvaluation));

        Buyer buyer = Buyer.builder().id(1L).build();
        PurchaseOrder purchaseOrder = PurchaseOrder.builder().buyer(buyer).build();
        PurchaseOrderEvaluation newPurchaseOrder = PurchaseOrderEvaluation.builder().id(1L).purchaseOrder(purchaseOrder)
                .comment("Produto ruim de mais").build();
        purchaseOrderEvaluationService.update(newPurchaseOrder);

        verify(purchaseOrderEvaluationRepository, times(1)).save(any(PurchaseOrderEvaluation.class));
    }

    @Test
    public void findByProductId_shouldThrowResourceNotFoundException_whenNoEvaluationIsFound() {
        when(purchaseOrderEvaluationRepository.findByProductId(anyLong())).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> purchaseOrderEvaluationService.findByProductId(anyLong()));
    }

    @Test
    public void findByProductId_shouldReturnPurchaseOrderEvaluationView_whenEvaluationsAreFound() {
        PurchaseOrderEvaluation firstEvaluation = PurchaseOrderEvaluation.builder().rating(9).build();
        PurchaseOrderEvaluation secondEvaluation = PurchaseOrderEvaluation.builder().rating(10).build();
        List<PurchaseOrderEvaluation> evaluations = List.of(firstEvaluation, secondEvaluation);

        when(purchaseOrderEvaluationRepository.findByProductId(anyLong())).thenReturn(evaluations);

        PurchaseOrderEvaluationView evaluationsView = purchaseOrderEvaluationService.findByProductId(1L);

        assertEquals(9.5, evaluationsView.getAverageRate());
        assertEquals(2, evaluationsView.getEvaluations().size());
    }
}
