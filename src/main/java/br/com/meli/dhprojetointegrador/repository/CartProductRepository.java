package br.com.meli.dhprojetointegrador.repository;

import br.com.meli.dhprojetointegrador.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    List<CartProduct> findByPurchaseOrderId(long id);
}
