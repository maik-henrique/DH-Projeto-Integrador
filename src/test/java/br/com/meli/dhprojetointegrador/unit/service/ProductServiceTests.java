package br.com.meli.dhprojetointegrador.unit.service;

import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Seller;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.service.ProductService;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    private ProductRepository productRepository = mock(ProductRepository.class);;

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

    List<Product> products = Arrays.asList(productFFa, productFFb);

    Product product = Product.builder()
            .id(1l)
            .name("Coroa")
            .statusProduct(true)
            .build();

    Seller seller = Seller.builder()
            .id(1l)
            .name("Vendedor")
            .build();

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

    /**
     * @Author: Matheus Guerra
     * @Teste: Teste unitario função saveProductWithSeller
     * @Description: valida que a função retorna o seller cadastrado
     */
    @Test
    @DisplayName("Test US:06 - saveProductWithSeller - Correct functioning")
    public void saveProductWithSeller_should_return_correct_seller(){
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.saveProductWithSeller(product,seller);
        product.setSeller(seller);

        assert result.getSeller().getName().equals("Vendedor");
        assert result.equals(product);
    }

    /**
     * @Author: Matheus Guerra
     * @Teste: Teste unitario função putProductStatus
     * @Description: valida que a função retorna o product com o atributo statusProduct alterado corretamente
     */
    @Test
    @DisplayName("Test US:06 - putProductStatus - Correct functioning")
    public void sputProductStatus_should_return_correct_seller(){
        when(productRepository.findByIdAndSeller_Id(1l,1l)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.putProductStatus(1l,1l,false);
        product.setStatusProduct(false);

        assert result.getStatusProduct().equals(false);
        assert result.equals(product);
    }
}
