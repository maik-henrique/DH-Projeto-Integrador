package br.com.meli.dhprojetointegrador.unit.service;

import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.repository.WarehouseRepository;
import br.com.meli.dhprojetointegrador.service.WarehouseService;
import br.com.meli.dhprojetointegrador.unit.util.WarehouseCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTests {

    @InjectMocks
    private WarehouseService warehouseService;

    WarehouseRepository warehouseRepository = mock(WarehouseRepository.class);

    Warehouse warehouse1 = WarehouseCreator.createValidWarehouse();
    Warehouse warehouse2 = WarehouseCreator.createValidWarehouse();


    List<Warehouse> warehouses = Arrays.asList(warehouse1, warehouse2);


    @Test
    @DisplayName("Test Req-6 - getAllWarehouses")
    public void returns_list_warehouses_when_successful(){
        when(warehouseRepository.findAll()).thenReturn(warehouses);
        List<Warehouse> result = warehouseService.findAllWarehouses();

        assert result.equals(warehouses);
        assert result.get(0).getName().equals("Warehouse");
    }

    @Test
    @DisplayName("Test Req-6 createWarehouse")
    public void return_warehouse_when_successful_created(){
        when(warehouseRepository.save(ArgumentMatchers.any(Warehouse.class))).thenReturn(warehouse1);
        Warehouse result = warehouseService.create(warehouse1);
        assert result.equals(warehouse1);
        assert result.getName().equals("Warehouse");

    }
}
