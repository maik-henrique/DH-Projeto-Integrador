package br.com.meli.dhprojetointegrador.unit.service;

import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.BuyerRepository;
import br.com.meli.dhprojetointegrador.repository.CategoryRepository;
import br.com.meli.dhprojetointegrador.repository.InboundOrderRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.service.InboundOrderService;
import br.com.meli.dhprojetointegrador.service.ProductService;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;


import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;


    private ProductRepository productRepository = mock(ProductRepository.class);;

    @Mock
    private CategoryRepository categoryRepository;

    Category categoryFF = Category.builder()
            .id(1L)
            .name(CategoryEnum.FF)
            .build();

    Product productFFa = Product.builder()
            .id(1L)
            .name("maracuja")
            .category(categoryFF)
            .build();

    Product productFFb = Product.builder()
            .id(1L)
            .name("pera")
            .category(categoryFF)
            .build();

    Product productWithoutCategory = Product.builder()
            .id(1L)
            .name("queijo")
            .build();

    List<Product> products = Arrays.asList(productFFa, productFFb);

    /**
     * @Author: Matheus Guerra
     * @Teste: Teste unitario função returnProductsByCategory
     * @Description: valida que a função retorna uma lista de produtos de uma mesma categoria
     */
    @Test
    @DisplayName("Test US:02 - Item 02a")
    public void returnProductsByCategory_should_return_correct_products(){

        when(productRepository.findByCategory_Name(CategoryEnum.FF)).thenReturn(products);
        List<Product> result = productService.returnProductsByCategory("FF");

        // Uma possibilidade
        //assertThat(result, hasItems(productFFa, productFFb));

        assertThat(result, containsInAnyOrder(productFFa, productFFb));
    }

    /**
     * @Author: Matheus Guerra
     * @Teste: Teste unitario função returnProductsByCategory
     * @Description: valida que a função retorna uma lista vazia caso não haja produtos de uma categoria cadastrados
     */
    @Test
    @DisplayName("Test US:02 - Item 02b")
    public void returnProductsByCategory_should_return_emptyListofProducts(){

        when(productRepository.findByCategory_Name(CategoryEnum.FF)).thenReturn(products);
        List<Product> result = productService.returnProductsByCategory("FS");

        assertThat(result, IsEmptyCollection.empty());
    }



}
