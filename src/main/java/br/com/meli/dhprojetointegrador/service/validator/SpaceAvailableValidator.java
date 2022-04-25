package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.entity.BatchStock;
import br.com.meli.dhprojetointegrador.entity.InboundOrder;
import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.Section;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SpaceAvailableValidator implements IInboundOrderValidator {

    private final Section section;
    private final InboundOrder inboundOrder;

    @Override
    public void validate() throws BusinessValidatorException {
        Float inboundOrderTotalVolume = inboundOrder.getBatchStockList().stream()
                .map(BatchStock::getProducts)
                .map(Product::getVolume)
                .reduce(0.0f, Float::sum);

        if (inboundOrderTotalVolume > section.getCapacity()) {
            throw new BusinessValidatorException("Setor nao possui espaco disponivel");
        }
    }
}
