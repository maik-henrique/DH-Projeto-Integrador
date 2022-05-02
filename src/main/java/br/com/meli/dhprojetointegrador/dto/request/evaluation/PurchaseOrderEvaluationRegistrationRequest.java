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

    @NotNull
    private Long buyerId;

    @NotNull
    private Long purchaseOrderId;

    @NotNull
    private @Valid EvaluationDetailsRegistrationRequest evaluation;

}
