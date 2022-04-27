import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.repository.OrderRepository;
import br.com.meli.dhprojetointegrador.service.OrderService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

public class PurchaseOrderServiceTest {

    private OrderService service;

    private OrderRepository orderRepository;

    /**
     * @Author: David
     * @Methodo: Mudar Cart para Aberto ou Finalizado na Order
     * @Description: Modifique o pedido existente. torná-lo do tipo de carrinho para modificar - ABERTO/FINALIZADO
     */
    @Test
    void naoDeveAlterarStatusQuandoReceberVazio() {

        this.orderRepository = Mockito.mock(OrderRepository.class);
        this.service = new OrderService(orderRepository);

        PurchaseOrder purchaseOrder = PurchaseOrder.builder().status(StatusEnum.ABERTO).build();

        Mockito.when(this.service.atualizar(any())).thenReturn(purchaseOrder);


        PurchaseOrder result = this.service.atualizar(purchaseOrder);

        assertNotNull(result);
        assertTrue(!result.isEmpty());
        assertNotEquals("", result);

    }

}
