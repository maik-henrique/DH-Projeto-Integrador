package br.com.meli.dhprojetointegrador.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.meli.dhprojetointegrador.entity.Section;


import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    List<Section> findSectionByWarehouseId(@Param("id")Long id);

    List<Section> findSectionByCategoryId(@Param("id")Long id);



}
