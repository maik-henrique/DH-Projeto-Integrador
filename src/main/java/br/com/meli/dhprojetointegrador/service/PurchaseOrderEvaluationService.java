package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.PurchaseOrderEvaluation;
import br.com.meli.dhprojetointegrador.repository.PurchaseOrderEvaluationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PurchaseOrderEvaluationService {
    private final PurchaseOrderEvaluationRepository purchaseOrderEvaluationRepository;

    public PurchaseOrderEvaluation save(PurchaseOrderEvaluation purchaseOrderEvaluation) {

        return purchaseOrderEvaluationRepository.save(purchaseOrderEvaluation);
    }
}
