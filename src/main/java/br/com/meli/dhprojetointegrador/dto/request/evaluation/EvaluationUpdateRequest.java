package br.com.meli.dhprojetointegrador.dto.request.evaluation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class EvaluationUpdateRequest {
    @NotNull(message = "id must not be null")
    private Long id;

    @Size(min = 1, max = 400, message = "message must be between 1 and 400 characters")
    @NotBlank(message = "comment must not be blank")
    private String comment;

    @JsonProperty("buyerId")
    @NotNull(message = "buyerId must not be null")
    private Long purchaseOrderBuyerId;
}
