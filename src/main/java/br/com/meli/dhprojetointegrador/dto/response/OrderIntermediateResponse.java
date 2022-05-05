package br.com.meli.dhprojetointegrador.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class OrderIntermediateResponse {
    private Double totalPrice;
    private Long createdID;
}
