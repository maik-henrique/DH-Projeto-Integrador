package br.com.meli.dhprojetointegrador.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.meli.dhprojetointegrador.dto.request.FavoriteProductRequest;
import br.com.meli.dhprojetointegrador.entity.Agent;
import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.entity.Seller;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.BuyerRepository;
import br.com.meli.dhprojetointegrador.repository.CategoryRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.repository.SectionRepository;
import br.com.meli.dhprojetointegrador.repository.SellerRepository;
import br.com.meli.dhprojetointegrador.repository.WarehouseRepository;

@WithMockUser(username = "jooj", roles = { "AGENT", "BUYER" })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BuyerControllerTests extends BaseIntegrationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private void setupList() {
        Agent agent = Agent.builder().name("Agente de warehouse").build();
        Warehouse warehouse = Warehouse.builder().name("Centro de distribuição MELIMELI").agent(agent).build();
        agent.setWarehouse(warehouse);
        Warehouse warehouseManaged = warehouseRepository.save(warehouse);

        Category category = Category.builder()
                .name(CategoryEnum.FF)
                .minimumTemperature(-10)
                .maximumTemperature(10)
                .build();

        Category categoryManaged = categoryRepository.save(category);

        Section section = Section.builder().category(categoryManaged).warehouse(warehouseManaged)
                .name("section 1")
                .build();

        sectionRepository.save(section);

        Seller seller = Seller.builder().name("Seller 1").build();

        sellerRepository.save(seller);

        Product productOne = Product.builder()
                .name("Frango")
                .category(category)
                .seller(seller)
                .brand("Seara")
                .volume(1)
                .price(new BigDecimal(10))
                .build();

        Product productTwo = Product.builder()
                .name("Patinho")
                .category(category)
                .seller(seller)
                .brand("JBS")
                .volume(1)
                .price(new BigDecimal(20))
                .build();

        productRepository.save(productOne);

        productRepository.save(productTwo);

        buyerRepository.save(Buyer.builder().name("Pedro").build());

    }

    @Test
    @DisplayName("to add in favorite product when success")
    public void shouldAddProductInFavorite_WhenSuccess() throws Exception {
        setupList();
        FavoriteProductRequest favoriteProductRequest = FavoriteProductRequest.builder().buyerId(1L)
                .productId(1L).build();
        String payload = objectMapper.writeValueAsString(favoriteProductRequest);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/buyer/favorite")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

    }

    @Test
    @DisplayName("to remove favorite product when success")
    public void shouldRemoveProductInFavorite_WhenSuccess() throws Exception {
        setupList();
        FavoriteProductRequest favoriteProductRequest = FavoriteProductRequest.builder().buyerId(1L)
                .productId(1L).build();
        String payload = objectMapper.writeValueAsString(favoriteProductRequest);
        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/buyer/favorite")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/v1/fresh-products/buyer/favorite/{buyerId}", 1)
                        .param("productId", "1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());

    }
}