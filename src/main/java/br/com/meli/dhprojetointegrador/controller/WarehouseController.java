package br.com.meli.dhprojetointegrador.controller;


import br.com.meli.dhprojetointegrador.dto.request.WarehouseRequest;
import br.com.meli.dhprojetointegrador.dto.response.WarehouseDTO;
import br.com.meli.dhprojetointegrador.dto.response.WarehouseListDTO;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fresh-products/admin")
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final ModelMapper modelMapper;

    /**
     * @Author: Micaela Alves
     * @Description: Consulta as warehouses cadastradas
     * @return: lista de todas as warehouses
     */
    @GetMapping("/warehouse")
    public ResponseEntity<?> findAllWarehouses(){
        try {
            List<Warehouse> warehouseResponse = warehouseService.findAllWarehouses();
            return new ResponseEntity<>(WarehouseListDTO.convertToList(warehouseResponse), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Author: Micaela Alves
     * @Description: realiza o cadastro de uma warehouse
     * @param warehouseRequest
     * @return warehouse cadastrada
     */
    @PostMapping("warehouse")
    public ResponseEntity<?> createWarehouse(@Valid @RequestBody WarehouseRequest warehouseRequest) {
        try {
            Warehouse newWarehouse = modelMapper.map(warehouseRequest, Warehouse.class);
            Warehouse warehouseCreated = warehouseService.create(newWarehouse);
            WarehouseDTO warehouseResponse = modelMapper.map(warehouseCreated,WarehouseDTO.class);
            return new ResponseEntity<>(warehouseResponse, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
