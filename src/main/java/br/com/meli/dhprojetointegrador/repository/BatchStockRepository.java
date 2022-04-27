package br.com.meli.dhprojetointegrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.meli.dhprojetointegrador.entity.BatchStock;

public interface BatchStockRepository extends JpaRepository<BatchStock, Long> {
}
