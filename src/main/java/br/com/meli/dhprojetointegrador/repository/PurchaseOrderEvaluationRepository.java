package br.com.meli.dhprojetointegrador.repository;

import br.com.meli.dhprojetointegrador.entity.PurchaseOrderEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderEvaluationRepository extends JpaRepository<PurchaseOrderEvaluation, Long> {

    boolean existsByProductIdAndPurchaseOrderId(Long productId, Long PurchaseOrderId);
}
