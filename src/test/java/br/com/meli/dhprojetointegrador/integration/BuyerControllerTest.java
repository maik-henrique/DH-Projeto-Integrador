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

        setup();

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/buyer/4")
                        .contentType("id").content("4"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        PurchaseOrder exceptionResponse = objectMapper.readValue(responsePayload, PurchaseOrder.class);

        assertNotNull(responsePayload);
        assertEquals(exceptionResponse.getStatus(), BuyerStatusEnum.ATIVO);
        assertEquals(exceptionResponse.getStatus(), StatusEnum.FINALIZADO);
    }


    /**
     * @Author: David
     * @Description: Listar todas as purchaseOrder referentes ao Buyer com um mesmo Status
     * @return
     */
    @Test
    public void deveListarTodasPurchasesDoBuyerComMesmoStatus() throws Exception{

        setup();

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/buyer/4")
                        .contentType("id").content("2"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        PurchaseOrder exceptionResponse = objectMapper.readValue(responsePayload, PurchaseOrder.class);

        assertNotNull(responsePayload);
        assertEquals(exceptionResponse.getStatus(), StatusEnum.ABERTO);
        assertEquals(exceptionResponse.getStatus(), BuyerStatusEnum.ATIVO);
    }


    /**
     * @Author: David
     * @Methodo:
     * @Description: Alterar os dados do buyer, pelo email/nome
     * @return
     */
    /*
    @Test
    public void deveAlterarDadosDoBuyer() throws Exception{

        setup();

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.put("/api/v1/fresh-products/buyer/4")
                        .contentType("id").content("2"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        Buyer exceptionResponse = objectMapper.readValue(responsePayload, Buyer.class);

        assertNotNull(responsePayload);
        assertEquals(exceptionResponse.getStatus(), BuyerStatusEnum.ATIVO);
        assertEquals(exceptionResponse.getName(), BuyerStatusEnum.ATIVO);
        assertEquals(exceptionResponse.getEmail(), BuyerStatusEnum.ATIVO);


    }
     */

    /**
     * @Author: David
     * @Description: Desativar a conta a partir de email e password, setando Ativo e Inativo
     * @return
     */
    @Test
    public void deveDesativarUmBuyer() throws Exception{

        setup();

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.delete("/api/v1/fresh-products/buyer/4")
                        .contentType("id").content("2"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        Buyer exceptionResponse = objectMapper.readValue(responsePayload, Buyer.class);

        assertNotNull(responsePayload);
        assertEquals(exceptionResponse.getStatus(), BuyerStatusEnum.ATIVO);
    }

    private void setup() {

        Buyer buyer = setupBuyer(BuyerStatusEnum.ATIVO);
        setupPurchaseOrder(buyer,StatusEnum.ABERTO, 4L);
        setupPurchaseOrder(buyer, StatusEnum.FINALIZADO, 4L);
    }

    private Buyer setupBuyer(BuyerStatusEnum status) {
        Buyer buyer = Buyer.builder()
                .id(4L)
                .name("Bruno")
                .password("1234567")
                .email("bruno@email.com")
                .status(status)
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
