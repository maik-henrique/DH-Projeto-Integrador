package br.com.meli.dhprojetointegrador.integration;

import br.com.meli.dhprojetointegrador.dto.request.ProductInput;
import br.com.meli.dhprojetointegrador.dto.request.PurchaseOrderInput;
import br.com.meli.dhprojetointegrador.dto.response.*;
import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTests {

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
    @DisplayName("Create Purchase Order - when receiving the right input function works properly")
    public void correct_functioning_of_PurchaseOrderProductRegistration() throws Exception {
        setup();
        LocalDate date = LocalDate.of(2021, 04, 25);

        ProductInput product1 = ProductInput.builder().productId(1L).quantity(5).build();
        ProductInput product2 = ProductInput.builder().productId(2L).quantity(5).build();

        PurchaseOrderInput purchaseOrderInput = PurchaseOrderInput.builder()
                .date(date)
                .orderStatus(StatusEnum.ABERTO)
                .products(List.of(product1, product2))
                .buyerId(1L)
                .build();

        String payload = objectMapper.writeValueAsString(purchaseOrderInput);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/orders/")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        TotalPrice totalPriceResponse = objectMapper.readValue(responsePayload, TotalPrice.class);

        assertNotNull(responsePayload);
        assertEquals(totalPriceResponse.getTotalPrice(), 35.00);
    }

    @Test
    @DisplayName("Create Purchase Order - throws correct error when receive a user that doesn't exist")
    public void function_PurchaseOrderProductRegistration_should_trow_BuyerNotFoundException() throws Exception {
        setup();
        LocalDate date = LocalDate.of(2021, 04, 25);

        ProductInput product1 = ProductInput.builder().productId(1L).quantity(5).build();
        ProductInput product2 = ProductInput.builder().productId(2L).quantity(5).build();

        PurchaseOrderInput purchaseOrderInput = PurchaseOrderInput.builder()
                .date(date)
                .orderStatus(StatusEnum.ABERTO)
                .products(List.of(product1, product2))
                .buyerId(10L)
                .build();

        String payload = objectMapper.writeValueAsString(purchaseOrderInput);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/orders/")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        ExceptionPayloadDTO exceptionResponse = objectMapper.readValue(responsePayload, ExceptionPayloadDTO.class);

        assertNotNull(responsePayload);
        assertEquals(exceptionResponse.getTitle(), "Buyer Not Found");
    }

    @Test
    @DisplayName("Create Purchase Order - throws correct error when receive a product that doesn't exist")
    public void function_PurchaseOrderProductRegistration_should_trow_ProductNotFoundException() throws Exception {
        setup();
        LocalDate date = LocalDate.of(2021, 04, 25);

        ProductInput product1 = ProductInput.builder().productId(6L).quantity(5).build();
        ProductInput product2 = ProductInput.builder().productId(2L).quantity(5).build();

        PurchaseOrderInput purchaseOrderInput = PurchaseOrderInput.builder()
                .date(date)
                .orderStatus(StatusEnum.ABERTO)
                .products(List.of(product1, product2))
                .buyerId(1L)
                .build();

        String payload = objectMapper.writeValueAsString(purchaseOrderInput);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/orders/")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        ExceptionPayloadDTO exceptionResponse = objectMapper.readValue(responsePayload, ExceptionPayloadDTO.class);

        assertNotNull(responsePayload);
        assertEquals(exceptionResponse.getTitle(), "Product Not Found");
    }

    @Test
    @DisplayName("Create Purchase Order - throws correct error when receive a quantity larger than in stock")
    public void function_PurchaseOrderProductRegistration_should_throw_NotEnoughProductsException() throws Exception {
        setup();
        LocalDate date = LocalDate.of(2021, 04, 25);

        ProductInput product1 = ProductInput.builder().productId(1L).quantity(25).build();
        ProductInput product2 = ProductInput.builder().productId(2L).quantity(5).build();

        PurchaseOrderInput purchaseOrderInput = PurchaseOrderInput.builder()
                .orderStatus(StatusEnum.ABERTO)
                .date(date)
                .products(List.of(product1, product2))
                .buyerId(1L)
                .build();

        String payload = objectMapper.writeValueAsString(purchaseOrderInput);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/orders/")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        ExceptionPayloadDTO exceptionResponse = objectMapper.readValue(responsePayload, ExceptionPayloadDTO.class);

        assertNotNull(responsePayload);
        assertEquals(exceptionResponse.getTitle(), "Not Enough Products");
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
        Agent agent = Agent.builder()
                .name("007")
                .password("password")
                .build();

        Warehouse warehouse = Warehouse.builder().id(1L).name("warehouse 01").agent(agent).build();
        agent.setWarehouse(warehouse);

        return warehouseRepository.save(warehouse);
    }

    private Category setupCategory(CategoryEnum categoryEnum) {
        Category category = Category.builder().name(categoryEnum)
                .maximumTemperature(32.0F)
                .minimumTemperature(-10.0F)
                .maximumTemperature(-10.0F)
                .build();
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
                .minimumTemperature(32)
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
