package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;

public interface IPurchaseOrderEvaluationValidator {
	
	void validate() throws BusinessValidatorException;

}
