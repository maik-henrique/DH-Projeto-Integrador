package br.com.meli.dhprojetointegrador.service;

import org.springframework.stereotype.Service;

import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.WarehouseRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public Warehouse findWarehouseIdBySection(Section section) throws BusinessValidatorException {
        return warehouseRepository.findBySections(section)
                .orElseThrow(() -> new BusinessValidatorException("Warehouse not found, section id invalid"));
    }

    public List<Warehouse> findAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse create(Warehouse warehouse){
        return warehouseRepository.save(warehouse);
    }

    public Warehouse findById(Long id) {
        return warehouseRepository
                .findById(id)
                .orElseThrow(() -> new BusinessValidatorException(String.format("Warehouse with id %d not found", id)));
    }
}
