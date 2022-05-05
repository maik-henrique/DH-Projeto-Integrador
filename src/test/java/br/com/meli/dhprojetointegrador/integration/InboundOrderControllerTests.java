package br.com.meli.dhprojetointegrador.integration;

import br.com.meli.dhprojetointegrador.dto.request.BatchStockUpdateRequest;
import br.com.meli.dhprojetointegrador.dto.request.InboundOrderUpdateRequest;
import br.com.meli.dhprojetointegrador.dto.response.BatchStockResponse;
import br.com.meli.dhprojetointegrador.dto.response.ExceptionPayloadResponse;
import br.com.meli.dhprojetointegrador.dto.response.InboundOrderResponse;
import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
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

@WithMockUser(username = "jooj", roles = {"AGENT", "ADMIN"})
public class InboundOrderControllerTests extends BaseIntegrationControllerTests {

    @Autowired
    private MockMvc mock;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InboundOrderRepository inboundOrderRepository;
    @Autowired
    private BatchStockRepository batchStockRepository;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Inbound Order - Proper setting of the of values")
    public void update_shouldUpdateInboundOrderField_whenProperRequestIsSent() throws Exception {
        setupBaseData(2.0f);

        BatchStockUpdateRequest expectedBatchStock = BatchStockUpdateRequest.builder()
                .batchNumber(1L)
                .currentQuantity(4)
                .initialQuantity(4)
                .currentTemperature(24.0f)
                .minimumTemperature(19f)
                .manufacturingDate(LocalDate.of(2022, 5, 23))
                .manufacturingTime(LocalDateTime.of(2016, 12, 30, 14, 23, 25))
                .dueDate(LocalDate.of(2022, 4, 22))
                .productId(2L).build();

        InboundOrderUpdateRequest inboundOrderUpdateRequest = InboundOrderUpdateRequest.builder().agentId(1L)
                .orderDate(LocalDate.of(2020, 3, 3))
                .orderNumber(1L)
                .batchStock(List.of(expectedBatchStock)).sectionId(1L)
                .build();

        String payload = objectMapper.writeValueAsString(inboundOrderUpdateRequest);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.put("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();


        String responsePaylaod = result.getResponse().getContentAsString();
        InboundOrderResponse inboundOrderResponse = objectMapper.readValue(responsePaylaod, InboundOrderResponse.class);

        assertNotNull(inboundOrderResponse);

        BatchStockResponse batchStockResponse = inboundOrderResponse.getBatchStock().get(0);

        assertNotNull(batchStockResponse);
        assertEquals(expectedBatchStock.getBatchNumber(), batchStockResponse.getBatchNumber());
        assertEquals(expectedBatchStock.getCurrentQuantity(), batchStockResponse.getCurrentQuantity());
        assertEquals(expectedBatchStock.getCurrentTemperature(), batchStockResponse.getCurrentTemperature());
        assertEquals(expectedBatchStock.getCurrentQuantity(), batchStockResponse.getCurrentQuantity());
        assertEquals(expectedBatchStock.getDueDate(), batchStockResponse.getDueDate());
        assertEquals(expectedBatchStock.getInitialQuantity(), batchStockResponse.getInitialQuantity());
        assertEquals(expectedBatchStock.getProductId(), batchStockResponse.getProductId());
        assertEquals(expectedBatchStock.getManufacturingTime(), batchStockResponse.getManufacturingTime());
        assertEquals(expectedBatchStock.getManufacturingDate(), batchStockResponse.getManufacturingDate());
    }

    @Test
    @DisplayName("Inbound Order - Mismatch of product and section category")
    public void update_shouldReturnUnprossebleEntityResponse_whenCategoyOfTheProductDoesNotMatch() throws Exception {
        setupBaseData(2.0f);
        setupBaseDataExtraProduct(CategoryEnum.CONGELADOS, 2.0f);

        BatchStockUpdateRequest expectedBatchStock = BatchStockUpdateRequest.builder().batchNumber(123L)
                .currentQuantity(4).initialQuantity(4).currentTemperature(24.0f).minimumTemperature(19f)
                .manufacturingDate(LocalDate.of(2022, 5, 23))
                .manufacturingTime(LocalDateTime.of(2016, 12, 30, 14, 23, 25)).dueDate(LocalDate.of(2022, 4, 22))
                .productId(3L).build();

        InboundOrderUpdateRequest inboundOrderUpdateRequest = InboundOrderUpdateRequest.builder().agentId(1L)
                .orderDate(LocalDate.of(2020, 3, 3)).orderNumber(1L).batchStock(List.of(expectedBatchStock)).sectionId(1L)
                .build();

        String payload = objectMapper.writeValueAsString(inboundOrderUpdateRequest);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.put("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status()
                        .isUnprocessableEntity())
                .andReturn();

        String responsePaylaod = result.getResponse().getContentAsString();
        ExceptionPayloadResponse payloadResponse = objectMapper.readValue(responsePaylaod, ExceptionPayloadResponse.class);

        assertNotNull(payloadResponse);
        assertEquals("An error occurred during business validation processing", payloadResponse.getTitle());
        assertEquals(422, payloadResponse.getStatusCode());
        assertEquals("Product's category from product 3 is invalid, the expected was FRIOS",
                payloadResponse.getDescription());
    }

    @Test
    @DisplayName("Inbound Order Update - Volume of inbound order exceeds the section capacity")
    public void update_shouldReturnUnprossebleEntityResponse_whenProductsVolumeExceedTheSectionCapacity() throws Exception {
        setupBaseData(42.0f);

        BatchStockUpdateRequest expectedBatchStock = BatchStockUpdateRequest.builder()
                .batchNumber(123L)
                .currentQuantity(4)
                .initialQuantity(4)
                .currentTemperature(24.0f)
                .minimumTemperature(19f)
                .manufacturingDate(LocalDate.of(2022, 5, 23))
                .manufacturingTime(LocalDateTime.of(2016, 12, 30, 14, 23, 25))
                .dueDate(LocalDate.of(2022, 4, 22))
                .productId(2L).build();

        InboundOrderUpdateRequest inboundOrderUpdateRequest = InboundOrderUpdateRequest.builder().agentId(1L)
                .orderDate(LocalDate.of(2020, 3, 3)).orderNumber(1L).batchStock(List.of(expectedBatchStock))
                .sectionId(1L)
                .build();

        String payload = objectMapper.writeValueAsString(inboundOrderUpdateRequest);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.put("/api/v1/fresh-products/inboundorder")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status()
                        .isUnprocessableEntity())
                .andReturn();

        String responsePaylaod = result.getResponse().getContentAsString();
        ExceptionPayloadResponse payloadResponse = objectMapper.readValue(responsePaylaod, ExceptionPayloadResponse.class);

        assertNotNull(payloadResponse);
        assertEquals("An error occurred during business validation processing", payloadResponse.getTitle());
        assertEquals(422, payloadResponse.getStatusCode());

        assertEquals(String.format(
                "Section has capacity of %.2f, which is incompatible with the inbound order total volume which is %.2f", 32.0f,
                42.0f), payloadResponse.getDescription());
    }

