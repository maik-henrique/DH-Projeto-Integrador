package br.com.meli.dhprojetointegrador.integration;

import br.com.meli.dhprojetointegrador.dto.request.SellerPostRequest;
import br.com.meli.dhprojetointegrador.dto.request.SellerProductPostRequest;
import br.com.meli.dhprojetointegrador.dto.response.TotalPrice;
import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Seller;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.repository.SellerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
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
import java.util.Collections;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class SellerControllerTests extends BaseIntegrationControllerTests {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private  ModelMapper modelMapper;

    // Post
    @Test
    @DisplayName("US:06 - Seller creation test")
    public void registerSeller_shouldReturnStatusCreated_whenSellerIsRegistered() throws Exception {

        Seller seller = Seller.builder()
                .id(1l)
                .name("Carlos")
                .statusActiveAccount(true)
                .products(null)
                .build();

        SellerPostRequest sellerDTO = SellerPostRequest.builder().name("Carlos").build();

        String payload = objectMapper.writeValueAsString(sellerDTO);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/seller/register")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        Seller sellerResult = objectMapper.readValue(responsePayload,Seller.class);

        assertNotNull(responsePayload);
        assertThat(sellerResult, samePropertyValuesAs(seller));
    }

    @Test
    @DisplayName("US:06 - Seller's product creation test")
    public void registerSellerProduct_shouldReturnStatusCreated_whenProductIsRegistered() throws Exception {

        Seller seller = setupSeller("Carlos");

        SellerProductPostRequest productDTO = setupSellerProduct("Tiara");
        Product product = modelMapper.map(productDTO, Product.class);

        String payload = objectMapper.writeValueAsString(productDTO);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/seller/{seller_id}/products", 1)
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();


        String responsePayload = result.getResponse().getContentAsString();
        Seller sellerResult = objectMapper.readValue(responsePayload,Seller.class);

        assertNotNull(responsePayload);
        assert (sellerResult.getName().equals("Carlos"));
        assert (sellerResult.getProducts().iterator().next().getName().equals("Tiara"));
        assert (sellerResult.getProducts().iterator().next().getPrice().equals(BigDecimal.valueOf(12.34)));
        assert (sellerResult.getProducts().iterator().next().getVolume() == 5f);

    }

    // Get
    @Test
    @DisplayName("US:06 - Seller creation test")
    public void findAllSellersProducts_shouldReturnStatusOk_whenSellersProductsExists() throws Exception {

        Seller seller = setupSeller("Roberto");
        Product product = setupProduct("Colar", seller);

        Set<Product> products = Set.of(product);

        String payload = objectMapper.writeValueAsString(products);

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/seller/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();


        String responsePayload = result.getResponse().getContentAsString();

        assertNotNull(responsePayload);
        assertEquals(responsePayload, payload);

    }


    private SellerProductPostRequest setupSellerProduct(String name){

        return SellerProductPostRequest.builder()
                .name(name)
                .price(BigDecimal.valueOf(12.34))
                .volume(5f)
                .build();

    }
    private Seller setupSeller(String name){
        Seller seller = Seller.builder().name(name).statusActiveAccount(true).build();

        sellerRepository.save(seller);
        return seller;
    }

    private Product setupProduct(String name,Seller seller) {
        Product product = Product.builder()
                .name(name)
                .price(BigDecimal.valueOf(12.34))
                .volume(5f)
                .seller(seller)
                .batchStockList(Collections.EMPTY_SET)
                .build();

        productRepository.save(product);
        return product;
    }



}
