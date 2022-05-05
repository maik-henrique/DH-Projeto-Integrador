package br.com.meli.dhprojetointegrador.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.meli.dhprojetointegrador.dto.BatchStockDTO;
import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.BatchStockRepository;
import br.com.meli.dhprojetointegrador.repository.CategoryRepository;
import br.com.meli.dhprojetointegrador.repository.InboundOrderRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.repository.SectionRepository;
import br.com.meli.dhprojetointegrador.repository.WarehouseRepository;



@WithMockUser(username = "jooj", roles = {"AGENT", "ADMIN"})
public class BatchStockControllerTest extends BaseIntegrationControllerTests{
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
    @DisplayName("Retornar os BatchStocks de acordo com os parametros")
    public void shouldReturnAllBatchStockFindByParam() throws Exception {
        setupBaseData();

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/due-date")
                        .param("sectionId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        List<BatchStock> batchStocks = objectMapper.readerForListOf(BatchStockDTO.class).readValue(responsePayload);

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(responsePayload);
        assertFalse(batchStocks.isEmpty());
        assertEquals(batchStocks.size(), 1);

    }

    @Test
    @DisplayName("Retornar batchStock pela categoria")
    public void shouldReturnBatchStockFindByCategory() throws Exception {
        setupBaseData();

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/due-date/")
                        .param("category", "FF")
                        .param("sectionId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        List<BatchStockDTO> batchStocks = objectMapper.readerForListOf(BatchStockDTO.class).readValue(responsePayload);

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(responsePayload);
        assertFalse(batchStocks.isEmpty());
        assertEquals(batchStocks.size(), 1);

    }

    @Test
    @DisplayName("Retornar batchStock pela data de validade")
    public void shouldReturnBatchStockFindByDueDate() throws Exception {
        setupBaseData();

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/due-date/")
                        .param("numberOfDays", "20")
                        .param("sectionId", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        List<BatchStockDTO> batchStocks = objectMapper.readerForListOf(BatchStockDTO.class).readValue(responsePayload);

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(responsePayload);
        assertFalse(batchStocks.isEmpty());
        assertEquals(batchStocks.size(), 1);

    }

    @Test
    @DisplayName("Retornar batchStock aplicando todos os filtros")
    public void shouldReturnBatchStockApplierAllFilters() throws Exception {
        setupBaseData();

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/due-date/")
                        .param("numberOfDays", "20")
                        .param("sectionId", "3")
                        .param("category", "FF"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        List<BatchStockDTO> batchStocks = objectMapper.readerForListOf(BatchStockDTO.class).readValue(responsePayload);

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(responsePayload);
        assertFalse(batchStocks.isEmpty());
        assertEquals(batchStocks.size(), 1);

    }

    private void setupBaseData() {
        Category managedCategory = setupCategory(CategoryEnum.FF);
        Warehouse managedWarehouse = setupWarehouse();
        Section managedSection = setupSection(managedCategory, managedWarehouse);
        Product managedProduct = setupProduct(managedCategory, 2.0f);

        InboundOrder manegeInboundOrder = setupInboundOrder(managedWarehouse, managedSection);
        setupBatchStock(managedProduct, manegeInboundOrder);

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
        Warehouse warehouse = Warehouse.builder().name("Galpao do joao").agent(agent).build();
        agent.setWarehouse(warehouse);

        return warehouseRepository.save(warehouse);
    }

    private BatchStock setupBatchStock(Product managedProduct, InboundOrder manegeInboundOrder) {
        LocalDate TODAY_MORE_FIFTEEN = LocalDate.now().plusDays(15);
        LocalDate TODAY_MORE_THIRTEEN = LocalDate.now().plusDays(30);
        BatchStock batchStock = BatchStock.builder()
                .batchNumber(123L)
                .currentQuantity(3)
                .initialQuantity(4)
                .currentTemperature(24f)
                .manufacturingDate(LocalDate.of(2020, 4, 22))
                .manufacturingTime(LocalDateTime.of(2016, 10, 30, 14, 23, 25))
                .dueDate(TODAY_MORE_FIFTEEN)
                .inboundOrder(manegeInboundOrder)
                .products(managedProduct)
                .build();

        batchStockRepository.save(batchStock);

        Category category = setupCategory(CategoryEnum.FS);
        Warehouse warehouse = setupWarehouse();
        Section section = setupSection(category, warehouse);
        Product product = setupProduct(category, 2.0f);
        InboundOrder inboundOrder = setupInboundOrder(warehouse, section);

        Category otherCategory = setupCategory(CategoryEnum.FF);
        Section otherSection = setupSection(otherCategory, warehouse);
        InboundOrder otherInboundOrder = setupInboundOrder(warehouse, otherSection);

        batchStock = BatchStock.builder()
                .batchNumber(124L)
                .currentQuantity(3)
                .initialQuantity(4)
                .currentTemperature(24f)
                .manufacturingDate(LocalDate.of(2020, 4, 22))
                .manufacturingTime(LocalDateTime.of(2016, 10, 30, 14, 23, 25))
                .dueDate(TODAY_MORE_THIRTEEN)
                .inboundOrder(inboundOrder)
                .products(product)
                .build();

        batchStockRepository.save(batchStock);

        batchStock = BatchStock.builder()
                .batchNumber(125L)
                .currentQuantity(3)
                .initialQuantity(4)
                .currentTemperature(24f)
                .manufacturingDate(LocalDate.of(2020, 4, 22))
                .manufacturingTime(LocalDateTime.of(2016, 10, 30, 14, 23, 25))
                .dueDate(TODAY_MORE_THIRTEEN)
                .inboundOrder(otherInboundOrder)
                .products(managedProduct)
                .build();

        return batchStockRepository.save(batchStock);
    }

    private InboundOrder setupInboundOrder(Warehouse managedWarehouse,
            Section managedSection) {

        InboundOrder inboundOrder = InboundOrder.builder()
                .section(managedSection)
                .orderDate(LocalDate.of(2020, 3, 4))
                .agent(managedWarehouse.getAgent())
                .build();

        return inboundOrderRepository.save(inboundOrder);
    }

}
