package br.com.meli.dhprojetointegrador.controller;


import br.com.meli.dhprojetointegrador.dto.request.ProductInput;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.service.OrderService;
import br.com.meli.dhprojetointegrador.service.CartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(OrderController.baseUri)
public class OrderController {


    public static final String baseUri = "/api/v1/fresh-products/orders/";

    @Autowired
    private OrderService orderService;


    @Autowired
    private CartProductService cartProductService;

    /**
     * @Author: David
     * @Methodo: Mudar Cart para Aberto ou Finalizado na Order
     * @Description: Modifique o pedido existente. torná-lo do tipo de carrinho para modificar - ABERTO/FINALIZADO
     * @param idorder
     * @return
     */
    @PutMapping("{idorder}")
    public ResponseEntity<PurchaseOrder> ModifyExistingOrder(@Valid @PathVariable Long idorder){
        PurchaseOrder newOrderStatus = orderService.atualizar(idorder);
        return ResponseEntity.ok(newOrderStatus);
    }

    /**
     * @Author: David
     * @Methodo: Listar produtos em seus determinados lotes
     * @Description: Veja uma lista de produtos com todos os lotes onde aparece. Se a lista não existir, ela deve retornar um “404 Not Found”.
     * @param list
     * @param querytype
     * @param idProducto
     * @return
     */
    @GetMapping("/api/v1/fresh-products/{list}?{querytype}={idProducto}")
    public ResponseEntity<List<ProductInput>> listarProdutosLotes(@RequestParam(required = true) String list, Integer querytype, Integer idProducto){
        List<Product> products = orderService.listaProdutosLotes();
        return ResponseEntity.ok(ProductInput.map(products));
    }

    @GetMapping
    public ResponseEntity<?> ShowProductsOrder(@RequestParam Long idOrder) {
        return new ResponseEntity<>(cartProductService.getProductsByOrderId(idOrder), HttpStatus.OK);

    }

}
