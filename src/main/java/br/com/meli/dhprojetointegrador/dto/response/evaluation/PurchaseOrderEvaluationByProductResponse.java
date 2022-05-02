package br.com.meli.dhprojetointegrador.dto.response.evaluation;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class PurchaseOrderEvaluationByProductResponse {
    private Double averageRate;
    private Set<ProductEvaluationDetailsResponse> evaluations;

}
