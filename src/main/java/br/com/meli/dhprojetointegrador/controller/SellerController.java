package br.com.meli.dhprojetointegrador.controller;


import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Seller;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.repository.SellerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/seller")
public class SellerController {

    SellerRepository sellerRepository;
    ProductRepository productRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Set<Product>> findAllSellersProducts(@PathVariable Long id){

//        Optional<Seller> op = sellerRepository.findById(id).get().getProducts();
//        Seller seller = op.get();

        return ResponseEntity.ok().body(sellerRepository.findById(id).get().getProducts());
    }

    @PostMapping("/register")
    public ResponseEntity<Seller> registerSeller(@RequestBody Seller seller){

        sellerRepository.save(seller);

        return ResponseEntity.ok().body(seller);
    }

    @PostMapping("/{id}/products")
    public ResponseEntity<Seller> registerSellerProduct(@PathVariable Long id,
                                                        @RequestBody Product product){

        Optional<Seller> op = sellerRepository.findById(id);
        Seller seller = op.get();
        product.setSeller(seller);

        Long productId = productRepository.save(product).getId();
        Optional<Product> opProduct = productRepository.findById(productId);


        seller.getProducts().add(opProduct.get());

        sellerRepository.save(seller);

        return ResponseEntity.ok().body(seller);
    }

    @PutMapping("/{id}/change-name")
    public Seller changeSellerName(@PathVariable Long id,
                                   @RequestParam(name = "name", required = false) String newName){

        Seller seller = sellerRepository.getById(id);
        seller.setName(newName);
        sellerRepository.save(seller);

        return seller;
    }

    @PutMapping("/{id}/change-product-status/{product_id}")
    public ResponseEntity<Product> changeSellerProductStatus(@PathVariable Long id,
                                                            @PathVariable Long product_id,
                                                            @RequestParam(name = "status", required = false) Boolean status){

        Product product = productRepository.findByIdAndSeller_Id(product_id, id);
        product.setStatusProduct(status);
        productRepository.save(product);
        return ResponseEntity.ok().body(productRepository.findByIdAndSeller_Id(product_id, id));
    }

    @PutMapping("/{id}/change-account-status")
    public ResponseEntity<Seller> changeSellerAccountStatus(@PathVariable Long id,
                                       @RequestParam(name = "status", required = false) Boolean status){

        Seller seller = sellerRepository.getById(id);
        seller.setStatusActiveAccount(status);
        sellerRepository.save(seller);

        return ResponseEntity.ok().body(seller);
    }

}
