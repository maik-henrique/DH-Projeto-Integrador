package br.com.meli.dhprojetointegrador.controller;


import br.com.meli.dhprojetointegrador.dto.request.InboundPostRequestBody;
import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.repository.CategoryRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@ResponseBody
@RequestMapping(ProductController.baseUri)
public class ProductController {

    public static final String baseUri = "/api/v1/";
    private final ProductService productService;


    /**
     * Author: Mariana Galdino
     * Method: Buscar todos os produtos
     * Description: Serviço responsavel por retornar todos os produtos presentes na aplicação;
     * @return lista de produtos
     */

    @GetMapping("fresh-products")
    public ResponseEntity<?> returnAllProducts() {
        List<Product> products = productService.returnAllProducts();
            return products == null || products.isEmpty()?ResponseEntity.notFound().build():
                    ResponseEntity.ok(products);
    }

    /**
     * Author: Matheus Guerra
     * Method: Buscar todos os produtos de uma certa categoria
     * Description: Retorna todos os produtos de uma mesma categoria presentes no banco de dados;
     *
     * @param category Um dos 3 valores possiveis para o atributo "name" da Class Category:
     *                 FS,
     *                 RF,
     *                 FF
     *
     * @return Se existir, retorna lista de produtos filtrados por categoria
     */

    @GetMapping("/fresh-products/list")
    public ResponseEntity<?> returnAllProductsByCategory(@RequestParam(required = false) String category) {

        List<Product> products = productService.returnProductsByCategory(category);
        return products == null || products.isEmpty()?ResponseEntity.notFound().build():
                ResponseEntity.ok(products);
    }

}