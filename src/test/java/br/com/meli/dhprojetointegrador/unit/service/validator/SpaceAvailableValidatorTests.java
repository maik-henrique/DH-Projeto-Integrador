package br.com.meli.dhprojetointegrador.unit.service.validator;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.service.validator.SpaceAvailableValidator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpaceAvailableValidatorTests {

    private SpaceAvailableValidator spaceAvailableValidator;

    @Test
    public void validate_shouldThrowBusinessValidatorException_whenSectionDoesNotHaveSpaceAvailable(){
        Section section = Section.builder().capacity(32.0f).build();
        Product frios = Product.builder().volume(34.0f).build();
        BatchStock batchStockFrios = BatchStock.builder().products(frios).build();

        Product congelados = Product.builder().volume(12.0f).build();
        BatchStock batchStockCongelados = BatchStock.builder().products(congelados).build();

        InboundOrder inbo = InboundOrder.builder().batchStockList(List.of(batchStockFrios, batchStockCongelados)).build();
        spaceAvailableValidator = new SpaceAvailableValidator(section, inbo);

        assertThrows(BusinessValidatorException.class, () -> spaceAvailableValidator.validate());

    }
    @Test
    public void validate_shouldNotThrowException_whenSectionHasSpaceAvailable(){
        Section section = Section.builder().capacity(32.0f).build();
        Product congelados = Product.builder().volume(12.0f).build();
        BatchStock batchStockCongelados = BatchStock.builder().products(congelados).build();
        InboundOrder inbo = InboundOrder.builder().batchStockList(List.of(batchStockCongelados)).build();
        spaceAvailableValidator = new SpaceAvailableValidator(section, inbo);

        assertDoesNotThrow(() -> spaceAvailableValidator.validate());
    }

}
