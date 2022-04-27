package br.com.meli.dhprojetointegrador.repository;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchStockRepository extends JpaRepository<BatchStock, Long> {
}
