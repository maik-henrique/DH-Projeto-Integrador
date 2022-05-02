package br.com.meli.dhprojetointegrador.integration;

import br.com.meli.dhprojetointegrador.dto.request.ProductInput;
import br.com.meli.dhprojetointegrador.dto.request.PurchaseOrderInput;
import br.com.meli.dhprojetointegrador.dto.request.evaluation.EvaluationDetailsRegistrationRequest;
import br.com.meli.dhprojetointegrador.dto.request.evaluation.PurchaseOrderEvaluationRegistrationRequest;
import br.com.meli.dhprojetointegrador.dto.response.ExceptionPayloadResponse;
import br.com.meli.dhprojetointegrador.dto.response.evaluation.PurchaseOrderEvaluationFetchResponse;
import br.com.meli.dhprojetointegrador.dto.response.evaluation.PurchaseOrderEvaluationResponse;
import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PurchaseOrderEvaluationControllerTests extends BaseIntegrationControllerTests {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BatchStockRepository batchStockRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private InboundOrderRepository inboundOrderRepository;


    @Test
    public void save_shouldReturnedRegisteredEvaluation_whenProperRequestIsMade() throws Exception {
        setup();
        createPurchaseOrder();
        final String expectedComment = "O produto muito bom";
        final Long expectedProductId = 1L;
        final Integer expectedRating = 10;

        EvaluationDetailsRegistrationRequest evaluation = EvaluationDetailsRegistrationRequest.builder()
                .productId(expectedProductId)
                .comment(expectedComment)
                .rating(expectedRating)
                .build();
        PurchaseOrderEvaluationRegistrationRequest request = PurchaseOrderEvaluationRegistrationRequest.builder()
                .buyerId(1L)
                .evaluation(evaluation)
                .purchaseOrderId(1L)
                .build();
        String requestPayload = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mock.perform(post("/api/v1/evaluation")
                .contentType(APPLICATION_JSON).content(requestPayload)
        ).andExpect(status().isCreated())
                .andReturn();

        PurchaseOrderEvaluationResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                PurchaseOrderEvaluationResponse.class);

        assertEquals(expectedProductId, response.getProductId());
        assertEquals(expectedRating, response.getRating());
        assertEquals(expectedComment, response.getComment());
    }

    @Test
    public void save_shouldReturnedUnprocessableEntity_whenPurchasWasAlreadyEvaluated() throws Exception {
        setup();
        createPurchaseOrder();
        final String expectedComment = "O produto muito bom";
        final Long expectedProductId = 1L;
        final Integer expectedRating = 10;

        EvaluationDetailsRegistrationRequest evaluation = EvaluationDetailsRegistrationRequest.builder()
                .productId(expectedProductId)
                .comment(expectedComment)
                .rating(expectedRating)
                .build();
        PurchaseOrderEvaluationRegistrationRequest request = PurchaseOrderEvaluationRegistrationRequest.builder()
                .buyerId(1L)
                .evaluation(evaluation)
                .purchaseOrderId(1L)
                .build();
        String requestPayload = objectMapper.writeValueAsString(request);

        mock.perform(post("/api/v1/evaluation").contentType(APPLICATION_JSON).content(requestPayload))
                .andExpect(status().isCreated());

        MvcResult mvcResult = mock.perform(post("/api/v1/evaluation").contentType(APPLICATION_JSON).content(requestPayload))
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        ExceptionPayloadResponse exceptionPayloadResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExceptionPayloadResponse.class);

        assertEquals("Purchase of id 1 and product of id 1 were already evaluated", exceptionPayloadResponse.getDescription());
        assertEquals("An error occurred during business validation processing", exceptionPayloadResponse.getTitle());
        assertEquals(422, exceptionPayloadResponse.getStatusCode());

    }

    @Test
    public void save_shouldReturnUnprocessableEntity_whenPurchaseOrderDoesNotExists() throws Exception {
        final String expectedComment = "O produto muito bom";
        final Long expectedProductId = 1L;
        final Integer expectedRating = 10;

        EvaluationDetailsRegistrationRequest evaluation = EvaluationDetailsRegistrationRequest.builder()
                .productId(expectedProductId)
                .comment(expectedComment)
                .rating(expectedRating)
                .build();
        PurchaseOrderEvaluationRegistrationRequest request = PurchaseOrderEvaluationRegistrationRequest.builder()
                .buyerId(1L)
                .evaluation(evaluation)
                .purchaseOrderId(1L)
                .build();
        String requestPayload = objectMapper.writeValueAsString(request);

        mock.perform(post("/api/v1/evaluation")
                .contentType(APPLICATION_JSON).content(requestPayload)
        ).andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void findEvaluationsByBuyerId_shouldReturnedEvaluations_whenThereAreEvaluationsRegistered() throws Exception {
        setup();
        createPurchaseOrder();
        final String expectedComment = "O produto muito bom";
        final Long expectedProductId = 1L;
        final Integer expectedRating = 10;
        final Long expetedBuyerId = 1L;

        EvaluationDetailsRegistrationRequest evaluation = EvaluationDetailsRegistrationRequest.builder()
                .productId(expectedProductId)
                .comment(expectedComment)
                .rating(expectedRating)
                .build();
        PurchaseOrderEvaluationRegistrationRequest request = PurchaseOrderEvaluationRegistrationRequest.builder()
                .buyerId(expetedBuyerId)
                .evaluation(evaluation)
                .purchaseOrderId(1L)
                .build();
        String requestPayload = objectMapper.writeValueAsString(request);

        mock.perform(post("/api/v1/evaluation")
                .contentType(APPLICATION_JSON).content(requestPayload)
        ).andExpect(status().isCreated());

        MvcResult mvcResult = mock.perform(get("/api/v1/evaluation/buyer/1"))
                .andExpect(status().isOk()).andReturn();

        List<PurchaseOrderEvaluationFetchResponse> response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<PurchaseOrderEvaluationFetchResponse>>() {
        });

        PurchaseOrderEvaluationFetchResponse itemResponse = response.get(0);
        assertEquals(1, itemResponse.getPurchaseId());
        assertEquals(expectedProductId, itemResponse.getProductId());
        assertEquals(expectedComment, itemResponse.getComment());
        assertEquals(expectedRating, itemResponse.getRating());

    }
    @Test
    public void findEvaluationsByBuyerId_shouldReturnedNotFound_whenEvaluationsAreNotFound() throws Exception {
        MvcResult mvcResult = mock.perform(get("/api/v1/evaluation/buyer/1"))
                .andExpect(status().isNotFound()).andReturn();
        ExceptionPayloadResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExceptionPayloadResponse.class);

        assertEquals("No evaluation was found for buyer of id 1", response.getDescription());
        assertEquals(404, response.getStatusCode());
        assertEquals("The target resource wasn't found", response.getTitle());
    }

    private void createPurchaseOrder() throws Exception {
        LocalDate date = LocalDate.of(2021, 4, 25);

        ProductInput product1 = ProductInput.builder().productId(1L).quantity(5).build();
        ProductInput product2 = ProductInput.builder().productId(2L).quantity(5).build();

        PurchaseOrderInput purchaseOrderInput = PurchaseOrderInput.builder()
                .date(date)
                .products(List.of(product1, product2))
                .buyerId(1L)
                .build();

        String payload = objectMapper.writeValueAsString(purchaseOrderInput);

        mock.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/orders/")
                .contentType(APPLICATION_JSON).content(payload))
                .andExpect(status().isCreated()).andExpect(status().isCreated());
    }


    private void setup() {
        setupBuyer();
        Warehouse warehouse = setupWarehouse();
        Category categoryFS = setupCategory(CategoryEnum.FS);
        Section section = setupSection(categoryFS, warehouse);

        Product product1 = setupProduct(1L, "Banana", new BigDecimal("2.50"));
        Product product2 = setupProduct(2L, "Cenoura", new BigDecimal("4.50"));
        BatchStock batchStock1 = setupBatchStock(1L, product1);
        BatchStock batchStock2 = setupBatchStock(2L, product2);

        setupInboundOrder(1L, batchStock1, warehouse, section);
        setupInboundOrder(2L, batchStock2, warehouse, section);
    }

    private Buyer setupBuyer() {
        Buyer buyer = Buyer.builder()
                .id(1L)
                .name("Bruno")
                .password("123456")
                .email("bruno@email.com")
                .build();

        return buyerRepository.save(buyer);
    }

    private Product setupProduct(Long id, String name, BigDecimal price) {
        Product product = Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .build();

        return productRepository.save(product);
    }

    private Warehouse setupWarehouse() {
        Agent agent = Agent.builder().name("007").build();
        Warehouse warehouse = Warehouse.builder().id(1L).name("warehouse 01").agent(agent).build();
        agent.setWarehouse(warehouse);

        return warehouseRepository.save(warehouse);
    }

    private Category setupCategory(CategoryEnum categoryEnum) {
        Category category = Category.builder().name(categoryEnum).build();
        return categoryRepository.save(category);
    }

    private Section setupSection(Category managedCategory, Warehouse managedWarehouse) {
        Section section = Section.builder().category(managedCategory).name("Cold section").capacity(32.0f)
                .warehouse(managedWarehouse).build();

        return sectionRepository.save(section);
    }

    private BatchStock setupBatchStock(Long batchNumber, Product managedProduct) {
        BatchStock batchStock = BatchStock.builder()
                .batchNumber(batchNumber)
                .currentQuantity(15).initialQuantity(15)
                .currentTemperature(24f)
                .manufacturingDate(LocalDate.of(2020, 4, 22))
                .manufacturingTime(LocalDateTime.of(2016, 10, 30, 14, 23, 25)).dueDate(LocalDate.of(2022, 4, 22))
                .products(managedProduct).build();

        return batchStockRepository.save(batchStock);
    }

    private InboundOrder setupInboundOrder(Long orderNumber, BatchStock managedBatchStock, Warehouse managedWarehouse,
                                           Section managedSection) {

        InboundOrder inboundOrder = InboundOrder.builder()
                .orderNumber(orderNumber).batchStockList(Set.of(managedBatchStock))
                .section(managedSection).orderDate(LocalDate.of(2020, 3, 4))
                .agent(managedWarehouse.getAgent())
                .build();

        return inboundOrderRepository.save(inboundOrder);
    }

}
