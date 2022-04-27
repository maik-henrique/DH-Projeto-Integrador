package br.com.meli.dhprojetointegrador.controller;


import br.com.meli.dhprojetointegrador.dto.request.freshproducts.FetchFreshProductsSortByRequest;
import br.com.meli.dhprojetointegrador.dto.response.freshproducts.BatchStockCollection;
import br.com.meli.dhprojetointegrador.dto.response.freshproducts.FreshProductsQueriedResponse;
import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.service.BatchStockService;
import br.com.meli.dhprojetointegrador.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    private final BatchStockService batchStockService;

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

    @GetMapping("fresh-products/list")
    public ResponseEntity<?> findAllProducts(@RequestParam(name = "sortBy", defaultValue = "L") FetchFreshProductsSortByRequest sortBy,
                                             @RequestParam(name = "productId") Long id) {
        List<BatchStock> batchStockSorted = batchStockService.findByProductId(id, sortBy.getFieldName());

        FreshProductsQueriedResponse response = modelMapper.map(BatchStockCollection.builder().batchStock(batchStockSorted).build(),
                FreshProductsQueriedResponse.class);
        return ResponseEntity.ok(response);
    }

}