package br.com.meli.dhprojetointegrador.integration;

import br.com.meli.dhprojetointegrador.dto.response.ExceptionPayloadResponse;
import br.com.meli.dhprojetointegrador.dto.response.InboundOrderResponse;
import br.com.meli.dhprojetointegrador.dto.response.ProductResponseDto;
import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.CategoryRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Author: Mariana Galdino
     * Test: Retorna todos os produtos
     * Description: Serviço responsavel por retornar todos os produtos presentes na
     * aplicação;
     *
     * @return lista de produtos
     */

    @Test
    @DisplayName("Product Controller Integration - Return all registered products")
    public void shouldReturnAllRegisteredProducts() throws Exception {

        Product product1 = setupProduct("frango",  20f);
        Product product2 = setupProduct("sorvete",  10f);



        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();


        String responsePayload = result.getResponse().getContentAsString();
        List<Product> products = objectMapper.readerForListOf(Product.class).readValue(responsePayload);

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertNotNull(responsePayload);
        assertFalse(products.isEmpty());

    }

    /**
     * Author: Mariana Galdino
     * Test: Retorna lista de produtos vazia e status 404 not found
     * Description: Serviço responsavel por retornar uma lista vazia de produtos
     * @return lista vazia de produtos
     */
    @Test
    @DisplayName("Product Controller Integratio - Return 404 not found")
    public void shouldReturnHttpNotFound_whenNoOneProduct() throws Exception {

        Product product1 = Product.builder().name("alface").price(new BigDecimal(12)).volume(5f).build();

        String payload = objectMapper.writeValueAsString(product1);

        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products")
                .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();


        String responsePayload = result.getResponse().getContentAsString();


        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertTrue(responsePayload.isEmpty());

    }

    private Product setupProduct(String name, Float volume) {
        Product product = Product.builder().name(name).volume(volume).build();

        productRepository.save(product);
        return product;
    }

}
