package br.com.meli.dhprojetointegrador.dto.request.evaluation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class EvaluationUpdateRequest {
    @NotNull(message = "id must not be null")
    private Long id;

    @NotBlank(message = "comment must not be blank")
    private String comment;

    @JsonProperty("buyerId")
    @NotNull(message = "buyerId must not be null")
    private Long purchaseOrderBuyerId;
}
