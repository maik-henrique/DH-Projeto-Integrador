package br.com.meli.dhprojetointegrador.dto.response.evaluation;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class PurchaseOrderEvaluationFetchResponse {
    private Long productId;
    private Long purchaseId;
    private String comment;
    private Integer rating;
}
