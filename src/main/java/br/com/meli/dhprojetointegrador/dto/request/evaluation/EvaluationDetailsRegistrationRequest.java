package br.com.meli.dhprojetointegrador.dto.request.evaluation;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class EvaluationDetailsRegistrationRequest {
    @NotNull(message = "productId must not be null")
    private Long productId;

    @Size(min = 1, max = 400, message = "message must be between 1 and 400 characters")
    private String comment;

    @Range(min = 0, max = 10, message = "rating must be between 0 and 10, and also needs to be an integer")
    @NotNull(message = "rating must not be null")
    private Integer rating;
}
