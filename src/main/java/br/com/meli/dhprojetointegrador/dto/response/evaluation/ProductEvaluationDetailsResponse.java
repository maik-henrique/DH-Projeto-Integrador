package br.com.meli.dhprojetointegrador.dto.response.evaluation;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductEvaluationDetailsResponse {
    private Integer id;
    private String comment;
    private Integer rating;
}
