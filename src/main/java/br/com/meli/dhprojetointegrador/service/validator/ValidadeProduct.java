package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.exception.NotEnoughProductsException;
import br.com.meli.dhprojetointegrador.exception.ProductNotFoundException;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ValidadeProduct {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Author: Bruno Mendes
     * Method: validateQuantity
     * Description: Valida se determinado produto possui estoque suficiente
     */
    public Product validateQuantity(Integer qtd, Long id) {
        try {
            Product product = productRepository.getById(id);
            Set<BatchStock> batchList = product.getBatchStockList();
            int totalStock = batchList.stream().mapToInt(BatchStock::getCurrentQuantity).sum();
            if (totalStock < qtd) {
                throw new NotEnoughProductsException("The product " + product.getName() + " doesn't have enough stock for your purchase");
            }
            return product;
        } catch (EntityNotFoundException e ) {
            throw new ProductNotFoundException("This product isn't on the database");
        }
    }
}
