package br.com.meli.dhprojetointegrador.dto.request.evaluation;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class PurchaseOrderEvaluationRegistrationRequest {

    @NotNull(message = "buyerId must not be null")
    private Long buyerId;

    @NotNull(message = "purchaseOrderId must not be null")
    private Long purchaseOrderId;

    @NotNull(message = "evaluation must not be null")
    private @Valid EvaluationDetailsRegistrationRequest evaluation;

}
