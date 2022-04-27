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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/list")
@AllArgsConstructor
public class FreshProductsController {

    private final ModelMapper modelMapper;
    private final BatchStockService batchStockService;


    @GetMapping
    public ResponseEntity<?> findAllProducts(@RequestParam(name = "sortBy") FetchFreshProductsSortByRequest sortBy,
                                             @RequestParam(name = "productId") Long id) {
        List<BatchStock> batchStockSorted = batchStockService.findByProductId(id, sortBy.getFieldName());

        FreshProductsQueriedResponse response = modelMapper.map(BatchStockCollection.builder().batchStock(batchStockSorted).build(),
                FreshProductsQueriedResponse.class);
        return ResponseEntity.ok(response);
    }


}
