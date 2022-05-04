package br.com.meli.dhprojetointegrador.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
  Optional<Warehouse> findBySections(Section section);

}
