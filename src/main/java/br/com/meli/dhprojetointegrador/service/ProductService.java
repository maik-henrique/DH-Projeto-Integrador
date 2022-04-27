package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new BusinessValidatorException(String.format("Product with id %d not found", id)));
    }

    /**
     * Author: Mariana Galdino
     * Method: Buscar todos os produtos na lista
     * Description: Serviço responsavel por retornar uma lista com todos os produtos presentes na aplicação;
     * @return lista de produtos
     */
    public List<Product> returnAllProducts()    {
        return productRepository.findAll();
    }

    /**
     * Author: Matheus Guerra
     * Method: Buscar todos os produtos de uma certa categoria
     * Description: Serviço responsavel por retornar uma lista com todos os produtos presentes
     * na aplicação filtrados por categoria;
     *
     * @param category Um dos 3 valores possiveis para o atributo "name" da Class Category:
     *                 FS,
     *                 RF,
     *                 FF
     *
     * @return Se existir, retorna lista de produtos filtrados por categoria
     */
    public List<Product> returnProductsByCategory(String category ){
        return productRepository.findByCategory_Name(CategoryEnum.valueOf(category));
    }

}
