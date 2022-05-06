package br.com.meli.dhprojetointegrador.integration;

import br.com.meli.dhprojetointegrador.dto.request.ProductRefactor;
import br.com.meli.dhprojetointegrador.dto.request.PurchaseOrderRequest;
import br.com.meli.dhprojetointegrador.dto.response.*;
import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import static org.junit.jupiter.api.Assertions.*;
 

@ContextConfiguration
@WithMockUser(username = "jooj", roles = {"BUYER"})
public class OrderControllerTests extends BaseIntegrationControllerTests {

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

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Test
    @DisplayName("Create Purchase Order - when receiving the right input function works properly")
    public void correct_functioning_of_PurchaseOrderProductRegistration() throws Exception {
        setup();
        LocalDate date = LocalDate.of(2021, 04, 25);

        ProductRefactor product1 = ProductRefactor.builder().productId(1L).quantity(5).build();
        ProductRefactor product2 = ProductRefactor.builder().productId(2L).quantity(5).build();

        PurchaseOrderRequest purchaseOrderRequest = PurchaseOrderRequest.builder()
                .date(date)
                .orderStatus(StatusEnum.ABERTO)
                .products(List.of(product1, product2))
                .buyerId(1L)
                .build();

        String payload = objectMapper.writeValueAsString(purchaseOrderRequest);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/orders/")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        TotalPriceResponse totalPriceResponse = objectMapper.readValue(responsePayload, TotalPriceResponse.class);

        assertNotNull(responsePayload);
        assertEquals(totalPriceResponse.getTotalPrice(), 35.00);
    }

    @Test
    @DisplayName("Create Purchase Order - throws correct error when receive a user that doesn't exist")
    public void function_PurchaseOrderProductRegistration_should_trow_BuyerNotFoundException() throws Exception {
        setup();
        LocalDate date = LocalDate.of(2021, 04, 25);

        ProductRefactor product1 = ProductRefactor.builder().productId(1L).quantity(5).build();
        ProductRefactor product2 = ProductRefactor.builder().productId(2L).quantity(5).build();

        PurchaseOrderRequest purchaseOrderRequest = PurchaseOrderRequest.builder()
                .date(date)
                .orderStatus(StatusEnum.ABERTO)
                .products(List.of(product1, product2))
                .buyerId(10L)
                .build();

        String payload = objectMapper.writeValueAsString(purchaseOrderRequest);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/orders/")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        ExceptionPayloadResponse exceptionResponse = objectMapper.readValue(responsePayload, ExceptionPayloadResponse.class);

        assertNotNull(responsePayload);
        assertEquals(exceptionResponse.getTitle(), "Buyer Not Found");
    }

    @Test
    @DisplayName("Create Purchase Order - throws correct error when receive a product that doesn't exist")
    public void function_PurchaseOrderProductRegistration_should_trow_ProductNotFoundException() throws Exception {
        setup();
        LocalDate date = LocalDate.of(2021, 04, 25);

        ProductRefactor product1 = ProductRefactor.builder().productId(6L).quantity(5).build();
        ProductRefactor product2 = ProductRefactor.builder().productId(2L).quantity(5).build();

        PurchaseOrderRequest purchaseOrderRequest = PurchaseOrderRequest.builder()
                .date(date)
                .orderStatus(StatusEnum.ABERTO)
                .products(List.of(product1, product2))
                .buyerId(1L)
                .build();

        String payload = objectMapper.writeValueAsString(purchaseOrderRequest);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/orders/")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        ExceptionPayloadResponse exceptionResponse = objectMapper.readValue(responsePayload, ExceptionPayloadResponse.class);

        assertNotNull(responsePayload);
        assertEquals(exceptionResponse.getTitle(), "Product Not Found");
    }

    @Test
    @DisplayName("Create Purchase Order - throws correct error when receive a quantity larger than in stock")
    public void function_PurchaseOrderProductRegistration_should_throw_NotEnoughProductsException() throws Exception {
        setup();
        LocalDate date = LocalDate.of(2021, 04, 25);

        ProductRefactor product1 = ProductRefactor.builder().productId(1L).quantity(25).build();
        ProductRefactor product2 = ProductRefactor.builder().productId(2L).quantity(5).build();

        PurchaseOrderRequest purchaseOrderRequest = PurchaseOrderRequest.builder()
                .orderStatus(StatusEnum.ABERTO)
                .date(date)
                .products(List.of(product1, product2))
                .buyerId(1L)
                .build();

        String payload = objectMapper.writeValueAsString(purchaseOrderRequest);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/orders/")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        ExceptionPayloadResponse exceptionResponse = objectMapper.readValue(responsePayload, ExceptionPayloadResponse.class);

        assertNotNull(responsePayload);
        assertEquals(exceptionResponse.getTitle(), "Not Enough Products");
    }

    /**
     * Author: Micaela Alves
     * Test: Teste de integração do endpoint "/api/v1/fresh-products/orders"
     * Description: verifica se o endpoint retorna a lista de produtos de uma determinada order
     *
     */
    @Test
    @DisplayName("Show Products in Purchase Order - when receiving valid id return all products")
    public void should_return_all_products_in_purchase_order() throws Exception{
        Product product1 = setupProduct(1L, "Banana", new BigDecimal("2.50"));
        Buyer buyer = setupBuyer();
        PurchaseOrder purchaseOrder = setupPurchaseOrder(buyer);
        purchaseOrderRepository.save(purchaseOrder);
        CartProduct cartProduct = setupCartProduct(product1, purchaseOrder);
        cartProductRepository.save(cartProduct);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/orders/")
                .param("idOrder", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        List<CartProductResponse> products = objectMapper.readerForListOf(CartProductResponse.class).readValue(response);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(response);
        assertFalse(products.isEmpty());

    }

    /**
     * Author: Micaela Alves
     * Test: Teste de integração do endpoint "/api/v1/fresh-products/orders"
     * Description: verifica se o endpoint NotFound quando o id informado for invalido
     *
     */
    @Test
    @DisplayName("Return 404 not found - when receiving invalid order id")
    public void should_return_not_found_when_invalid_orderId() throws Exception{
        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/orders/")
                        .param("idOrder", "1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());

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

    private CartProduct setupCartProduct(Product prod, PurchaseOrder order) {
        return CartProduct.builder()
                .product(prod)
                .purchaseOrder(order)
                .quantity(5)
                .build();
    }

    private PurchaseOrder setupPurchaseOrder(Buyer buyer){
        return PurchaseOrder.builder()
                .id(1L)
                .buyer(buyer)
                .status(StatusEnum.ABERTO)
                .date(LocalDate.of(2020, 4, 22))
                .build();
    }


}
