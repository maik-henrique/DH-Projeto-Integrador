package br.com.meli.dhprojetointegrador.controller;


import br.com.meli.dhprojetointegrador.dto.response.WarehouseDTO;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fresh-products/admin")
public class ManagerController {

    private final WarehouseService warehouseService;
    private final ModelMapper modelMapper;

    @GetMapping("/warehouse")
    public ResponseEntity<WarehouseDTO> findAllWarehouses(){
        List<Warehouse> warehouses = warehouseService.findAllWarehouses();
        WarehouseDTO warehousesDTO = modelMapper.map(warehouses,WarehouseDTO.class);
        return ResponseEntity.ok(warehousesDTO);
    }
}
