package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.enums.StatusEnum;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import lombok.AllArgsConstructor;

/**
 * @author maik
 * 
 *	Valida se uma PurchaseOrder foi finalizada
 */
@AllArgsConstructor
public class PurchaseOrderCompletedValidator implements IPurchaseOrderEvaluationValidator {
	
	private final PurchaseOrder purchaseOrder;

	/**
	 * Valida se uma PurchaseOrder está apta para avaliação pelo cliente
	 * @throws BusinessValidatorException caso a PurchaseOrder ainda não tenha sido terminada
	 */
	@Override
	public void validate() throws BusinessValidatorException {
		
		if (!StatusEnum.FINALIZADO.equals(purchaseOrder.getStatus())) {
			throw new BusinessValidatorException(String.format("PurchaseOrder of id %d isn't closed yet, so it's not possible to give it an evaluation", 
					purchaseOrder.getId()));			
		}
		
	}

}
