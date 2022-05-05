package br.com.meli.dhprojetointegrador.dto.response;

import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductByWarehouseResponse {
    Long productId;
    List<WarehouseQuantityResponse> warehouses;
}
