package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;

/**
 * Validator for InboundOrder processing.
 * @author maik
 *
 */
public interface IInboundOrderValidator {
	
	/**
	 * Method used to apply business logic validation on InboundOrder objects, if the validation succeeds, it'll do nothing, otherwise
	 * it throws an exception.
	 *  
	 * @throws BusinessValidatorException in case a validation fails
	 */
    void validate() throws BusinessValidatorException;
}
