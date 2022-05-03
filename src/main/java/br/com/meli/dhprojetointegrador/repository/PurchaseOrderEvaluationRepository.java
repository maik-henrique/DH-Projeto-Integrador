package br.com.meli.dhprojetointegrador.repository;

import br.com.meli.dhprojetointegrador.entity.PurchaseOrderEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderEvaluationRepository extends JpaRepository<PurchaseOrderEvaluation, Long> {

    boolean existsByProductIdAndPurchaseOrderId(Long productId, Long PurchaseOrderId);

    @Query("FROM PurchaseOrderEvaluation p WHERE p.purchaseOrder.buyer.id = :buyerId")
    List<PurchaseOrderEvaluation> findByBuyerId(@Param("buyerId") Long id);

    List<PurchaseOrderEvaluation> findByProductId(Long productId);

    @Query("FROM PurchaseOrderEvaluation p WHERE p.purchaseOrder.buyer.id = :buyerId AND p.id = :id")
    Optional<PurchaseOrderEvaluation> findByIdAndBuyerId(@Param("id") Long id, @Param("buyerId") Long buyerId);
}
