package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.CartProduct;
import br.com.meli.dhprojetointegrador.exception.ResourceNotFoundException;
import br.com.meli.dhprojetointegrador.repository.CartProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class CartProductService{

    private final CartProductRepository cartProdRepo;

    /**
     * Author: Micaela Alves
     * Method: getProductsByOrderId
     * Description: Recupera uma lista com todos os registros de CartProduct onde OrderId for igual ao especificado
     *
     **/
    //@Cacheable(value = "getProductsByOrderId", key = "#OrderId")
    public List<CartProduct> getProductsByOrderId(long OrderId) {
        List<CartProduct> list = cartProdRepo.findByPurchaseOrderId(OrderId);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Order Id is not found");
        }
        return list;
    }

}
