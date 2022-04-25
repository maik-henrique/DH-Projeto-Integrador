package br.com.meli.dhprojetointegrador.service;

import org.springframework.stereotype.Service;

import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BusinessValidatorException(String.format("Product with id %d not found", id)));
    }

}
