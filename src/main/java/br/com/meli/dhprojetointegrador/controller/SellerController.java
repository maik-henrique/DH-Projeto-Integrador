package br.com.meli.dhprojetointegrador.controller;


import br.com.meli.dhprojetointegrador.dto.request.SellerPostRequest;
import br.com.meli.dhprojetointegrador.dto.request.SellerProductPostRequest;
import br.com.meli.dhprojetointegrador.dto.response.ProductResponseDto;
import br.com.meli.dhprojetointegrador.dto.response.SellerResponseDTO;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Seller;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.repository.SellerRepository;
import br.com.meli.dhprojetointegrador.service.ProductService;
import br.com.meli.dhprojetointegrador.service.SellerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping(SellerController.baseUri)
public class SellerController {

    public static final String baseUri = "/api/v1/seller";

    SellerRepository sellerRepository;
    ProductRepository productRepository;
    private final SellerService sellerService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<Set<Product>> findAllSellersProducts(@PathVariable Long id){

        Set<Product> products = sellerService.findSellerById(id).getProducts();

        return products == null || products.isEmpty()?ResponseEntity.notFound().build():
                ResponseEntity.ok(products);
    }

    @PostMapping("/register")
    public ResponseEntity<Seller> registerSeller(@Valid @RequestBody SellerPostRequest sellerPostRequest,
                                                 UriComponentsBuilder uriBuilder){

        Seller seller = modelMapper.map(sellerPostRequest, Seller.class);
        seller.setStatusActiveAccount(true);
        SellerResponseDTO response = modelMapper.map(seller, SellerResponseDTO.class);

        URI uri = uriBuilder
                .path(baseUri.concat("/{id}"))
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(sellerService.saveSeller(seller));
    }

    @PostMapping("/{seller_id}/products")
    public ResponseEntity<Seller> registerSellerProduct(@PathVariable Long seller_id,
                                                        @Valid @RequestBody SellerProductPostRequest sellerProduct,
                                                        UriComponentsBuilder uriBuilder){

        Product product = modelMapper.map(sellerProduct, Product.class);
        Seller seller = sellerService.findSellerById(seller_id);
        Long productId = productService.saveProductWithSeller(product,seller).getId();
        Product productSaved = productService.findProductById(productId);
        seller.getProducts().add(productSaved);
        Seller sellerSaved = sellerService.saveSeller(seller);

        URI uri = uriBuilder

                .path(baseUri.concat("/{id}/products"))
                .buildAndExpand(sellerSaved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(sellerSaved);
    }

    @PutMapping("/{id}/change-name")
    public ResponseEntity<Seller> changeSellerName(@PathVariable Long id,
                                   @RequestParam(name = "name", required = true) String newName){

        return ResponseEntity.ok().body(sellerService.putSellerName(id, newName));
    }

    @PutMapping("/{id}/change-account-status")
    public ResponseEntity<Seller> changeSellerAccountStatus(@PathVariable Long id,
                                                            @RequestParam(name = "status", required = true) Boolean status){

        return ResponseEntity.ok().body(sellerService.putSellerAccountStatus(id, status));
    }

    @PutMapping("/{sellerId}/change-product-status/{productId}")
    public ResponseEntity<Product> changeSellerProductStatus(@PathVariable Long sellerId,
                                                            @PathVariable Long productId,
                                                            @RequestParam(name = "status", required = true) Boolean status){

        return ResponseEntity.ok().body(productService.putProductStatus(productId, sellerId, status));
    }



}
