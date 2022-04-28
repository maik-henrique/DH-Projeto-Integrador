package br.com.meli.dhprojetointegrador.integration;

import br.com.meli.dhprojetointegrador.dto.response.ExceptionPayloadDTO;
import br.com.meli.dhprojetointegrador.dto.response.ProductByWarehouseResponse;
import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@SpringBootTest()
@AutoConfigureMockMvc
public class GetProductByWarehouseIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BatchStockRepository batchStockRepository;

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

    private void setup() {
        Warehouse warehouse = setupWarehouse();
        Category categoryFS = setupCategory(CategoryEnum.FS);
        Section section = setupSection(categoryFS, warehouse);

        Product product1 = setupProduct(1L, "Banana", new BigDecimal("2.50"));
        InboundOrder inboundOrder = setupInboundOrder(1L, warehouse, section);

        BatchStock batchStock1 = setupBatchStock(1L, product1, inboundOrder);
        BatchStock batchStock2 = setupBatchStock(2L, product1, inboundOrder);


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

    private BatchStock setupBatchStock(Long batchNumber, Product managedProduct, InboundOrder inboundOrder) {
        BatchStock batchStock = BatchStock.builder()
                .batchNumber(batchNumber)
                .currentQuantity(15).initialQuantity(15)
                .currentTemperature(24f)
                .inboundOrder(inboundOrder)
                .manufacturingDate(LocalDate.of(2020, 4, 22))
                .manufacturingTime(LocalDateTime.of(2016, 10, 30, 14, 23, 25)).dueDate(LocalDate.of(2022, 4, 22))
                .products(managedProduct).build();

        return batchStockRepository.save(batchStock);
    }

    private InboundOrder setupInboundOrder(Long orderNumber, Warehouse managedWarehouse,
                                           Section managedSection) {

        InboundOrder inboundOrder = InboundOrder.builder()
                .orderNumber(orderNumber)
                .section(managedSection).orderDate(LocalDate.of(2020, 3, 4))
                .agent(managedWarehouse.getAgent())
                .build();

        return inboundOrderRepository.save(inboundOrder);
    }

    /**
     * @Author: Bruno
     * @Teste: correct_functioning_of_returnTotalProductsByWarehouse
     * @Description: Teste integrador endpoint /api/v1/fresh-products/warehouse/{id}
     */
    @Test
    @DisplayName("Get product by warehouses - when receiving the right input function works properly")
    public void correct_functioning_of_returnTotalProductsByWarehouse() throws Exception {
        setup();

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/warehouse/1"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        ProductByWarehouseResponse productByWarehouse = objectMapper.readValue(responsePayload, ProductByWarehouseResponse.class);

        assertNotNull(responsePayload);
        assertEquals(productByWarehouse.getWarehouses().isEmpty(), false);
        assertEquals(productByWarehouse.getWarehouses().get(0).getTotalQuantity(), 30);
        assertEquals(productByWarehouse.getWarehouses().get(0).getWarehouseCode(), 1L);
    }

    /**
     * @Author: Bruno
     * @Teste: function_returnTotalProductsByWarehouse_should_trow_ProductNotFound
     * @Description: Teste integrador endpoint /api/v1/fresh-products/warehouse/{id}
     */
    @Test
    @DisplayName("Get product by warehouses - when receiving inexistent id returns correct error")
    public void function_returnTotalProductsByWarehouse_should_trow_ProductNotFound() throws Exception {
        setup();

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/warehouse/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        ExceptionPayloadDTO exceptionResponse = objectMapper.readValue(responsePayload, ExceptionPayloadDTO.class);

        assertNotNull(responsePayload);
        assertEquals(exceptionResponse.getTitle(), "Product Not Found");
    }
}
