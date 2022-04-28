package br.com.meli.dhprojetointegrador.unit.service;

import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {


        @InjectMocks
        private ProductService productService;

        @Mock
        private ProductRepository productRepository;

    /**
     * Author: Mariana Galdino
     * Test: Buscar todos os produtos
     * Description: Serviço responsavel por retornar todos os produtos presentes na
     * aplicação;
     *
     * @return lista de produtos
     */

        @Test
        @DisplayName("Test Service 01 - Req002: Return all products")
        public void shouldReturnAllRegisteredProducts(){
            List<Product> productList = new ArrayList<>();
            Product product = Product.builder().name("abacaxi").price(new BigDecimal(10.0)).volume(15).build();
            Product product2 = Product.builder().name("frango").price(new BigDecimal(50.0)).volume(30).build();
            productList.add(product);
            productList.add(product2);

            Mockito.when(productRepository.findAll()).thenReturn(productList);

            List<Product> prodRepo = productService.returnAllProducts();


            assert(prodRepo.equals(productList));
        }

    /**
     * Author: Mariana Galdino
     * Test: Retorna lista de produtos vazia e status 404 not found
     * Description: Serviço responsavel por retornar uma lista vazia de produtos
     * @return lista vazia de produtos
     */

        @Test
        @DisplayName("Test Service 02 - Req002: Return status 404 when not found products")
        public void shouldReturnHttpStatusWhenNotFoundProducts(){
            List<Product> productList = new ArrayList<>();

            Mockito.when(productRepository.findAll()).thenReturn(productList);

            List<Product> prodRepo = productService.returnAllProducts();

            assertTrue(prodRepo.isEmpty());

        }

    }
