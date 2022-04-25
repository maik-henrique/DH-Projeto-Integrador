package br.com.meli.dhprojetointegrador.controller;

import br.com.meli.dhprojetointegrador.dto.request.PurchaseOrderInput;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.service.OrderService;
import br.com.meli.dhprojetointegrador.entity.CartProduct;
import br.com.meli.dhprojetointegrador.service.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(OrderController.baseUri)
public class OrderController {


    public static final String baseUri = "/api/v1/fresh-products/orders/";

    @Autowired
    private OrderService orderService;

  

    @PostMapping("")
    public PurchaseOrder<PurchaseOrderDTO> PurchaseOrderProductRegistration(@Valid @RequestBody PurchaseOrderDTO dto, UriComponentsBuilder uriBuilder){



    @Autowired
    private CartProductService cartProductService;


    @PutMapping("{idorder}")
    public ResponseEntity<PurchaseOrder> ModifyExistingOrder(@PathVariable Long idorder){

        PurchaseOrder newOrderStatus = orderService.atualizar(idorder);

        return ResponseEntity.ok(newOrderStatus);
    }

    @GetMapping("/")
    public ResponseEntity<?> ShowProductsOrder(@RequestParam Long idOrder) {
        return new ResponseEntity<>(cartProductService.getProductsByOrderId(idOrder), HttpStatus.OK);

    }

}
