package br.com.meli.dhprojetointegrador.integration;


import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.repository.BuyerRepository;
import br.com.meli.dhprojetointegrador.repository.PurchaseOrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WithMockUser(username = "jooj", roles = {"BUYER"})
public class PurchaseOrderControllerTest extends BaseIntegrationControllerTests {

    @Autowired
    private MockMvc mock;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * @Author: David
     * @Methodo: Mudar Cart para Aberto ou Finalizado na Order
     * @Description: Modifique o pedido existente. torná-lo do tipo de carrinho para modificar - ABERTO/FINALIZADO
     */
    @Test
    public void deveMudarStatusOrder() throws Exception{

        setup();

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.put("/api/v1/fresh-products/orders/2")
                        .contentType("id").content("2"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        PurchaseOrder exceptionResponse = objectMapper.readValue(responsePayload, PurchaseOrder.class);

        assertNotNull(responsePayload);
        assertEquals(exceptionResponse.getStatus(), StatusEnum.ABERTO);
    }

    private void setup() {

        Buyer buyer = setupBuyer();
        setupPurchaseOrder(buyer,StatusEnum.ABERTO, 1L);
        setupPurchaseOrder(buyer, StatusEnum.FINALIZADO, 2L);

    }

    private Buyer setupBuyer() {
        Buyer buyer = Buyer.builder()
                .id(1L)
                .name("Bruno")
                .email("bruno@email.com")
                .build();

        return buyerRepository.save(buyer);
    }

    private PurchaseOrder setupPurchaseOrder(Buyer buyer, StatusEnum status, Long id){

       PurchaseOrder purchaseOrder = PurchaseOrder.builder()
               .id(id)
               .buyer(buyer)
               .status(status)
               .date(LocalDate.of(2022,10,10))
               .build();

       return purchaseOrderRepository.save(purchaseOrder);
    }

}
