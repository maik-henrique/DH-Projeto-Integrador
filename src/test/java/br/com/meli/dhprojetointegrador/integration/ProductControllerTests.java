package br.com.meli.dhprojetointegrador.integration;


import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.math.BigDecimal;

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


    @Test
    @DisplayName("US:02 - Item 02 a - Test se ta funcionando")
    public void correct_functioning_of_returnAllProductsByCategory() throws Exception {

        Category categoryFF = setupCategory(CategoryEnum.FF);
        Category categoryFS = setupCategory(CategoryEnum.FS);

        Product productFFa = setupProduct("morango", categoryFF);
        Product productFFb = setupProduct("laranja", categoryFF);

        Product productFSa = setupProduct("pera", categoryFS);

        Product productWithoutCategory = setupProduct("objeto", null);



        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/list").param("category","FF"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();

        System.out.println(responsePayload.toString());

    }

    @Test
    @DisplayName("US:02 - Item 02 a - Test se ta funcionando")
    public void incorrect_functioning_of_returnAllProductsByCategory() throws Exception {

        Category categoryFF = setupCategory(CategoryEnum.FF);
        Category categoryFS = setupCategory(CategoryEnum.FS);

        Product productFFa = setupProduct("morango", categoryFF);
        Product productFFb = setupProduct("laranja", categoryFF);

        Product productFSa = setupProduct("pera", categoryFS);

        Product productWithoutCategory = setupProduct("objeto", null);



        MvcResult result = mock
                .perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/list").param("category","RF"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String responsePayload = result.getResponse().getContentAsString();

        System.out.println(responsePayload.toString());

    }



    private void setup() {

    }


    private Category setupCategory(CategoryEnum categoryEnum) {
        Category category = Category.builder().name(categoryEnum).build();
        return categoryRepository.save(category);
    }

    private Product setupProduct(String name, Category category) {
        Product product = Product.builder().name(name).category(category).build();

        productRepository.save(product);
        return product;
    }
}
