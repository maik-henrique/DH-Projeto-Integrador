package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.CartProduct;
import br.com.meli.dhprojetointegrador.repository.ICartProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartProductService{

    private final ICartProductRepository cartProdRepo;

    public CartProductService(ICartProductRepository cartProdRepo) {
        this.cartProdRepo = cartProdRepo;
    }

    @Transactional(readOnly = true)
    public List<CartProduct> getProductsByOrderId(long id) {
        return cartProdRepo.findByPurchaseOrderId(id) ;
    }

}
