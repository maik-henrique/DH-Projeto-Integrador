package br.com.meli.dhprojetointegrador.controller;

import br.com.meli.dhprojetointegrador.dto.request.freshproducts.FetchFreshProductsSortByRequest;
import br.com.meli.dhprojetointegrador.dto.response.freshproducts.BatchStockCollectionResponse;
import br.com.meli.dhprojetointegrador.dto.response.freshproducts.FreshProductsQueriedResponse;
import br.com.meli.dhprojetointegrador.entity.BatchStock;
import java.util.List;
import br.com.meli.dhprojetointegrador.dto.response.ProductByWarehouseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.meli.dhprojetointegrador.dto.response.ProductResponseResponse;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.service.BatchStockService;
import br.com.meli.dhprojetointegrador.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;


@RestController
@AllArgsConstructor
@ResponseBody
@RequestMapping("/api/v1/fresh-products")
public class ProductController {


    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final BatchStockService batchStockService;

    /**
     * Author: Mariana Galdino
     * Method: Buscar todos os produtos
     * Description: Serviço responsavel por retornar todos os produtos presentes na
     * aplicação;
     *
     *
     * @return lista de produtos
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseResponse>> returnAllProducts() {

        List<Product> products = productService.returnAllProducts();
        return products == null || products.isEmpty()?ResponseEntity.notFound().build():
                ResponseEntity.ok(ProductResponseResponse.map(products));
    }

    /**
     * Author: Matheus Guerra e Maik
     * Method: Buscar todos os produtos de uma certa categoria ou busca lotes em que um determinado produto está contido
     * Description: Retorna todos os produtos de uma mesma categoria presentes no
     * banco de dados; Alternativamente, permite a busca dos lotes em que um produto está contido.
     *
     * @param category Um dos 3 valores possiveis para o atributo "name" da Class
     *                 Category:
     *                 FS,
     *                 RF,
     *                 FF
     * @param sortBy   Para requisições baseadas em produtos, é usado como parâmetro de ordenação, por padrão orderna por batchNumber
     * @param id       Representa o id do produto que será alvo da busca
     *
     * @return Se existir, retorna lista de produtos filtrados por categoria
     */
    @GetMapping("/list")
    public ResponseEntity<?> findAllProducts(@RequestParam(name = "category", required = false) String category,
                                             @RequestParam(name = "sortBy", defaultValue = "L") FetchFreshProductsSortByRequest sortBy,
                                             @RequestParam(name = "productId", required = false) Long id) {
        if (category != null) {
            List<Product> products = productService.returnProductsByCategory(category);
            return products == null || products.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(products);
        }

        List<BatchStock> batchStockSorted = batchStockService.findByProductId(id, sortBy.getFieldName());
        FreshProductsQueriedResponse response = modelMapper.map(BatchStockCollectionResponse.builder().batchStock(batchStockSorted).build(),
                FreshProductsQueriedResponse.class);

        return ResponseEntity.ok(response);

    }

    /**
     * Author: Bruno Mendes
     * Method: returnTotalProductsByWarehouse
     * Description: Busca os produtos e associação com cada warehouse e soma o total de produtos em cada warehouse
     * @return ProductByWarehouseResponse
     */
    @GetMapping("fresh-products/warehouse/{id}")
    public ResponseEntity <ProductByWarehouseResponse> returnTotalProductsByWarehouse(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.getProductByWarehouse(id));
    }

}