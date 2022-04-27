package br.com.meli.dhprojetointegrador.unit;

import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.repository.*;
import br.com.meli.dhprojetointegrador.service.OrderService;
import br.com.meli.dhprojetointegrador.service.validator.ValidadeProduct;
import br.com.meli.dhprojetointegrador.service.validator.ValidateBuyer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.constraints.NotNull;

public class PurchaseOrderServiceTest {


    @InjectMocks
    private OrderService service;

    @Mock
    private ValidateBuyer validateBuyer;

    @Mock
    private ValidadeProduct validadeProduct;

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

        this.orderRepository = Mockito.mock(OrderRepository.class);
        this.productRepository = Mockito.mock(ProductRepository.class);

        PurchaseOrder purchaseOrderAberto = PurchaseOrder.builder().status(StatusEnum.ABERTO).build();
        PurchaseOrder purchaseOrderFechado = PurchaseOrder.builder().status(StatusEnum.FINALIZADO).build();

        Mockito.when(orderRepository.getById(1L)).thenReturn(purchaseOrderAberto);
        Mockito.when(orderRepository.save(Mockito.any(PurchaseOrder.class))).thenReturn(purchaseOrderFechado);

        PurchaseOrder result = this.service.atualizar(1L);

        assert result.equals(purchaseOrderFechado);

    }

}
