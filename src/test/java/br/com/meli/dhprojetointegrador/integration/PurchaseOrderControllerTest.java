package br.com.meli.dhprojetointegrador.integration;

import br.com.meli.dhprojetointegrador.controller.OrderController;
import br.com.meli.dhprojetointegrador.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@SpringBootTest(classes= OrderController.class)
@AutoConfigureMockMvc
public class PurchaseOrderControllerTest {


    @Autowired
    private MockMvc mvc;

    /**
     * @Author: David
     * @Methodo: Mudar Cart para Aberto ou Finalizado na Order
     * @Description: Modifique o pedido existente. torná-lo do tipo de carrinho para modificar - ABERTO/FINALIZADO
     */
    @Test
    public void deveMudarStatusOrder() throws Exception{

            mvc.perform(MockMvcRequestBuilders
                    .put("/api/v1/fresh-products/orders/2"))
                    .andExpect(MockMvcResultMatchers.status().isOk());

    }

}
