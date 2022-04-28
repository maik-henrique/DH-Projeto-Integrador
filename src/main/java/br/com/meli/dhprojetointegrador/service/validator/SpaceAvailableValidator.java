package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import lombok.AllArgsConstructor;

/**
 * @Author: Maik
 * Valida se a Section possui espaço disponível para a InboundOrder que está sendo recebida
 *
 */
@AllArgsConstructor
public class SpaceAvailableValidator implements IInboundOrderValidator {

    private final Section section;
    private final InboundOrder inboundOrder;

    /**
     * @Author: Maik
     *
     * Cálcula o volume total da ordem de entrada e compara com a capacidade da seção, caso exceda, lança uma
     * exceção
     * @throws: BusinessValidatorException caso exceda a capacidade da seção
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
