package br.com.meli.dhprojetointegrador.dto.response;

import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.entity.Warehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseListDTO {
    private Long id;
    private String name;

    public WarehouseListDTO(Warehouse warehouse) {
        this.id = warehouse.getId();
        this.name = warehouse.getName();
    }

    public static List<WarehouseListDTO> convertToList(List<Warehouse> warehouses) {
        return warehouses.stream().map(WarehouseListDTO::new).collect(Collectors.toList());
    }
}
