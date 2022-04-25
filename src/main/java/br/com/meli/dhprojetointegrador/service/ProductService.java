package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.controller.ProductController;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Author: Mariana Galdino
     * Method: Buscar todos os produtos na lista
     * Description: Serviço responsavel por retornar uma lista com todos os produtos presentes na aplicação;
     * @return lista de produtos
     */

    public List<Product> returnAllProducts()    {
        return productRepository.findAll();
    }

}
