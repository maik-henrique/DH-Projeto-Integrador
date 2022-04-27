package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import lombok.AllArgsConstructor;

/**
 * 
 * Validates if section has space available for the incoming inbound order
 *
 */
@AllArgsConstructor
public class SpaceAvailableValidator implements IInboundOrderValidator {

    private final Section section;
    private final InboundOrder inboundOrder;

    /**
     * The validation calculates the total volume of the inbound order and compares it to the section capacity,
     * if it exceeds then it'll throw a BusinessValidatorException
     */
    @Override
    public void validate() throws BusinessValidatorException {
        Float inboundOrderTotalVolume = inboundOrder.getBatchStockList().stream()
                .map(BatchStock::getProducts)
                .map(Product::getVolume)
                .reduce(0.0f, Float::sum);

        if (inboundOrderTotalVolume > section.getCapacity()) {
            throw new BusinessValidatorException(String.format("Section has capacity of %.2f, which is incompatible with the inbound order total volume which is %.2f",
            		section.getCapacity(), inboundOrderTotalVolume));
        }
    }
}
