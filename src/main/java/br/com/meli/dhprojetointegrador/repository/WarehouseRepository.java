package br.com.meli.dhprojetointegrador.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.entity.Warehouse;


@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
  Optional<Warehouse> findBySections(Section section);
}
