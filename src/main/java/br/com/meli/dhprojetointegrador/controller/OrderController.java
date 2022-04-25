package br.com.meli.dhprojetointegrador.controller;


import br.com.meli.dhprojetointegrador.entity.CartProduct;
import br.com.meli.dhprojetointegrador.service.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/")
    public ResponseEntity<?> ShowProductsOrder(@RequestParam Long idOrder) {
        return new ResponseEntity<>(cartProductService.getProductsByOrderId(idOrder), HttpStatus.OK);

    }

}
