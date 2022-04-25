package br.com.meli.dhprojetointegrador.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.meli.dhprojetointegrador.entity.InboundOrder;

@Repository
public interface InboundOrderRepository extends JpaRepository<InboundOrder, Integer> {
    Optional<InboundOrder> findByOrderNumber(Integer orderNumber);
}
