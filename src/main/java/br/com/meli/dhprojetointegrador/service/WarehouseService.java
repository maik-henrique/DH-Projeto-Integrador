package br.com.meli.dhprojetointegrador.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.WarehouseRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class WarehouseService {

    private WarehouseRepository warehouseRepository;

    //@Cacheable(value = "findWarehouseIdBySection", key = "#section")
    public Warehouse findWarehouseIdBySection(Section section) throws BusinessValidatorException {
        return warehouseRepository.findBySections(section)
                .orElseThrow(() -> new BusinessValidatorException("Warehouse not found, section id invalid"));
    }
    public List<Warehouse> findAllWarehouse(){
        return warehouseRepository.findAll();
    }
}
