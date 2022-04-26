package br.com.meli.dhprojetointegrador.unit.service.validator;

import br.com.meli.dhprojetointegrador.entity.*;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.service.validator.SectionCategoryValidator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SectionCategoryValidatorTests {

    private SectionCategoryValidator sectionCategoryValidator;

    @Test
    public void validate_shouldNotThrowException_whenSectionAndProductCategoryMatches() {
        Category frios = Category.builder().name(CategoryEnum.FRIOS).build();
        Section section = Section.builder().category(frios).build();

        Product productFrios = Product.builder().category(frios).volume(12.0f).build();
        BatchStock batchStockCongelados = BatchStock.builder().products(productFrios).build();
        InboundOrder inboundOrder = InboundOrder.builder().batchStockList(Set.of(batchStockCongelados)).build();

        sectionCategoryValidator = new SectionCategoryValidator(section, inboundOrder);

        assertDoesNotThrow(() -> sectionCategoryValidator.validate());
    }

    @Test
    public void validate_shouldThrowBusinessValidatorException_whenSectionAndProductCategoryDoesNotMatch() {
        Category frios = Category.builder().name(CategoryEnum.FRIOS).build();
        Category congelados = Category.builder().name(CategoryEnum.CONGELADOS).build();

        Section sectionCongelados = Section.builder().category(congelados).build();

        Product productFrios = Product.builder().category(frios).build();
        BatchStock batchStockCongelados = BatchStock.builder().products(productFrios).build();
        InboundOrder inboundOrder = InboundOrder.builder().batchStockList(Set.of(batchStockCongelados)).build();

        sectionCategoryValidator = new SectionCategoryValidator(sectionCongelados, inboundOrder);

        assertThrows(BusinessValidatorException.class, () -> sectionCategoryValidator.validate());
    }
}
