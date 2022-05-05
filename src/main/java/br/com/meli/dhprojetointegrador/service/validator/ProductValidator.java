package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.exception.NotEnoughProductsException;
import br.com.meli.dhprojetointegrador.exception.ProductNotFoundException;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProductValidator {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Author: Bruno Mendes
     * Method: validateQuantity
     * Description: Valida se determinado produto possui estoque suficiente
     */
    //@CachePut(value = "validateQuantity", key = "#id")
    public Product validateQuantity(Integer qtd, Long id) {
        try {
            Product product = productRepository.findById(id).get();
            Set<BatchStock> batchList = new HashSet<>(product.getBatchStockList());
            int totalStock = batchList.stream().mapToInt(BatchStock::getCurrentQuantity).sum();
            if (totalStock < qtd) {
                throw new NotEnoughProductsException("The product " + product.getName() + " doesn't have enough stock for your purchase");
            } else {
                return product;
            }
        } catch (NoSuchElementException e ) {
            throw new ProductNotFoundException("This product isn't on the database");
        }
    }
}
