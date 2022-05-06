package br.com.meli.dhprojetointegrador.controller;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import br.com.meli.dhprojetointegrador.dto.response.CartProductResponse;
import br.com.meli.dhprojetointegrador.entity.CartProduct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.meli.dhprojetointegrador.dto.request.PurchaseOrderRequest;
import br.com.meli.dhprojetointegrador.dto.response.OrderIntermediateResponse;
import br.com.meli.dhprojetointegrador.dto.response.TotalPriceResponse;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.service.CartProductService;
import lombok.AllArgsConstructor;
import br.com.meli.dhprojetointegrador.service.OrderService;


@AllArgsConstructor
@RestController
@RequestMapping(OrderController.baseUri)
public class OrderController {

    public static final String baseUri = "/api/v1/fresh-products/orders/";

    private final OrderService orderService;

    private final CartProductService cartProductService;

    /**
     * Author: Bruno Mendes
     * Method: PurchaseOrderProductRegistration
     * Description: Controller para realizar a operação de criar uma ordem de compra
     */
    @PostMapping("")
    public ResponseEntity<TotalPriceResponse> PurchaseOrderProductRegistration(@Valid @RequestBody PurchaseOrderRequest input,
                                                                               UriComponentsBuilder uriBuilder) {
        OrderIntermediateResponse result = orderService.createOrder(input);
        TotalPriceResponse totalPriceResponse = TotalPriceResponse.builder().totalPrice(result.getTotalPrice()).build();
        URI uri = uriBuilder
                .path(baseUri.concat("/{id}"))
                .buildAndExpand(result.getCreatedID())
                .toUri();
        return ResponseEntity.created(uri).body(totalPriceResponse);
    }

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
     * @param idOrder
     * @return
     * Author: Micaela Alves
     * Method: ShowProductsOrder
     * Description: Controller retorna a lista de produtos pertencentes a uma PurchaseOrder
     */
    @GetMapping
    public ResponseEntity<?> ShowProductsOrder(@RequestParam Long idOrder) {
        List<CartProduct> products = cartProductService.getProductsByOrderId(idOrder);
        return new ResponseEntity<>(CartProductResponse.convertToProductList(products), HttpStatus.OK);
    }
}