package br.com.meli.dhprojetointegrador.dto.request.evaluation;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

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
    private Set<@Valid EvaluationDetailsRegistrationRequest> evaluation;

}
