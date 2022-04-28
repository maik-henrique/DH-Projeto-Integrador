package br.com.meli.dhprojetointegrador.repository;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface BatchStockRepository extends JpaRepository<BatchStock, Long> {
    @Query("FROM BatchStock b WHERE b.products.id = :product AND b.dueDate >= :minimumDueDate AND  b.dueDate <= :maxDueDate")
    List<BatchStock> findBatchStockByProducts(@Param("product") Long productId, @Param("minimumDueDate") LocalDate minimumDueDate,
                                              @Param("maxDueDate") LocalDate maxDueDate, Sort sort);
}
