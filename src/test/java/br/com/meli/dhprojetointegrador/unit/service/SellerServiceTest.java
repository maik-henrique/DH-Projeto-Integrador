package br.com.meli.dhprojetointegrador.unit.service;

import br.com.meli.dhprojetointegrador.dto.request.NewProductRequest;
import br.com.meli.dhprojetointegrador.dto.response.FullProductResponse;
import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Seller;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.CategoryRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.repository.SellerRepository;
import br.com.meli.dhprojetointegrador.service.SellerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SellerServiceTest {
    ProductRepository productRepository = mock(ProductRepository.class);
    SellerRepository sellerRepositry = mock(SellerRepository.class);
    CategoryRepository categoryRepository = mock(CategoryRepository.class);

    private final SellerService sellerservice = new SellerService(productRepository, sellerRepositry, categoryRepository);

    NewProductRequest newProductRequest = NewProductRequest.builder()
            .name("sorvete")
            .sellerId(3L)
            .price(BigDecimal.valueOf(17.50))
            .categoryId(4L)
            .volume(0.25F)
            .build();

    Seller seller = Seller.builder()
            .id(3L)
            .name("Jorge")
            .build();

    Category category = Category.builder()
            .id(4L)
            .name(CategoryEnum.FS)
            .maximumTemperature(20)
            .minimumTemperature(5)
            .build();

    Product product = Product.builder()
            .id(1L)
            .name("sorvete")
            .price(BigDecimal.valueOf(17.50))
            .volume(0.25F)
            .category(category)
            .seller(seller)
            .build();

    Product product2 = Product.builder()
            .id(2L)
            .name("banana")
            .price(BigDecimal.valueOf(2.30))
            .volume(0.15F)
            .category(category)
            .seller(seller)
            .build();

    Product product2Updated = Product.builder()
            .id(2L)
            .name("maca")
            .price(BigDecimal.valueOf(1.30))
            .volume(0.15F)
            .category(category)
            .seller(seller)
            .build();


    FullProductResponse fullProductResponse = FullProductResponse.builder()
            .id(1L)
            .name("sorvete")
            .price(BigDecimal.valueOf(17.50))
            .volume(0.25F)
            .seller_id(3L)
            .category_id(4L)
            .build();

    FullProductResponse fullProductResponse2 = FullProductResponse.builder()
            .id(2L)
            .name("banana")
            .price(BigDecimal.valueOf(2.30))
            .volume(0.15F)
            .seller_id(3L)
            .category_id(4L)
            .build();

    FullProductResponse fullProductResponse2Updated = FullProductResponse.builder()
            .id(2L)
            .name("maca")
            .price(BigDecimal.valueOf(1.30))
            .volume(0.15F)
            .seller_id(3L)
            .category_id(4L)
            .build();

    /**
     * @Author: Bruno Mendes
     * @Teste: Teste unitario função createProduct
     * @Description: valida funcionamento correto da função
     */
    @Test
    @DisplayName("TestPI-68 - createProduct")
    public void createProduct_should_return_correct_FullProductResponse() {
        when(sellerRepositry.getById(3L)).thenReturn(seller);
        when(categoryRepository.getById(4L)).thenReturn(category);
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        FullProductResponse result = sellerservice.createProduct(newProductRequest);

        assert result.getId().equals(fullProductResponse.getId());
        assert result.getName().equals(fullProductResponse.getName());
        assert result.getPrice().equals(fullProductResponse.getPrice());
        assert result.getSeller_id().equals(fullProductResponse.getSeller_id());
        assert result.getCategory_id().equals(fullProductResponse.getCategory_id());
        assert result.getVolume() == fullProductResponse.getVolume();
    }

    /**
     * @Author: Bruno Mendes
     * @Teste: Teste unitario função getAllProducts
     * @Description: valida funcionamento correto da função
     */
    @Test
    @DisplayName("TestPI-68 - getAllProducts")
    public void getAllProducts_should_return_correct_list_of_FullProductResponse() {
        when(sellerRepositry.getById(3L)).thenReturn(seller);
        when(productRepository.findBySeller(seller)).thenReturn(List.of(product, product2));

        List<FullProductResponse> result = sellerservice.getAllProducts(3L);

        assert result.get(0).getId().equals(fullProductResponse.getId());
        assert result.get(0).getName().equals(fullProductResponse.getName());
        assert result.get(0).getPrice().equals(fullProductResponse.getPrice());
        assert result.get(0).getSeller_id().equals(fullProductResponse.getSeller_id());
        assert result.get(0).getCategory_id().equals(fullProductResponse.getCategory_id());
        assert result.get(0).getVolume() == fullProductResponse.getVolume();

        assert result.get(1).getId().equals(fullProductResponse2.getId());
        assert result.get(1).getName().equals(fullProductResponse2.getName());
        assert result.get(1).getPrice().equals(fullProductResponse2.getPrice());
        assert result.get(1).getSeller_id().equals(fullProductResponse2.getSeller_id());
        assert result.get(1).getCategory_id().equals(fullProductResponse2.getCategory_id());
        assert result.get(1).getVolume() == fullProductResponse2.getVolume();
    }

    /**
     * @Author: Bruno Mendes
     * @Teste: Teste unitario função getAllProductsByName
     * @Description: valida funcionamento correto da função
     */
    @Test
    @DisplayName("TestPI-68 - getAllProductsAndName")
    public void getAllProductsByName_should_return_correct_list_of_FullProductResponse() {
        when(productRepository.findBySellerIdAndAndName(3L, "banana")).thenReturn(List.of(product2));

        List<FullProductResponse> result = sellerservice.getAllProductsByName(3L, "banana");

        assert result.get(0).getId().equals(fullProductResponse2.getId());
        assert result.get(0).getName().equals(fullProductResponse2.getName());
        assert result.get(0).getPrice().equals(fullProductResponse2.getPrice());
        assert result.get(0).getSeller_id().equals(fullProductResponse2.getSeller_id());
        assert result.get(0).getCategory_id().equals(fullProductResponse2.getCategory_id());
        assert result.get(0).getVolume() == fullProductResponse2.getVolume();
    }

    /**
     * @Author: Bruno Mendes
     * @Teste: Teste unitario função updateProduct
     * @Description: valida funcionamento correto da função
     */
    @Test
    @DisplayName("TestPI-68 - updateproduct")
    public void updateProduct_should_return_correct_FullProductResponse() {
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product2Updated);

        FullProductResponse result = sellerservice.updateProduct(2L, "maca", BigDecimal.valueOf(1.30));

        assert result.getId().equals(fullProductResponse2Updated.getId());
        assert result.getName().equals(fullProductResponse2Updated.getName());
        assert result.getPrice().equals(fullProductResponse2Updated.getPrice());
        assert result.getSeller_id().equals(fullProductResponse2Updated.getSeller_id());
        assert result.getCategory_id().equals(fullProductResponse2Updated.getCategory_id());
        assert result.getVolume() == fullProductResponse2Updated.getVolume();
    }
}
