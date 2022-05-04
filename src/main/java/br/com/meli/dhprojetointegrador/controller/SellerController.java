package br.com.meli.dhprojetointegrador.controller;

import br.com.meli.dhprojetointegrador.dto.request.NewProductRequest;
import br.com.meli.dhprojetointegrador.dto.response.FullProductResponse;
import br.com.meli.dhprojetointegrador.service.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/seller/products")
@AllArgsConstructor
public class SellerController {

    public static final String baseUri = "api/v1/fresh-products/seller/products";

    private SellerService sellerService;

    /**
     * Author: Bruno Mendes
     * Method: createNewProduct
     * Description: Controller para realizar a operação de criar um novo produto
     */
    @PostMapping
    public ResponseEntity<FullProductResponse> createNewProduct(@Valid @RequestBody NewProductRequest input,
                                                                                UriComponentsBuilder uriBuilder) {
        FullProductResponse result = sellerService.createProduct(input);
        URI uri = uriBuilder
                .path(baseUri.concat("/{id}"))
                .buildAndExpand(result.getId())
                .toUri();
        return ResponseEntity.created(uri).body(result);
    }

    /**
     * Author: Bruno Mendes
     * Method: getProducts
     * Description: Controller para realizar a operação de recuperar produtos
     */
    @GetMapping
    public ResponseEntity<List<FullProductResponse>> getProducts(@RequestParam(name = "sellerId") String sellerId,
                                                                          @RequestParam(name = "name", required = false) String name) {
        if (name == null) {
            List<FullProductResponse> result = sellerService.getAllProducts(Long.decode(sellerId));
            return ResponseEntity.ok(result);
        } else {
            List<FullProductResponse> result = sellerService.getAllProductsByName(Long.decode(sellerId), name);
            return ResponseEntity.ok(result);
        }
    }

    /**
     * Author: Bruno Mendes
     * Method: updateProduct
     * Description: Controller para realizar a operação de atualizar nome e/ou preco de um produto
     */
    @PatchMapping
    public ResponseEntity<FullProductResponse> updateProduct(@RequestParam(name = "productId") String productId,
                                                             @RequestParam(name = "name", required = false) String name,
                                                             @RequestParam(name = "price", required = false) String price) {
        FullProductResponse result = sellerService.updateProduct(Long.valueOf(productId), name, BigDecimal.valueOf(Float.parseFloat(price)));

        return ResponseEntity.ok(result);
    }
}
