package br.com.meli.dhprojetointegrador.integration;


import br.com.meli.dhprojetointegrador.dto.request.BatchStockPostRequest;
import br.com.meli.dhprojetointegrador.dto.request.InboundOrderPostRequest;
import br.com.meli.dhprojetointegrador.dto.response.freshproducts.FreshProductsQueriedResponse;
import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.enums.DueDateEnum;
import br.com.meli.dhprojetointegrador.repository.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "jooj", roles = {"AGENT", "BUYER"})
public class ProductControllerTests extends BaseIntegrationControllerTests {

    private final static LocalDate LOCAL_DATE_NOW_MOCK = LocalDate.of(2022, 4, 13);
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private Clock clock;

    @BeforeEach
    public void setup() {
        when(clock.instant()).thenReturn(LOCAL_DATE_NOW_MOCK.atStartOfDay(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.of("Z"));
    }

    @Test
    @DisplayName("Products filter - Produto não está registrado ou está vencido")
    public void findAllProducts_shouldReturnStatusNotFound_whenProductIsNotRegistered() throws Exception {
        mockMvc.perform(get("/api/v1/fresh-products/list").param("productId", "1")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Busca filtrada por produtos - Retorno da lista de batch stock apropriado")
    public void findAllProducts_shouldReturnStatusOkAndValidResponseBody_whenProductIsRegisteredAndItHasValidDueDates() throws Exception {
        setupBaseData();

        MvcResult result = mockMvc.perform(get("/api/v1/fresh-products/list").param("productId", "1")).andExpect(status().isOk())
                .andReturn();

        String responsePayload = result.getResponse().getContentAsString();

        FreshProductsQueriedResponse response = objectMapper.readValue(responsePayload, FreshProductsQueriedResponse.class);

        assertEquals(2, response.getBatchStock().size());
        assertEquals("1", response.getProductId());
        assertEquals("1", response.getWarehouseCode());
    }

    /**
     * Author: Mariana Galdino
     * Test: Retorna todos os produtos
     * Description: Serviço responsavel por retornar todos os produtos presentes na
     * aplicação;
     *
     * @return lista de produtos
     */

    @Test
    @DisplayName("Test01 - req 02 - Return all registered products")
    public void shouldReturnAllRegisteredProducts() throws Exception {

        Product product1 = setupProducts("frango", 20f);
        Product product2 = setupProducts("sorvete", 10f);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();


        String responsePayload = result.getResponse().getContentAsString();
        List<Product> products = objectMapper.readerForListOf(Product.class).readValue(responsePayload);

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(responsePayload);
        assertFalse(products.isEmpty());

    }

    /**
     * Author: Mariana Galdino
     * Test: Retorna lista de produtos vazia e status 404 not found
     * Description: Serviço responsavel por retornar uma lista vazia de produtos
     *
     * @return lista vazia de produtos
     */
    @Test
    @DisplayName( "Test02 - req02 - Return 404 not found")
    public void shouldReturnHttpNotFound_whenNoOneProduct() throws Exception {

        Product product1 = Product.builder().name("alface").price(new BigDecimal(12)).volume(5f).build();

        String payload = objectMapper.writeValueAsString(product1);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();


        String responsePayload = result.getResponse().getContentAsString();


        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertTrue(responsePayload.isEmpty());

    }
    private Product setupProducts(String name, Float volume) throws Exception {
        Product product = Product.builder().name(name).volume(volume).price(BigDecimal.valueOf(22)).build();
        productRepository.save(product);
        return product;
    }


        /**
         * @Author: Matheus Guerra
         * @Teste: Teste de integração do endpoint "/api/v1/fresh-products/list"
         * @Description: valida o funcionamento correto do endpoint:
         *  Retorno de uma lista de produtos filtrados por categoria;
         * Retorno de uma lista vazia caso não haja produtos da categoria escolhida;
         *
         */
        @Test
        @DisplayName("US:02 - Item 02")
        public void correct_functioning_of_returnAllProductsByCategory () throws Exception {

            Category categoryFF = setupCategory(CategoryEnum.FF);
            Category categoryFS = setupCategory(CategoryEnum.FS);

            Product productFFa = setupProduct("morango", categoryFF);
            Product productFFb = setupProduct("laranja", categoryFF);

            Product productFSa = setupProduct("pera", categoryFS);

            setupProduct("objeto", null);

            List<Product> produtosFF = Arrays.asList(productFFa, productFFb);
            List<Product> produtosFS = Arrays.asList(productFSa);

            String payloadFF = objectMapper.writeValueAsString(produtosFF);
            String payloadFS = objectMapper.writeValueAsString(produtosFS);

            MvcResult resultFF = mockMvc
                    .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/list").param("category", "FF"))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            MvcResult resultFS = mockMvc
                    .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/list").param("category", "FS"))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            MvcResult resultRF = mockMvc
                    .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/list").param("category", "RF"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

            String responsePayloadFF = resultFF.getResponse().getContentAsString();
            String responsePayloadFS = resultFS.getResponse().getContentAsString();
            String responsePayloadRF = resultRF.getResponse().getContentAsString();

            assertNotNull(responsePayloadFF);
            assertNotNull(responsePayloadFS);
            assertEquals("", responsePayloadRF);

            assertEquals(payloadFF, responsePayloadFF);
            assertEquals(payloadFS, responsePayloadFS);

        }


        private Category setupCategory (CategoryEnum categoryEnum){
            Category category = Category.builder().name(categoryEnum).sections(Collections.EMPTY_SET).build();
            return categoryRepository.save(category);
        }

        private Product setupProduct(String name, Category category) {
        Product product = Product.builder().name(name).category(category).batchStockList(Collections.EMPTY_SET).build();

            productRepository.save(product);
            return product;
        }

        public void setupBaseData () throws Exception {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            javaTimeModule.addSerializer(Date.class, new DateSerializer(false, dateFormat));

            objectMapper.registerModule(javaTimeModule);

            Agent agent = Agent.builder().name("Agente de warehouse").build();
            Warehouse warehouse = Warehouse.builder().name("Centro de distribuição MELIMELI").agent(agent).build();
            agent.setWarehouse(warehouse);

            Warehouse warehouseManaged = warehouseRepository.save(warehouse);

            Category category = Category.builder().maximumTemperature(22.0f).minimumTemperature(15.0f)
                    .name(CategoryEnum.FRIOS).build();

            Category managedCategory = categoryRepository.save(category);

            Section section = Section.builder().capacity(32.0f).category(managedCategory).name("Seção de frios")
                    .warehouse(warehouseManaged).build();

            Section managedSection = sectionRepository.save(section);

            Seller seller = Seller.builder().name("João Silvério").build();

            Seller managedSeller = sellerRepository.save(seller);

            Product frango = Product.builder().category(managedCategory).name("Frango").price(BigDecimal.valueOf(32.22))
                    .volume(2.0f).seller(managedSeller).build();

            Product carne = Product.builder().category(managedCategory).name("Carne").price(BigDecimal.valueOf(32.22))
                    .volume(2.0f).seller(managedSeller).build();

            Product frangoManaged = productRepository.save(frango);
            Product carneManaged = productRepository.save(carne);

            LocalDate expiredDueDate = LOCAL_DATE_NOW_MOCK.plusDays(1L);
            LocalDate notExpiredDueDate = LOCAL_DATE_NOW_MOCK.plusWeeks(DueDateEnum.MAX_DUEDATE_WEEKS.getDuedate()).plusDays(1);

            BatchStockPostRequest frangoBatchStock = BatchStockPostRequest.builder().batchNumber(123L).currentQuantity(32)
                    .currentTemperature(17.0f).dueDate(notExpiredDueDate).initialQuantity(42).minimumTemperature(17.0f)
                    .currentQuantity(21)
                    .productId(frangoManaged.getId()).manufacturingDate(LocalDate.of(2022, 3, 22))
                    .manufacturingTime(LocalDateTime.of(2022, 3, 22, 3, 22, 1)).build();

            BatchStockPostRequest carneBatchStock = BatchStockPostRequest.builder().batchNumber(124L).currentQuantity(32)
                    .currentTemperature(17.0f).dueDate(notExpiredDueDate).initialQuantity(42).minimumTemperature(17f)
                    .currentQuantity(21)
                    .productId(carneManaged.getId()).manufacturingDate(LocalDate.of(2022, 3, 22))
                    .manufacturingTime(LocalDateTime.of(2022, 3, 22, 3, 22, 1)).build();

            InboundOrderPostRequest postRequest = InboundOrderPostRequest.builder().agentId(1L)
                    .orderDate(LocalDate.of(2022, 3, 3))
                    .batchStock(List.of(frangoBatchStock, carneBatchStock)).sectionId(1L).build();

            String payload = objectMapper.writeValueAsString(postRequest);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/inboundorder")
                    .contentType(MediaType.APPLICATION_JSON).content(payload))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            frangoBatchStock.setBatchNumber(125L);
            postRequest.setBatchStock(List.of(frangoBatchStock));

            payload = objectMapper.writeValueAsString(postRequest);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/inboundorder")
                    .contentType(MediaType.APPLICATION_JSON).content(payload))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            frangoBatchStock.setDueDate(expiredDueDate);
            frangoBatchStock.setBatchNumber(126L);

            postRequest.setBatchStock(List.of(frangoBatchStock));

            payload = objectMapper.writeValueAsString(postRequest);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/inboundorder")
                    .contentType(MediaType.APPLICATION_JSON).content(payload))
                    .andExpect(MockMvcResultMatchers.status().isOk());


        }

    }
