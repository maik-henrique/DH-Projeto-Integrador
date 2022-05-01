package br.com.meli.dhprojetointegrador.dto.request.evaluation;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class EvaluationDetailsRegistrationRequest {
    @NotNull
    private Long productId;
    private String comment;

    @NotNull
    private Integer rating;
}
