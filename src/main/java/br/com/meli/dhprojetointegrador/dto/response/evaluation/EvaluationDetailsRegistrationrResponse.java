package br.com.meli.dhprojetointegrador.dto.response.evaluation;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class EvaluationDetailsRegistrationrResponse {
    private Long productId;
    private String comment;
    private int rating;
}
