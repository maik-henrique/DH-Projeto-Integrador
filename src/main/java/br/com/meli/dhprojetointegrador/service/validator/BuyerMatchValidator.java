package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.entity.Buyer;
import br.com.meli.dhprojetointegrador.entity.PurchaseOrder;
import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;
import lombok.AllArgsConstructor;

/**
 * @author maik
 * Valida se o comprador especificado corresponde a PurchaseOrder fornecida
 *
 */
@AllArgsConstructor
public class BuyerMatchValidator implements IPurchaseOrderEvaluationValidator {
	
	private final PurchaseOrder queriedPurchaseOrder;
	private final Buyer providedBuyer;
	
	/**
	 * Valida a correspondência entre o PurchaseOrder especificado e o Buyer
	 * @throws BusinessValidatorException caso não haja um match entre o Buyer e a PurchaseOrder armazenada
	 */
	@Override	
	public void validate() throws BusinessValidatorException {
		Buyer buyer = queriedPurchaseOrder.getBuyer();
		
		if (buyer == null || !providedBuyer.getId().equals(buyer.getId())) {
			throw new BusinessValidatorException(String.format("Buyer of id %d is not linked to the purchase order", providedBuyer.getId())); 
		}		
	}
	
	
	
	

}
