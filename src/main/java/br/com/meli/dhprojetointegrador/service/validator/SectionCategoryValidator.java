package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.enums.CategoryEnum;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import lombok.AllArgsConstructor;

/**
 * Validates if the category of an inbound order matches the section category 
 *
 */
@AllArgsConstructor
public class SectionCategoryValidator implements IInboundOrderValidator {

    private final Section section;
    private final InboundOrder inboundOrder;

    /**
     * Validates for each product in the batch stock if their category matches the section's category, if it does not
     * it'll then throw a BusinessValidatorException
     */
    @Override
    public void validate() throws BusinessValidatorException {
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
