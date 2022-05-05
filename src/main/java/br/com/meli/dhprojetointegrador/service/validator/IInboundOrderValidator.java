package br.com.meli.dhprojetointegrador.service.validator;

import br.com.meli.dhprojetointegrador.exception.BusinessValidatorException;

/**
 * Validação das regras de negócio voltadas para o processamento do InboundOrder
 * @author: Maik
 *
 */
public interface IInboundOrderValidator {
	
	/**
	 * Método usado para aplicar a validação das regras de negócio nos objetos InboundOrder, se a validação for bem sucedidade
	 * a execução procede normalmente, casao contrário deverá lançar uma exceção
	 * @throws BusinessValidatorException caso a validação falhe
	 */
    void validate() throws BusinessValidatorException;
}
