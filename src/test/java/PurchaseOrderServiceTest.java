import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.repository.OrderRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.service.OrderService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.constraints.NotNull;

public class PurchaseOrderServiceTest {

    private OrderService service;

    private OrderRepository orderRepository;

    private ProductRepository productRepository;

    /**
     * @Author: David
     * @Methodo: Mudar Cart para Aberto ou Finalizado na Order
     * @Description: Modifique o pedido existente. torná-lo do tipo de carrinho para modificar - ABERTO/FINALIZADO
     */
    @Test
    void naoDeveAlterarStatusQuandoReceberVazio() {

        this.orderRepository = Mockito.mock(OrderRepository.class);
        this.productRepository = Mockito.mock(ProductRepository.class);

        this.service = new OrderService(orderRepository, productRepository);

        PurchaseOrder purchaseOrderAberto = PurchaseOrder.builder().status(StatusEnum.ABERTO).build();
        PurchaseOrder purchaseOrderFechado = PurchaseOrder.builder().status(StatusEnum.FINALIZADO).build();

        Mockito.when(orderRepository.getById(1L)).thenReturn(purchaseOrderAberto);
        Mockito.when(orderRepository.save(Mockito.any(PurchaseOrder.class))).thenReturn(purchaseOrderFechado);

        PurchaseOrder result = this.service.atualizar(1L);

        assert result.equals(purchaseOrderFechado);

    }

}
