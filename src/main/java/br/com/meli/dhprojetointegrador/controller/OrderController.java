package br.com.meli.dhprojetointegrador.controller;


import br.com.meli.dhprojetointegrador.dto.request.PurchaseOrderInput;
import br.com.meli.dhprojetointegrador.dto.response.OrderIntermediateDTO;
import br.com.meli.dhprojetointegrador.dto.response.TotalPrice;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping(OrderController.baseUri)
public class OrderController {

    public static final String baseUri = "/api/v1/fresh-products/orders/";

    @Autowired
    private OrderService orderService;



    @PostMapping("")
    public ResponseEntity<TotalPrice> PurchaseOrderProductRegistration(@Valid @RequestBody PurchaseOrderInput input, UriComponentsBuilder uriBuilder){
        OrderIntermediateDTO result = orderService.createOrder(input);
        TotalPrice totalPrice = TotalPrice.builder().totalPrice(result.getTotalPrice()).build();
        URI uri = uriBuilder
                .path(baseUri.concat("/{id}"))
                .buildAndExpand(result.getCreatedID())
                .toUri();
        return ResponseEntity.created(uri).body(totalPrice);
    }
}
