package br.com.meli.dhprojetointegrador.dto.request.evaluation;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class EvaluationDetailsRegistrationRequest {
    @NotNull(message = "productId must not be null")
    private Long productId;
    private String comment;

    @NotNull(message = "rating must not be null")
    private Integer rating;
}
