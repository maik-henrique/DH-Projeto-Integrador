package br.com.meli.dhprojetointegrador.controller;

import br.com.meli.dhprojetointegrador.dto.request.NewProductRequest;
import br.com.meli.dhprojetointegrador.dto.response.FullProductResponse;
import br.com.meli.dhprojetointegrador.entity.Product;
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
@RequestMapping("/api/v1/fresh-products/seller")
@AllArgsConstructor
public class SellerController {

    public static final String baseUri = "api/v1/fresh-products/seller";

    private SellerService sellerService;

    /**
     * Author: Bruno Mendes
     * Method: PurchaseOrderProductRegistration
     * Description: Controller para realizar a operação de criar uma ordem de compra
     */
    @PostMapping("/products")
    public ResponseEntity<FullProductResponse> PurchaseOrderProductRegistration(@Valid @RequestBody NewProductRequest input,
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
     * Method: PurchaseOrderProductRegistration
     * Description: Controller para realizar a operação de criar uma ordem de compra
     */
    @GetMapping("/products")
    public ResponseEntity<List<FullProductResponse>> PurchaseOrderProductRegistration(@RequestParam(name = "sellerId") Long sellerId,
                                                                          @RequestParam(name = "name", required = false) String name) {
        if (name == null) {
            List<FullProductResponse> result = sellerService.getAllProducts(sellerId);
            return ResponseEntity.ok(result);
        } else {
            List<FullProductResponse> result = sellerService.getAllProductsByName(sellerId, name);
            return ResponseEntity.ok(result);
        }
    }

    /**
     * Author: Bruno Mendes
     * Method: PurchaseOrderProductRegistration
     * Description: Controller para realizar a operação de criar uma ordem de compra
     */
    @PatchMapping("/products")
    public ResponseEntity<FullProductResponse> PurchaseOrderProductRegistration(@RequestParam(name = "productId") Long productId,
                                                                    @RequestParam(name = "name", required = false) String name,
                                                                    @RequestParam(name = "price", required = false) BigDecimal price) {
        FullProductResponse result = sellerService.updateProduct(productId, name, price);

        return ResponseEntity.ok(result);
    }
}