    private void setupBaseData(float newPoductVolume) {
        Category managedCategory = setupCategory(CategoryEnum.FRIOS);
        Warehouse managedWarehouse = setupWarehouse();
        Section managedSection = setupSection(managedCategory, managedWarehouse);
        Product managedProduct = setupProduct(managedCategory, 2.0f);

        setupProduct(managedCategory, newPoductVolume);

        BatchStock managedBatchStock = setupBatchStock(managedProduct);

        setupInboundOrder(managedBatchStock, managedWarehouse, managedSection);
    }

    private void setupBaseDataExtraProduct(CategoryEnum categoryEnum, float volume) {
        Category managedCategory = setupCategory(categoryEnum);
        setupProduct(managedCategory, volume);
    }

    private Product setupProduct(Category managedCategory, float volume) {
        Product product = Product.builder().name("Bauru").category(managedCategory).volume(volume)
                .price(BigDecimal.valueOf(32)).build();

        productRepository.save(product);
        return product;
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

    private Warehouse setupWarehouse() {
        Agent agent = Agent.builder().name("007").build();
        Warehouse warehouse = Warehouse.builder().id(1L).name("Galpao do joao").agent(agent).build();
        agent.setWarehouse(warehouse);

        return warehouseRepository.save(warehouse);
    }

    private BatchStock setupBatchStock(Product managedProduct) {
        BatchStock batchStock = BatchStock.builder().batchNumber(123L).currentQuantity(3).initialQuantity(4)
                .currentTemperature(24f)

                .manufacturingDate(LocalDate.of(2020, 4, 22))
                .manufacturingTime(LocalDateTime.of(2016, 10, 30, 14, 23, 25)).dueDate(LocalDate.of(2022, 4, 22))
                .products(managedProduct).build();

        return batchStockRepository.save(batchStock);
    }

    private InboundOrder setupInboundOrder(BatchStock managedBatchStock, Warehouse managedWarehouse,
                                           Section managedSection) {

        InboundOrder inboundOrder = InboundOrder.builder().orderNumber(0L).batchStockList(Set.of(managedBatchStock))
                .section(managedSection).orderDate(LocalDate.of(2020, 3, 4))
                .agent(managedWarehouse.getAgent())
                .build();

        return inboundOrderRepository.save(inboundOrder);
    }

}