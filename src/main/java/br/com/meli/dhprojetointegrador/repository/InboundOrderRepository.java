package br.com.meli.dhprojetointegrador.repository;

import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InboundOrderRepository extends JpaRepository<InboundOrder, Integer> {
    Optional<InboundOrder> findByOrderNumber(Integer orderNumber);
}
