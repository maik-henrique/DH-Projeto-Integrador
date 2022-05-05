package br.com.meli.dhprojetointegrador.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.meli.dhprojetointegrador.dto.request.ProductRefactor;
import br.com.meli.dhprojetointegrador.dto.request.PurchaseOrderRequest;
import br.com.meli.dhprojetointegrador.dto.response.OrderIntermediateResponse;
import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.entity.CartProduct;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.exception.PurchaseOrderNotFoundException;
import br.com.meli.dhprojetointegrador.repository.BatchStockRepository;
import br.com.meli.dhprojetointegrador.repository.CartProductRepository;
import br.com.meli.dhprojetointegrador.repository.OrderRepository;
import br.com.meli.dhprojetointegrador.repository.ProductRepository;
import br.com.meli.dhprojetointegrador.repository.PurchaseOrderRepository;
import br.com.meli.dhprojetointegrador.service.validator.ProductValidator;
import br.com.meli.dhprojetointegrador.service.validator.BuyerValidator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderService {

    @Autowired
    private BuyerValidator buyerValidator;

    @Autowired
    private ProductValidator productValidator;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BatchStockRepository batchStockRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Author: David
     * Methodo: Mudar Cart para Aberto ou Finalizado na Order
     * Description: Modifique o pedido existente. torná-lo do tipo de carrinho para modificar - ABERTO/FINALIZADO
     */
    //@CachePut(value = "UpdateStatusOrder", key = "#idorder")
    public PurchaseOrder atualizar(Long idorder) {

        try {
            PurchaseOrder updateStatus = orderRepository.getById(idorder);

            if (updateStatus.getStatus().equals(StatusEnum.ABERTO)) {
                updateStatus.setStatus(StatusEnum.FINALIZADO);
            } else {
                updateStatus.setStatus(StatusEnum.ABERTO);
            }

            return orderRepository.save(updateStatus);

        } catch (EntityNotFoundException e) {
            throw new PurchaseOrderNotFoundException("Esta ordem nao foi encontrada na base de dados!");
        }
    }

    /**
     * Author: Bruno Mendes
     * Method: createCartProduct
     * Description: salva um produto do carrinho na tabela cartProduct
     */
    private void createCartProduct(Integer qtd, Long id, PurchaseOrder order) {
        Product product = productRepository.findById(id).get();
        CartProduct cartProduct = CartProduct.builder()
                .product(product)
                .purchaseOrder(order)
                .quantity(qtd)
                .build();
        cartProductRepository.save(cartProduct);
    }

    /**
     * Author: Bruno Mendes
     * Method: updateCurrentQuantity
     * Description: Atualiza a quantidade de produtos em cada batchstock após a
     * compra
     */
    private void updateCurrentQuantity(Integer qtd, Long id) {
        AtomicReference<Integer> acc = new AtomicReference<>(qtd);
        Product product = productRepository.findById(id).get();
        Set<BatchStock> batchStockList = new HashSet<>(product.getBatchStockList());
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
     * Method: calculateTotalCart
     * Description: recebe uma lista de produtos e quantodades e retorna o valor
     * total do carrinho
     */
    private Double calculateTotalCart(List<ProductRefactor> productRefactorList, List<Product> productList) {
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(new BigDecimal(0.00));

        productRefactorList.forEach(cartProduct -> {
            BigDecimal price = productList.stream().filter(p -> p.getId().equals(cartProduct.getProductId()))
                    .findFirst().get().getPrice();
            totalPrice.updateAndGet(v -> v.add(price.multiply(BigDecimal.valueOf(cartProduct.getQuantity()))));
        });

        return totalPrice.get().doubleValue();
    }

    /**
     * Author: Bruno Mendes
     * Method: createPurchaseOrder
     * Description: Recebe um Buyer e cria uma purchaseOrder
     */
    private PurchaseOrder createPurchaseOrder(Buyer buyer, LocalDate date) {
        PurchaseOrder purchaseOrder = PurchaseOrder.builder().buyer(buyer).status(StatusEnum.FINALIZADO).date(date)
                .build();
        return purchaseOrderRepository.save(purchaseOrder);
    }

    /**
     * Author: Bruno Mendes
     * Method: createOrder
     * Description: Recebe uma ordem de compras, realiza as validações e implementa
     * a compra e retorna o preço total do carrinho
     */
    //@CacheEvict(value = "createOrder", key = "#input")
    public OrderIntermediateResponse createOrder(PurchaseOrderRequest input) {

        Buyer buyer = buyerValidator.getBuyer(input.getBuyerId());

        List<ProductRefactor> productRefactorList = input.getProducts();
        List<Product> productList = productRefactorList.stream()
                .map(o -> productValidator.validateQuantity(o.getQuantity(), o.getProductId()))
                .collect(Collectors.toList());

        PurchaseOrder purchaseOrder = this.createPurchaseOrder(buyer, input.getDate());

        productRefactorList.forEach(o -> {
            this.createCartProduct(o.getQuantity(), o.getProductId(), purchaseOrder);
            this.updateCurrentQuantity(o.getQuantity(), o.getProductId());
        });

        Double totalPrice = this.calculateTotalCart(productRefactorList, productList);

        return OrderIntermediateResponse.builder()
                .createdID(purchaseOrder.getId())
                .totalPrice(totalPrice)
                .build();
    }
}
