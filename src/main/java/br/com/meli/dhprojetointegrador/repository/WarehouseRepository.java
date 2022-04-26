package br.com.meli.dhprojetointegrador.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.entity.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
  Optional<Warehouse> findBySections(Section section);
}
