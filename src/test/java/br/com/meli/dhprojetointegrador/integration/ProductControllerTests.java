package br.com.meli.dhprojetointegrador.integration;


import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.DisplayName;
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


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {


    @Autowired
    private MockMvc mock;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * @Author: Matheus Guerra
     * @Teste: Teste de integração do endpoint "/api/v1/fresh-products/list"
     * @Description: valida o funcionamento correto do endpoint:
     *              Retorno de uma lista de produtos filtrados por categoria;
     *              Retorno de uma lista vazia caso não haja produtos da categoria escolhida;
     */
    @Test
    @DisplayName("US:02 - Item 02")
    public void correct_functioning_of_returnAllProductsByCategory() throws Exception {

        Category categoryFF = setupCategory(CategoryEnum.FF);
        Category categoryFS = setupCategory(CategoryEnum.FS);

        Product productFFa = setupProduct("morango", categoryFF);
        Product productFFb = setupProduct("laranja", categoryFF);

        Product productFSa = setupProduct("pera", categoryFS);

        setupProduct("objeto", null);

        List<Product> produtosFF = Arrays.asList(productFFa,productFFb);
        List<Product> produtosFS = Arrays.asList(productFSa);

        String payloadFF = objectMapper.writeValueAsString(produtosFF);
        String payloadFS = objectMapper.writeValueAsString(produtosFS);


        MvcResult resultFF = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/list").param("category","FF"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        MvcResult resultFS = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/list").param("category","FS"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        MvcResult resultRF = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/list").param("category","RF"))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

        String responsePayloadFF = resultFF.getResponse().getContentAsString();
        String responsePayloadFS = resultFS.getResponse().getContentAsString();
        String responsePayloadRF = resultRF.getResponse().getContentAsString();

        assertNotNull(responsePayloadFF);
        assertNotNull(responsePayloadFS);
        assertEquals("",responsePayloadRF);

        assertEquals(payloadFF, responsePayloadFF);
        assertEquals(payloadFS, responsePayloadFS);

    }

    private Category setupCategory(CategoryEnum categoryEnum) {
        Category category = Category.builder().name(categoryEnum).sections(Collections.EMPTY_SET).build();
        return categoryRepository.save(category);
    }

    private Product setupProduct(String name, Category category) {
        Product product = Product.builder().name(name).category(category).batchStockList(Collections.EMPTY_SET).build();

        productRepository.save(product);
        return product;
    }
}
