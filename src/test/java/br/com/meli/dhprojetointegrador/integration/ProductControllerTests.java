package br.com.meli.dhprojetointegrador.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.meli.dhprojetointegrador.dto.BatchStockDTO;
import br.com.meli.dhprojetointegrador.dto.request.InboundPostRequestBody;
import br.com.meli.dhprojetointegrador.dto.response.freshproducts.FreshProductsQueriedResponse;
import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.entity.Seller;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.AgentRepository;
import br.com.meli.dhprojetointegrador.repository.BatchStockRepository;
import br.com.meli.dhprojetointegrador.repository.CategoryRepository;
import br.com.meli.dhprojetointegrador.repository.InboundOrderRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.repository.SectionRepository;
import br.com.meli.dhprojetointegrador.repository.SellerRepository;
import br.com.meli.dhprojetointegrador.repository.WarehouseRepository;


public class ProductControllerTests extends BaseIntegrationControllerTests {

	@Autowired
	private MockMvc mockMvc;

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
	private SellerRepository sellerRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("Products filter - Product wasn't found witht the field specifications")
	public void findAllProducts_shouldReturnStatusNotFound_whenProductIsNotRegistered() throws Exception {
		mockMvc.perform(get("/api/v1/fresh-products/list").param("productId", "1")).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Products filter - Valid data was found and properly returned")
	public void findAllProducts_shouldReturnStatusOkAndValidResponseBody_whenProductIsRegistered() throws Exception {
		
		setupBaseData();

		List<BatchStock> batchStocks = batchStockRepository.findAll();

		MvcResult result = mockMvc.perform(get("/api/v1/fresh-products/list").param("productId", "1")).andExpect(status().isOk())
		.andReturn();
		
		String responsePayload = result.getResponse().getContentAsString();
		
		FreshProductsQueriedResponse response = objectMapper.readValue(responsePayload, FreshProductsQueriedResponse.class);
		
		assertEquals(2, response.getBatchStock().size());
		assertEquals("1", response.getProductId());
		assertEquals("1", response.getWarehouseCode());
	}

	public void setupBaseData() throws Exception {
		Agent agent = Agent.builder().name("Agente de warehouse").password("123").build();
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

		BatchStockDTO frangoBatchStock = BatchStockDTO.builder().batchNumber(123L).currentQuantity(32)
				.currentTemperature(17).dueDate(LocalDate.of(2022, 4, 22)).initialQuantity(42).minimumTemperature(17)
				.product_id(frangoManaged.getId()).manufacturingDate(LocalDate.of(2022, 3, 22))
				.manufacturingTime(LocalDateTime.of(2022, 3, 22, 3, 22, 1)).build();

		BatchStockDTO carneBatchStock = BatchStockDTO.builder().batchNumber(124L).currentQuantity(32)
				.currentTemperature(17).dueDate(LocalDate.of(2022, 4, 22)).initialQuantity(42).minimumTemperature(17)
				.product_id(carneManaged.getId()).manufacturingDate(LocalDate.of(2022, 3, 22))
				.manufacturingTime(LocalDateTime.of(2022, 3, 22, 3, 22, 1)).build();

		InboundPostRequestBody postRequest = InboundPostRequestBody.builder().agentId(1L)
				.orderDate(Date.valueOf(LocalDate.of(2022, 3, 3)))
				.batchStock(List.of(frangoBatchStock, carneBatchStock)).sectionId(1L).build();

		String payload = objectMapper.writeValueAsString(postRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/inboundorder")
				.contentType(MediaType.APPLICATION_JSON).content(payload))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
		
		frangoBatchStock.setBatchNumber(125L);
		postRequest.setBatchStock(List.of(frangoBatchStock));
			
		payload = objectMapper.writeValueAsString(postRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/inboundorder")
				.contentType(MediaType.APPLICATION_JSON).content(payload))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
		
	}

}
