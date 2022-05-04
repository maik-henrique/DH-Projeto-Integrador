package br.com.meli.dhprojetointegrador.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class WarehouseRequest {

    @NotNull
    private String name;
}