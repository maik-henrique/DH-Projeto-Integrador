package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.entity.Product;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import br.com.meli.dhprojetointegrador.repository.PurchaseOrderEvaluationRepository;
import lombok.AllArgsConstructor;

/**
 * @author Maik
 * Valida se o produto de uma determinada compra já foi avaliado anteriormente
 */
@AllArgsConstructor
public class PurchaseAlreadyEvaluatedValidator implements IPurchaseOrderEvaluationValidator {
    private final PurchaseOrderEvaluationRepository purchaseOrderEvaluationRepository;
    private final Product product;
    private final PurchaseOrder purchaseOrder;

    /**
     * @author Maik
     * Valida se o produto de uma compra já foi avaliado, para isso faz uma busca com base no id do produto e a ordem
     * de compra, caso o registro exista, lança uma exceção
     *
     * @throws BusinessValidatorException caso o produto já tenha sido avaliado
     */
    @Override
    public void validate() throws BusinessValidatorException {
        boolean isPurchaseAlreadyEvaluated = purchaseOrderEvaluationRepository.existsByProductIdAndPurchaseOrderId(product.getId(),
                purchaseOrder.getId());

        if (isPurchaseAlreadyEvaluated) {
            throw new BusinessValidatorException(String.format("Purchase of id %d and product of id %d were already evaluated",
                    purchaseOrder.getId(), product.getId()));
        }
    }
}
