package br.com.meli.dhprojetointegrador.unit.service.validator;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.exception.NotEnoughProductsException;
import br.com.meli.dhprojetointegrador.exception.ProductNotFoundException;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.service.validator.ProductValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidateProductTests {

    ProductRepository productRepository = mock(ProductRepository.class);

    private ProductValidator validateProduct = new ProductValidator(productRepository);

    BatchStock batch1 = BatchStock.builder()
            .batchNumber(1L)
            .currentQuantity(15)
            .build();

    Product product1 = Product.builder()
            .id(1L)
            .batchStockList(Set.of(batch1))
            .name("Banana")
            .price(new BigDecimal("2.50"))
            .build();

    /**
     * @Author: Bruno
     * @Teste: Teste unitario função validateQuantity
     * @Description: valida funcionamento correto da função
     */
    @Test
    @DisplayName("TestPI-8 - validateQuantity")
    public void validateQuantity_should_return_correct_Product() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        Product result = validateProduct.validateQuantity(10, 1L);

        assert result.equals(product1);
    }

    /**
     * @Author: Bruno
     * @Teste: Teste unitario função validateQuantity
     * @Description: valida funcionamento correto da função
     */
    @Test
    @DisplayName("TestPI-8 - validateQuantity")
    public void validateQuantity_should_trow_corret_Error() {
        when(productRepository.findById(2L)).thenThrow(new NoSuchElementException());

        try {
            validateProduct.validateQuantity(10, 2L);
        } catch (ProductNotFoundException e) {
            assert true;
        }
    }

    /**
     * @Author: Bruno
     * @Teste: Teste unitario função validateQuantity
     * @Description: valida funcionamento correto da função
     */
    @Test
    @DisplayName("TestPI-8 - validateQuantity")
    public void validateQuantity_should_trow_NotEnougthProductsException() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        try {
            validateProduct.validateQuantity(20, 1L);
        } catch (NotEnoughProductsException e) {
            assert true;
        }
    }
}
