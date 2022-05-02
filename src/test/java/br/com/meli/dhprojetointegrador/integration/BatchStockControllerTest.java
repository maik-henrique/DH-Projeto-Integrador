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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.meli.dhprojetointegrador.dto.BatchStockDTO;
import br.com.meli.dhprojetointegrador.dto.request.BatchStockUpdateRequest;
import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.AgentRepository;
import br.com.meli.dhprojetointegrador.repository.BatchStockRepository;
import br.com.meli.dhprojetointegrador.repository.CategoryRepository;
import br.com.meli.dhprojetointegrador.repository.InboundOrderRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.repository.SectionRepository;
import br.com.meli.dhprojetointegrador.repository.WarehouseRepository;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class BatchStockControllerTest {
    @Autowired
    private MockMvc mock;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private AgentRepository agentRepository;

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

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/due-date").param("sectionId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        List<BatchStock> batchStocks = objectMapper.readerForListOf(BatchStockDTO.class).readValue(responsePayload);

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(responsePayload);
        assertFalse(batchStocks.isEmpty());

        System.out.println(batchStocks + "   AAAAAA \n\n\n\n");

    }
    @Test
    @DisplayName("Retornar batchstock pela categoria")
    public void shouldReturnBatchstockFindByCategory() throws Exception {
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

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/due-date/").param("category", "FF").param("sectionId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        List<BatchStockDTO> batchStocks = objectMapper.readerForListOf(BatchStockDTO.class).readValue(responsePayload);

        System.out.println(batchStocks.get(0).getCategory_id());

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(responsePayload);


    }

    private void setupBaseData(float newPoductVolume) {
        Category managedCategory = setupCategory(CategoryEnum.FF);
        Warehouse managedWarehouse = setupWarehouse();
        Section managedSection = setupSection(managedCategory, managedWarehouse);
        Product managedProduct = setupProduct(managedCategory, 2.0f);

        setupProduct(managedCategory, newPoductVolume);

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
                .warehouse(managedWarehouse).id(1L).build();

        return sectionRepository.save(section);
    }

    private Warehouse setupWarehouse() {
        Agent agent = Agent.builder().name("007").build();
        Warehouse warehouse = Warehouse.builder().id(1L).name("Galpao do joao").agent(agent).build();
        agent.setWarehouse(warehouse);

        return warehouseRepository.save(warehouse);
    }

    private BatchStock setupBatchStock(Product managedProduct, InboundOrder inboundOrder) {
        LocalDate TODAY = LocalDate.now().plusDays(15);
        BatchStock batchStock = BatchStock.builder()
                .batchNumber(123L)
                .currentQuantity(3)
                .initialQuantity(4)
                .currentTemperature(24f)
                .manufacturingDate(LocalDate.of(2020, 4, 22))
                .manufacturingTime(LocalDateTime.of(2016, 10, 30, 14, 23, 25))
                .dueDate(TODAY)
                .inboundOrder(inboundOrder)
                .products(managedProduct)
                .build();

        return batchStockRepository.save(batchStock);
    }

    private InboundOrder setupInboundOrder(Warehouse managedWarehouse,
            Section managedSection) {

        InboundOrder inboundOrder = InboundOrder.builder()
                .orderNumber(0L)
                .section(managedSection)
                .orderDate(LocalDate.of(2020, 3, 4))
                .agent(managedWarehouse.getAgent())
                .build();

        return inboundOrderRepository.save(inboundOrder);
    }

}
