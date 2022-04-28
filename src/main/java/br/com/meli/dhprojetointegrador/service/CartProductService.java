package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.CartProduct;
import br.com.meli.dhprojetointegrador.repository.CartProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CartProductService{

    private final CartProductRepository cartProdRepo;

    public List<CartProduct> getProductsByOrderId(long OrderId) {
        return cartProdRepo.findByPurchaseOrderId(OrderId);
    }

}
