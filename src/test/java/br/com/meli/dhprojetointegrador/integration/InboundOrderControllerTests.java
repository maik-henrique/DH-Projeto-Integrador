package br.com.meli.dhprojetointegrador.integration;

import br.com.meli.dhprojetointegrador.dto.request.BatchStockUpdateRequest;
import br.com.meli.dhprojetointegrador.dto.request.InboundOrderUpdateRequest;
import br.com.meli.dhprojetointegrador.dto.response.BatchStockResponse;
import br.com.meli.dhprojetointegrador.dto.response.InboundOrderResponse;
import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class InboundOrderControllerTests {

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
	public void update_shouldUpdateInboundOrder_when() throws Exception {
		setupBaseData();
		
		BatchStockUpdateRequest expectedBatchStock = BatchStockUpdateRequest.builder().batchNumber(1L)
				.currentQuantity(4).initialQuantity(4).currentTemperature(24.0f).minimumTemperature(19f)
				.manufacturingDate(LocalDate.of(2022, 5, 23))
				.manufacturingTime(LocalDateTime.of(2016, 12, 30, 14, 23, 25)).dueDate(LocalDate.of(2022, 4, 22))
				.productId(1L).build();

		InboundOrderUpdateRequest inboundOrderUpdateRequest = InboundOrderUpdateRequest.builder().agentId(1)
				.orderDate(LocalDate.of(2020, 3, 3)).orderNumber(1).batchStock(List.of(expectedBatchStock)).sectionId(2)
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

	private void setupBaseData() {
		Category managedCategory = setupCategory(CategoryEnum.FRIOS);
		Warehouse managedWarehouse = setupWarehouse();
		Section managedSection = setupSection(managedCategory, managedWarehouse);
		Product managedProduct = setupProduct(managedCategory);
		BatchStock managedBatchStock = setupBatchStock(managedProduct);
		InboundOrder managedInboundOrder = setupInboundOrder(managedBatchStock, managedWarehouse, managedSection);
	}

	private Product setupProduct(Category managedCategory) {
		Product product = Product.builder().id(1L).name("Bauru").category(managedCategory).volume(2.0f)
				.price(BigDecimal.valueOf(32)).build();

		productRepository.save(product);
		return product;
	}

	private Category setupCategory(CategoryEnum categoryEnum) {
		Category category = Category.builder().name(categoryEnum).build();
		return categoryRepository.save(category);
	}

	private Section setupSection(Category managedCategory, Warehouse managedWarehouse) {
		Section section = Section.builder().id(2).category(managedCategory).name("Cold section").capacity(32.0f)
				.warehouse(managedWarehouse).build();

		return sectionRepository.save(section);
	}

	private Warehouse setupWarehouse() {
		Agent agent = Agent.builder().id(1).name("007").build();
		Warehouse warehouse = Warehouse.builder().id(1).name("Galpao do joao").agent(agent).build();
		agent.setWarehouse(warehouse);

		return warehouseRepository.save(warehouse);
	}

	private BatchStock setupBatchStock(Product managedProduct) {
		BatchStock batchStock = BatchStock.builder().batchNumber(1L).currentQuantity(3).initialQuantity(4)
				.currentTemperature(24f)

				.manufacturingDate(LocalDate.of(2020, 4, 22))
				.manufacturingTime(LocalDateTime.of(2016, 10, 30, 14, 23, 25)).dueDate(LocalDate.of(2022, 4, 22))
				.products(managedProduct).build();

		return batchStockRepository.save(batchStock);
	}

	private InboundOrder setupInboundOrder(BatchStock managedBatchStock, Warehouse managedWarehouse,
			Section managedSection) {
		InboundOrder inboundOrder = InboundOrder.builder().orderNumber(123).batchStockList(List.of(managedBatchStock))
				.section(managedSection).orderDate(LocalDate.of(2020, 3, 4)).agent(managedWarehouse.getAgent()).build();

		return inboundOrderRepository.save(inboundOrder);
	}


}
