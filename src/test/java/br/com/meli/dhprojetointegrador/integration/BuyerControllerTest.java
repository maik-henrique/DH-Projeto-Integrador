package br.com.meli.dhprojetointegrador.integration;



import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.enums.BuyerStatusEnum;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.repository.BuyerRepository;
import br.com.meli.dhprojetointegrador.repository.PurchaseOrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@SpringBootTest()
@AutoConfigureMockMvc
public class BuyerControllerTest {

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
     * @Description: Listar todas as purchaseOrder referentes ao Buyer
     * @return
     */

    @Test
    public void deveListarTodasPurchasesDoBuyerAtivoStatusFinalizado() throws Exception{

        Buyer buyer = setupBuyer(BuyerStatusEnum.ATIVO);
        PurchaseOrder purchaseOrderFinalizado = setupPurchaseOrder(buyer,StatusEnum.FINALIZADO);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/buyer/{id}",purchaseOrderFinalizado.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();

        List<PurchaseOrder> response = objectMapper.readerForListOf(PurchaseOrder.class).readValue(responsePayload);

        assertNotNull(responsePayload);
        assertEquals(response.get(0).getBuyer().getStatus(), BuyerStatusEnum.ATIVO);
        assertEquals(response.get(0).getStatus(), StatusEnum.FINALIZADO);
    }


    /**
     * @Author: David
     * @Description: Listar todas as purchaseOrder referentes ao Buyer com um mesmo Status
     * @return
     */
    @Test
    public void deveListarTodasPurchasesDoBuyerComMesmoStatusFinalizado() throws Exception {

        Buyer buyer = setupBuyer(BuyerStatusEnum.ATIVO);
        PurchaseOrder purchaseOrderFinalizado = setupPurchaseOrder(buyer, StatusEnum.FINALIZADO);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/buyer")
                        .param("id", buyer.getId().toString())
                        .param("status", purchaseOrderFinalizado.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        List<PurchaseOrder> response = objectMapper.readerForListOf(PurchaseOrder.class).readValue(responsePayload);

        assertNotNull(responsePayload);
        assertEquals(response.get(0).getBuyer().getStatus(), BuyerStatusEnum.ATIVO);
        assertEquals(response.get(0).getStatus(), StatusEnum.FINALIZADO);

    }
        /**
         * @Author: David
         * @Description: Listar todas as purchaseOrder referentes ao Buyer com um mesmo Status
         * @return
         */
        @Test
        public void deveListarTodasPurchasesDoBuyerComMesmoStatusAberto() throws Exception{

            Buyer buyer = setupBuyer(BuyerStatusEnum.ATIVO);
            PurchaseOrder purchaseOrderAberto = setupPurchaseOrder(buyer,StatusEnum.ABERTO);

        MvcResult result2 = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/buyer")
                        .param("id", buyer.getId().toString())
                        .param("status", purchaseOrderAberto.getStatus().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responsePayload2 = result2.getResponse().getContentAsString();
        List<PurchaseOrder> response2 = objectMapper.readerForListOf(PurchaseOrder.class).readValue(responsePayload2);

        assertNotNull(responsePayload2);
        assertEquals(response2.get(0).getBuyer().getStatus(), BuyerStatusEnum.ATIVO);
        assertEquals(response2.get(0).getStatus(), StatusEnum.ABERTO);
    }


    /**
     * @Author: David
     * @Methodo:
     * @Description: Alterar os dados do buyer, pelo email/nome
     * @return
     */
    @Test
    public void deveAlterarDadosDoBuyer() throws Exception{

        Buyer buyer = setupBuyer(BuyerStatusEnum.ATIVO);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.put("/api/v1/fresh-products/buyer")
                        .param("id", buyer.getId().toString())
                        .param("name", "Cookie")
                        .param("email", "meli@cookie.com"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        Buyer response = objectMapper.readValue(responsePayload, Buyer.class);

        assertNotNull(responsePayload);
        assertEquals(response.getName(), "Cookie");
        assertEquals(response.getEmail(), "meli@cookie.com");

    }


    /**
     * @Author: David
     * @Description: Desativar a conta a partir de email e password, setando Ativo e Inativo
     * @return
     */
    @Test
    public void deveDesativarUmBuyer() throws Exception{

        Buyer buyer = setupBuyer(BuyerStatusEnum.ATIVO);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.delete("/api/v1/fresh-products/buyer/{id}", buyer.getId()))
                .andExpect(MockMvcResultMatchers.status().isAccepted()).andReturn();

        int respondeCode = result.getResponse().getStatus();
        assertEquals(respondeCode, 202);
    }

    private Buyer setupBuyer(BuyerStatusEnum status) {
        Buyer buyer = Buyer.builder()
                .name("Bruno")
                .password("1234567")
                .email("bruno@email.com")
                .status(status)
                .build();

        return buyerRepository.save(buyer);
    }

    private PurchaseOrder setupPurchaseOrder(Buyer buyer, StatusEnum status){

        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .buyer(buyer)
                .status(status)
                .date(LocalDate.of(2022,10,10))
                .build();

        return purchaseOrderRepository.save(purchaseOrder);
    }


}
