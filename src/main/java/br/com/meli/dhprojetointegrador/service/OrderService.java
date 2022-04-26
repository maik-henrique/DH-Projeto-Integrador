package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.dto.request.PurchaseOrderInput;
import br.com.meli.dhprojetointegrador.dto.response.OrderIntermediateDTO;
import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.repository.BatchStockRepository;
import br.com.meli.dhprojetointegrador.repository.CartProductRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.repository.PurchaseOrderRepository;
import br.com.meli.dhprojetointegrador.service.validator.ValidadeProduct;
import br.com.meli.dhprojetointegrador.service.validator.ValidateBuyer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderService {

    @Autowired
    private ValidateBuyer validateBuyer;

    @Autowired
    private ValidadeProduct validadeProduct;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BatchStockRepository batchStockRepository;

    /**
     * Author: Bruno Mendes
     * Method: createCartProduct
     * Description: salva um produto do carrinho na tabela cartProduct
     */
    private CartProduct createCartProduct(Integer qtd, Long id, PurchaseOrder order) {
        Product product = productRepository.getById(id);
        CartProduct cartProduct = CartProduct.builder()
                .product(product)
                .purchaseOrder(order)
                .quantity(qtd)
                .build();
        cartProductRepository.save(cartProduct);
        return cartProduct;
    }

    /**
     * Author: Bruno Mendes
     * Method: updateCurrentQuantity
     * Description: Atualiza a quantidade de produtos em cada batchstock após a compra
     */
    private void updateCurrentQuantity(Integer qtd, Long id) {
        AtomicReference<Integer> acc = new AtomicReference<>(qtd);
        Product product = productRepository.getById(id);
        List<BatchStock> batchStockList = product.getBatchStockList();
        batchStockList.stream().forEach(b -> {
            if (b.getCurrentQuantity() > 0) {
                Integer stock = b.getCurrentQuantity();
                while (stock > 0 && acc.get() > 0) {
                    stock -= 1;
                    b.setCurrentQuantity(b.getCurrentQuantity() - 1);
                    acc.updateAndGet(v -> v - 1);
                }
                b.setCurrentQuantity(stock);
                batchStockRepository.save(b);
            }
        });
    }

    /**
     * Author: Bruno Mendes
     * Method: createOrder
     * Description: Recebe uma ordem de compras, realiza as validações e implementa a compra e calcula o preço total do carrinho
     */
    public OrderIntermediateDTO createOrder(PurchaseOrderInput input){

        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(new BigDecimal(0.00));

        Buyer buyer = validateBuyer.getBuyer(input.getBuyerId());
        input.getProducts().forEach(o -> {validadeProduct.validateQuantity(o.getQuantity(), o.getProductId());} );

        PurchaseOrder purchaseOrder = PurchaseOrder.builder().buyer(buyer).status(StatusEnum.FECHADO).date(LocalDate.now()).build();
        purchaseOrderRepository.save(purchaseOrder);

        input.getProducts().forEach( o -> {
            CartProduct cartProduct = this.createCartProduct(o.getQuantity(), o.getProductId(), purchaseOrder);
            this.updateCurrentQuantity(o.getQuantity(), o.getProductId());
            totalPrice.updateAndGet(v -> v.add(cartProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(o.getQuantity()))));
        } );

        return  OrderIntermediateDTO.builder()
                .createdID(purchaseOrder.getId())
                .totalPrice(totalPrice.get().doubleValue())
                .build();
    }
}
