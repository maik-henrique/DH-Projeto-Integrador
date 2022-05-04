package br.com.meli.dhprojetointegrador.integration;

import br.com.meli.dhprojetointegrador.dto.request.NewProductRequest;
import br.com.meli.dhprojetointegrador.dto.response.FullProductResponse;
import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Seller;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.CategoryRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.repository.SellerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SellerControllerTest extends  BaseIntegrationControllerTests{

    @Autowired
    private MockMvc mock;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("CreateNewProduct  - when receiving the right input function works properly")
    public void correct_functioning_of_createNewProduct() throws Exception {

        Category categoryFS = setupCategory(CategoryEnum.FS);
        Seller jorge = setupSeller("Jorge");

        NewProductRequest newProductRequest = NewProductRequest.builder()
                .name("sorvete")
                .sellerId(jorge.getId())
                .price(BigDecimal.valueOf(17.50))
                .categoryId(categoryFS.getId())
                .volume(0.25F)
                .build();

        String payload = objectMapper.writeValueAsString(newProductRequest);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/seller/products")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        FullProductResponse response = objectMapper.readValue(responsePayload, FullProductResponse.class);

        assertNotNull(responsePayload);
        assertEquals(response.getSeller_id(), jorge.getId());
        assertEquals(response.getCategory_id(), categoryFS.getId());
        assertEquals(response.getName(), newProductRequest.getName());
        assertEquals(response.getVolume(), newProductRequest.getVolume());
        assertEquals(response.getPrice(), newProductRequest.getPrice());
    }

    @Test
    @DisplayName("CreateNewProduct  - when receiving the wrong seller id, returns the correct error")
    public void correct_error_of_createNewProduct_with_wrong_seller() throws Exception {

        Category categoryFS = setupCategory(CategoryEnum.FS);

        NewProductRequest newProductRequest = NewProductRequest.builder()
                .name("sorvete")
                .sellerId(25L)
                .price(BigDecimal.valueOf(17.50))
                .categoryId(categoryFS.getId())
                .volume(0.25F)
                .build();

        String payload = objectMapper.writeValueAsString(newProductRequest);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/seller/products")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

        assertEquals(result.getResolvedException().getMessage(), "Seller not found");
    }

    @Test
    @DisplayName("CreateNewProduct  - when receiving the wrong category, returns the correct error")
    public void correct_error_of_createNewProduct_with_wrong_category() throws Exception {
        Seller jorge = setupSeller("Jorge");

        NewProductRequest newProductRequest = NewProductRequest.builder()
                .name("sorvete")
                .sellerId(jorge.getId())
                .price(BigDecimal.valueOf(17.50))
                .categoryId(350L)
                .volume(0.25F)
                .build();

        String payload = objectMapper.writeValueAsString(newProductRequest);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/seller/products")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

        assertEquals(result.getResolvedException().getMessage(), "Category not found");
    }

    @Test
    @DisplayName("GetProducts - when receiving sellerId and name as input function works properly")
    public void correct_functioning_of_getProducts_with_sellerId_and_name() throws Exception {

        Category categoryFS = setupCategory(CategoryEnum.FS);
        Seller jorge = setupSeller("Jorge");
        Product product1 = setupProduct("banana", BigDecimal.valueOf(2.31), 0.15F, categoryFS, jorge);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/seller/products")
                        .param("sellerId", jorge.getId().toString())
                        .param("name",product1.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        List<FullProductResponse> response = objectMapper.readerForListOf(FullProductResponse.class).readValue(responsePayload);

        assertNotNull(responsePayload);
        assertEquals(response.get(0).getSeller_id(), jorge.getId());
        assertEquals(response.get(0).getCategory_id(), categoryFS.getId());
        assertEquals(response.get(0).getName(), product1.getName());
        assertEquals(response.get(0).getVolume(), product1.getVolume());
        assertEquals(response.get(0).getPrice(), product1.getPrice());
    }

    @Test
    @DisplayName("GetProducts - when receiving only sellerId as input function works properly")
    public void correct_functioning_of_getProducts_with_sellerId() throws Exception {

        Category categoryFS = setupCategory(CategoryEnum.FS);
        Seller jorge = setupSeller("Jorge");
        setupProduct("banana", BigDecimal.valueOf(2.31), 0.15F, categoryFS, jorge);
        setupProduct("maca", BigDecimal.valueOf(1.48), 0.15F, categoryFS, jorge);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/seller/products")
                        .param("sellerId", jorge.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        List<FullProductResponse> response = objectMapper.readerForListOf(FullProductResponse.class).readValue(responsePayload);

        assertNotNull(responsePayload);
        assertEquals(response.size(), 2);
    }

    @Test
    @DisplayName("updateProduct - when receiving correct input function works properly")
    public void correct_functioning_of_updateProduct() throws Exception {

        Category categoryFS = setupCategory(CategoryEnum.FS);
        Seller jorge = setupSeller("Jorge");
        Product product = setupProduct("banana", BigDecimal.valueOf(2.31), 0.15F, categoryFS, jorge);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.patch("/api/v1/fresh-products/seller/products")
                        .param("productId", product.getId().toString())
                        .param("name", "maca")
                        .param("price", "1.53"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();
        FullProductResponse response = objectMapper.readValue(responsePayload, FullProductResponse.class);

        assertNotNull(responsePayload);
        assertEquals(response.getName(), "maca");
        assertEquals(response.getPrice(), BigDecimal.valueOf(Float.parseFloat("1.53")));
    }

    private Category setupCategory(CategoryEnum categoryEnum) {
        Category category = Category.builder().name(categoryEnum).build();
        return categoryRepository.save(category);
    }

    private Seller setupSeller(String name) {
        Seller seller = Seller.builder().name(name).build();
        return sellerRepository.save(seller);
    }

    private Product setupProduct(String name, BigDecimal price, Float volume, Category category, Seller seller) {
        Product product = Product.builder()
                .name(name)
                .price(price)
                .volume(volume)
                .category(category)
                .seller(seller)
                .build();
        return productRepository.save(product);
    }
}
