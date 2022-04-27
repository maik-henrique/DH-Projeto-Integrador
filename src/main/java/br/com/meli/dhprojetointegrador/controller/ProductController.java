package br.com.meli.dhprojetointegrador.controller;


import br.com.meli.dhprojetointegrador.dto.response.ProductDto;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.service.ProductService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<List<ProductDto>> returnAllProducts() {
        List<Product> products = productService.returnAllProducts();
            return products == null || products.isEmpty()?ResponseEntity.notFound().build():
                    ResponseEntity.ok(ProductDto.map(products));
    }

}




