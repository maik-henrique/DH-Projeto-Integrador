package br.com.meli.dhprojetointegrador.unit.service.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.meli.dhprojetointegrador.entity.Category;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.entity.Warehouse;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.SectionRepository;
import br.com.meli.dhprojetointegrador.repository.WarehouseRepository;
import br.com.meli.dhprojetointegrador.service.SectionService;
import br.com.meli.dhprojetointegrador.service.validator.SectionValidator;
import br.com.meli.dhprojetointegrador.util.SectionCreator;
import br.com.meli.dhprojetointegrador.util.WarehouseCreator;

@ExtendWith(SpringExtension.class)
public class SectionValidatorTests {

    private SectionValidator sectionValidator;

    @Mock
    private WarehouseRepository warehouseRepository;
    @Mock
    private SectionRepository sectionRepository;

    @BeforeEach
    void setUp() {
        BDDMockito.when(warehouseRepository.save(ArgumentMatchers.any(Warehouse.class)))
                .thenReturn(WarehouseCreator.createValidWarehouse());

        BDDMockito.when(sectionRepository.save(ArgumentMatchers.any(Section.class)))
                .thenReturn(SectionCreator.createValidSection());

        BDDMockito.when(sectionRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(SectionCreator.createValidSection()));
    }

    @Test
    public void validate_shouldNotThrowException_whenSectionIsValid() {
        SectionService sectionService = new SectionService(sectionRepository);

        Category frios = Category.builder().name(CategoryEnum.FRIOS).build();

        Warehouse warehouse = warehouseRepository.save(Warehouse.builder().name("Warehouse 1").build());

        Section section = Section.builder().category(frios).name("Section 2")
                .warehouse(warehouse).capacity(10).id(1L).build();

        sectionRepository.save(section);

        sectionValidator = new SectionValidator(sectionService, section.getId());

        assertDoesNotThrow(() -> sectionValidator.validate());
    }

    @Test
    public void validate_shouldThrowBusinessValidatorException_whenSectionIsNotValid() {
        SectionService sectionService = new SectionService(sectionRepository);

        sectionValidator = new SectionValidator(sectionService, null);

        assertThrows(BusinessValidatorException.class, () -> sectionValidator.validate());
    }
}
