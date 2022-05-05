package br.com.meli.dhprojetointegrador.unit.service.validator;

import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.repository.*;
import br.com.meli.dhprojetointegrador.service.OrderService;
import br.com.meli.dhprojetointegrador.service.validator.ProductValidator;
import br.com.meli.dhprojetointegrador.service.validator.BuyerValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class PurchaseOrderServiceTests {


    @InjectMocks
    private OrderService service;

    @Mock
    private BuyerValidator buyerValidator;

    @Mock
    private ProductValidator productValidator;

    @Mock
    private CartProductRepository cartProductRepository;

    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BatchStockRepository batchStockRepository;

    @Mock
    private OrderRepository orderRepository;

    /**
     * @Author: David
     * @Methodo: Mudar Cart para Aberto ou Finalizado na Order
     * @Description: Modifique o pedido existente. torná-lo do tipo de carrinho para modificar - ABERTO/FINALIZADO
     */
    @Test
    void naoDeveAlterarStatusQuandoReceberVazio() {

        PurchaseOrder purchaseOrderAberto = PurchaseOrder.builder().status(StatusEnum.ABERTO).build();
        PurchaseOrder purchaseOrderFechado = PurchaseOrder.builder().status(StatusEnum.FINALIZADO).build();

        Mockito.when(orderRepository.getById(2L)).thenReturn(purchaseOrderAberto);
        Mockito.when(orderRepository.save(Mockito.any(PurchaseOrder.class))).thenReturn(purchaseOrderFechado);

        PurchaseOrder result = this.service.atualizar(2L);

        assert result.equals(purchaseOrderFechado);

    }

}
