package br.com.meli.dhprojetointegrador.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;

@Repository
public interface BatchStockRepository extends JpaRepository<BatchStock, Long> {

        @Query("FROM BatchStock b WHERE b.inboundOrder.section.id = :sectionId AND b.dueDate > :dueDate AND b.products.category.name IN :category")
        public List<BatchStock> findBySectionId(
                        @Param("sectionId") Long sectionId,
                        @Param("dueDate") LocalDate dueDate,
                        @Param("category") List<CategoryEnum> category,
                        Sort sort);

        // @Param("dueDate") LocalDate dueDate, Pageable pageable
        @Query("FROM BatchStock b WHERE b.products.id = :product AND b.dueDate >= :minimumDueDate AND  b.dueDate <= :maxDueDate")
        List<BatchStock> findBatchStockByProducts(@Param("product") Long productId,
                        @Param("minimumDueDate") LocalDate minimumDueDate,
                        @Param("maxDueDate") LocalDate maxDueDate, Sort sort);

}
