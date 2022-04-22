package br.com.meli.dhprojetointegrador.repository;

import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder, String> {

    PurchaseOrder findbyStatus(String status);

}
