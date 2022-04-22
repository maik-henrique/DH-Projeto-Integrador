package br.com.meli.dhprojetointegrador.controller;


import br.com.meli.dhprojetointegrador.dto.request.PurchaseOrderInput;
import br.com.meli.dhprojetointegrador.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PurchaseOrder<PurchaseOrderDTO> PurchaseOrderProductRegistration(@Valid @RequestBody PurchaseOrderInput input, UriComponentsBuilder uriBuilder){


    }
}
