package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SectionCategoryValidator implements IInboundOrderValidator {

    private final Section section;
    private final InboundOrder inboundOrder;

    @Override
    public void validate() {
        CategoryEnum expectedCategory = section.getCategory().getName();

        inboundOrder.getBatchStockList().stream().map(BatchStock::getProducts)
                .filter(product -> !product.getCategory().getName().equals(expectedCategory))
                .findAny()
                .ifPresent(product -> {
                    throw new BusinessValidatorException(String.format("Product's category from product %d is invalid, the expected was %s",
                            product.getId(), expectedCategory.name()));
                });
    }
}
