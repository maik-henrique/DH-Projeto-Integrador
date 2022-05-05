package br.com.meli.dhprojetointegrador.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class WarehouseQuantity {
    Long warehouseCode;
    Integer totalQuantity;
}
