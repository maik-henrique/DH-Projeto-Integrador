package br.com.meli.dhprojetointegrador.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import br.com.meli.dhprojetointegrador.dto.response.CartProductDTO;
import br.com.meli.dhprojetointegrador.entity.CartProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


import br.com.meli.dhprojetointegrador.dto.request.PurchaseOrderInput;
import br.com.meli.dhprojetointegrador.dto.response.OrderIntermediateDTO;
import br.com.meli.dhprojetointegrador.dto.response.TotalPrice;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.service.CartProductService;
import br.com.meli.dhprojetointegrador.service.OrderService;


@RestController
@RequestMapping(OrderController.baseUri)
public class OrderController {
    public static final String baseUri = "/api/v1/fresh-products/orders/";
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartProductService cartProductService;

    /**
     * Author: Bruno Mendes
     * Method: PurchaseOrderProductRegistration
     * Description: Controller para realizar a operação de criar uma ordem de compra
     */
    @PostMapping("")
    public ResponseEntity<TotalPrice> PurchaseOrderProductRegistration(@Valid @RequestBody PurchaseOrderInput input,
            UriComponentsBuilder uriBuilder) {
        OrderIntermediateDTO result = orderService.createOrder(input);
        TotalPrice totalPrice = TotalPrice.builder().totalPrice(result.getTotalPrice()).build();
        URI uri = uriBuilder
                .path(baseUri.concat("/{id}"))
                .buildAndExpand(result.getCreatedID())
                .toUri();
        return ResponseEntity.created(uri).body(totalPrice);
    }

    @PutMapping("{idorder}")
    public ResponseEntity<PurchaseOrder> ModifyExistingOrder(@PathVariable Long idorder) {
        PurchaseOrder newOrderStatus = orderService.atualizar(idorder);
        return ResponseEntity.ok(newOrderStatus);
    }

    @GetMapping
    public ResponseEntity<?> ShowProductsOrder(@RequestParam Long idOrder) {
        List<CartProduct> products = cartProductService.getProductsByOrderId(idOrder);
        return new ResponseEntity<>(CartProductDTO.convertToProductList(products), HttpStatus.OK);
    }
}