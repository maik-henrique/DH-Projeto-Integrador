package br.com.meli.dhprojetointegrador.unit.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.service.ProductService;

@ExtendWith(MockitoExtension.class)

public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    private ProductRepository productRepository = mock(ProductRepository.class);

    BigDecimal price = new BigDecimal(10.00);
    Category categoryFF = Category.builder()
            .id(1L)
            .name(CategoryEnum.FF)
            .build();

    Product productFFa = Product.builder()
            .id(1L)
            .name("maracuja")
            .category(categoryFF)
            .price(price)
            .build();

    Product productFFb = Product.builder()
            .id(1L)
            .name("pera")
            .category(categoryFF)
            .price(price)
            .build();

    List<Product> products = Arrays.asList(productFFa, productFFb);

    /**
     * @Author: Matheus Guerra
     * @Teste: Teste unitario função returnProductsByCategory
     * @Description: valida que a função retorna uma lista de produtos de uma mesma
     *               categoria
     */
    @Test
    @DisplayName("Test US:02 - Item 02a")
    public void returnProductsByCategory_should_return_correct_products() {

        when(productRepository.findByCategory_Name(CategoryEnum.FF)).thenReturn(products);
        List<Product> result = productService.returnProductsByCategory("FF");

        assertThat(result, containsInAnyOrder(productFFa, productFFb));
    }

    /**
     * @Author: Matheus Guerra
     * @Teste: Teste unitario função returnProductsByCategory
     * @Description: valida que a função retorna uma lista vazia caso não haja
     *               produtos de uma categoria cadastrados
     */
    @Test
    @DisplayName("Test US:02 - Item 02b")
    public void returnProductsByCategory_should_return_emptyListofProducts() {

        when(productRepository.findByCategory_Name(CategoryEnum.FF)).thenReturn(products);
        List<Product> result = productService.returnProductsByCategory("FS");

        assertThat(result, IsEmptyCollection.empty());
    }

    @Test
    @DisplayName("Find product filter by names contains when success")
    public void findByName_returnProductFilterByName_WhenSuccess() {
        when(productRepository.findByNameContainingIgnoreCase(anyString()))
                .thenReturn(products);
        List<Product> result = productService.findByName("ang");

        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Find product filter by names contains when success")
    public void findByBrand_returnProductFilterByBrand_WhenSuccess() {
        when(productRepository.findByBrandContainingIgnoreCase(anyString()))
                .thenReturn(products);
        List<Product> result = productService.findByBrand("ara");

        assertFalse(result.isEmpty());
    }

}
