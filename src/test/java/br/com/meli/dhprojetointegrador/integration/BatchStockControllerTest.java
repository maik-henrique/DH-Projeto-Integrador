package br.com.meli.dhprojetointegrador.integration;

import br.com.meli.dhprojetointegrador.dto.BatchStockDTO;
import br.com.meli.dhprojetointegrador.dto.request.BatchStockUpdateRequest;
import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import static org.junit.jupiter.api.Assertions.*;

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
                .
                .productId(2L).build();

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/due-date").param("sectionId", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        List<BatchStock> batchStocks = objectMapper.readerForListOf(BatchStockDTO.class).readValue(responsePayload);

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(responsePayload);
        assertTrue(batchStocks.isEmpty());

        System.out.println(batchStocks);

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

    private BatchStock setupBatchStock(Product managedProduct) {
        BatchStock batchStock = BatchStock.builder().batchNumber(123L).currentQuantity(3).initialQuantity(4)
                .currentTemperature(24f)
                .manufacturingDate(LocalDate.of(2020, 4, 22))
                .manufacturingTime(LocalDateTime.of(2016, 10, 30, 14, 23, 25)).dueDate(LocalDate.of(2022, 4, 22))
                .products(managedProduct)

                .build();

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
