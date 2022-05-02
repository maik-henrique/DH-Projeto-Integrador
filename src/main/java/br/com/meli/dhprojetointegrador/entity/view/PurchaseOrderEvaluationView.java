package br.com.meli.dhprojetointegrador.entity.view;

import br.com.meli.dhprojetointegrador.entity.PurchaseOrderEvaluation;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderEvaluationView {
    private Double averageRate;
    private List<PurchaseOrderEvaluation> evaluations;
}
