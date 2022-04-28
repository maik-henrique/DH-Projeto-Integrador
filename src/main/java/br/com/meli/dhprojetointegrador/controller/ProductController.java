package br.com.meli.dhprojetointegrador.controller;

import java.util.List;

import br.com.meli.dhprojetointegrador.dto.response.ProductByWarehouseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.service.ProductService;
import lombok.AllArgsConstructor;

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
     * Description: Serviço responsavel por retornar todos os produtos presentes na
     * aplicação;
     * 
     * @return lista de produtos
     */

    @GetMapping("fresh-products")
    public ResponseEntity<?> returnAllProducts() {
        List<Product> products = productService.returnAllProducts();
        return products == null || products.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(products);
    }

    /**
     * Author: Matheus Guerra
     * Method: Buscar todos os produtos de uma certa categoria
     * Description: Retorna todos os produtos de uma mesma categoria presentes no
     * banco de dados;
     *
     * @param category Um dos 3 valores possiveis para o atributo "name" da Class
     *                 Category:
     *                 FS,
     *                 RF,
     *                 FF
     *
     * @return Se existir, retorna lista de produtos filtrados por categoria
     */

    @GetMapping("/fresh-products/list")
    public ResponseEntity<?> returnAllProductsByCategory(@RequestParam(required = false) String category) {

        List<Product> products = productService.returnProductsByCategory(category);
        return products == null || products.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(products);
    }

    @GetMapping("fresh-products/warehouse/{id}")
    public ResponseEntity <ProductByWarehouseResponse> returnTotalProductsByWarehouse(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.getProductByWarehouse(id));
    }

}