package br.com.meli.dhprojetointegrador.service;

import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.repository.CategoryRepository;
import br.com.meli.dhprojetointegrador.repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.SectionRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final WarehouseRepository warehouseRepository;
    private final CategoryRepository categoryRepository;


    public Section findSectionById(Long id) throws BusinessValidatorException {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new BusinessValidatorException(String.format("Section of id %d not found", id)));
    }


    public List<Section> returnAllSectionsByWarehouse(Long warehouseId ){
        return sectionRepository.findSectionByWarehouseId(warehouseId);
    }

    public List<Section> returnAllSectionsByCategory(Long categoryId ){
        return sectionRepository.findSectionByCategoryId(categoryId);
    }


    public Section create(Section section, Long warehouseId, Long categoryId) {


        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseId);
        Warehouse warehouse1 = null;

        if (warehouse.isPresent()){
            warehouse1 = warehouse.get();
        }

        Optional<Category> category = categoryRepository.findById(categoryId);
        Category category1 = null;

        if (category.isPresent()){
            category1 = category.get();
        }

        section.setWarehouse(warehouse1);
        section.setCategory(category1);

        sectionRepository.save(section);

        return section;
    }


}
