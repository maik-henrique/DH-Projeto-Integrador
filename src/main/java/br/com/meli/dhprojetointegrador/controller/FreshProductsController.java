package br.com.meli.dhprojetointegrador.controller;

import br.com.meli.dhprojetointegrador.dto.request.freshproducts.FetchFreshProductsSortByRequest;
import br.com.meli.dhprojetointegrador.dto.response.freshproducts.FreshProductsQueriedResponse;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fresh-products/list")
@AllArgsConstructor
public class FreshProductsController {

    private final ProductService productService;
    private final ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<?> findAllProducts(@RequestParam(name = "sortBy") FetchFreshProductsSortByRequest sortBy,
                                             @RequestParam(name = "productId") Long id) {
        Product product = productService.findProductByIdSorted(id, sortBy.getFieldName());
        FreshProductsQueriedResponse freshProductsQueriedResponse = modelMapper.map(product,
                FreshProductsQueriedResponse.class);
        return ResponseEntity.ok(freshProductsQueriedResponse);
    }
}
