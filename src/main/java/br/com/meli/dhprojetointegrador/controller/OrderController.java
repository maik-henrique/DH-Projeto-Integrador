package br.com.meli.dhprojetointegrador.controller;


import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;


@RestController
@RequestMapping(OrderController.baseUri)
public class OrderController {


    public static final String baseUri = "/api/v1/fresh-products/orders/";

    @Autowired
    private OrderService orderService;



    @PostMapping("")
    public PurchaseOrder<PurchaseOrderInput> PurchaseOrderProductRegistration(@Valid @RequestBody PurchaseOrderInput dto, UriComponentsBuilder uriBuilder){

    }

    @PutMapping
    public PurchaseOrder<PurchaseOrderInput> ModifyExistingOrder(@Valid @RequestBody PurchaseOrderInput dto){
        PurchaseOrder purchaseorder = dto.map();
        PurchaseOrder newStatus = OrderService.atualizar(purchaseorder);


        return ResponseEntity.ok(PurchaseOrderInput.map(newStatus));

    }
}
