package br.com.meli.dhprojetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.meli.dhprojetointegrador.entity.InboundOrder;

public interface InboundOrderRepository extends JpaRepository<InboundOrder, Integer> {

}
