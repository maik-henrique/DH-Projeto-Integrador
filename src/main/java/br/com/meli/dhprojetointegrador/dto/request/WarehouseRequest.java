package br.com.meli.dhprojetointegrador.dto.request;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class WarehouseRequest {
    private Long id;
    private String name;
}